package me.hugmanrique.jacobin.reader;

import com.google.common.io.ByteSource;
import me.hugmanrique.jacobin.BinaryReader;
import me.hugmanrique.jacobin.util.ByteExceptions;
import me.hugmanrique.jacobin.util.ByteUtil;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Implementation of {@link BinaryReader} that uses an {@link InputStream} as the data source.
 * @author Hugo Manrique
 * @since 09/06/2018
 */
public abstract class AbstractBinaryReader implements BinaryReader {
  private static final int DEFAULT_BUFFER_SIZE = 4096;

  private final InputStream input;
  private final byte[] buffer;

  private int bufferSize;
  private int pos;
  private int totalBytesRead;

  public AbstractBinaryReader(final InputStream input) {
    this(input, DEFAULT_BUFFER_SIZE);
  }

  public AbstractBinaryReader(final InputStream input, int bufferSize) {
    this.input = checkNotNull(input);
    this.buffer = new byte[bufferSize];
    this.bufferSize = 0;
    this.pos = 0;
    this.totalBytesRead = 0;
  }

  public AbstractBinaryReader(final byte[] buffer) throws IOException {
    this(buffer, 0, buffer.length);
  }

  public AbstractBinaryReader(final byte[] buffer, final int offset, final int length) throws IOException {
    // TODO Create optimized Binary reader for raw byte arrays
    this(ByteSource.wrap(buffer).slice(offset, length).openStream());
  }

  /**
   * Reads more bytes from the input, making at least {@code n} bytes available in the buffer.
   * Caller must ensure that the requested space is not yet available, and that the requested
   * space is less than {@link #DEFAULT_BUFFER_SIZE}.
   */
  private void refillBuffer(int n) throws IOException {
    if (!tryRefillBuffer(n)) {
      throw ByteExceptions.TRUNCATED_DATA;
    }
  }

  /**
   * Tries to read more bytes from the input, making at least {@code n} bytes available in the
   * buffer. Caller must ensure that the requested space is not yet available, and that the
   * requested space is less than {@link #DEFAULT_BUFFER_SIZE}.
   * @return true if the bytes could be made available.
   */
  private boolean tryRefillBuffer(int n) throws IOException {
    if (pos + n <= bufferSize) {
      throw new IllegalStateException(
        "refillBuffer() called when " + n + " bytes were already available in the buffer"
      );
    }

    int tempPos = pos;
    if (tempPos > 0) {
      if (bufferSize > tempPos) {
        System.arraycopy(buffer, tempPos, buffer, 0, bufferSize - tempPos);
      }

      totalBytesRead += tempPos;
      bufferSize -= tempPos;
      pos = 0;
    }

    // We should refill the buffer as many bytes as possible
    int bytesRead = input.read(buffer, bufferSize, buffer.length - bufferSize);

    // TODO Can this happen?
    if (bytesRead == 0 || bytesRead > buffer.length) {
      throw new IllegalStateException("InputStream#read(byte[]) returned invalid result: " + bytesRead);
    }

    if (bytesRead > 0) {
      bufferSize += bytesRead;
      return bufferSize >= n || tryRefillBuffer(n);
    }

    return false;
  }

  @Override
  public boolean isAtEnd() throws IOException {
    return pos == bufferSize && !tryRefillBuffer(1);
  }

  @Override
  public int getTotalBytesRead() {
    return totalBytesRead + pos;
  }

  @Override
  public byte readByte() throws IOException {
    if (pos == bufferSize) {
      refillBuffer(1);
    }

    return buffer[pos++];
  }

  @Override
  public byte[] readBytes(int size) throws IOException {
    final int tempPos = pos;

    if (size <= (bufferSize - tempPos) && size > 0) {
      pos = tempPos + size;

      return Arrays.copyOfRange(buffer, tempPos, tempPos + size);
    } else {
      return readBytesSlow(size);
    }
  }

  /**
   * Exactly like {@link #readBytes(int)}, but caller must have already checked the fast path:
   * size <= (bufferSize - pos) && size > 0
   */
  private byte[] readBytesSlow(final int size) throws IOException {
    // Attempt to read the data in one byte array when it's safe to do.
    byte[] result = readBytesSlowOneChunk(size);

    if (result != null) {
      return result;
    }

    final int originalBufferPos = pos;
    final int bufferedBytes = bufferSize - pos;

    totalBytesRead += bufferSize;
    pos = 0;
    bufferSize = 0;

    int sizeLeft = size - bufferedBytes;
    List<byte[]> chunks = readBytesSlowRemainingChunks(sizeLeft);
    final byte[] bytes = new byte[size];

    // Start copying the leftover bytes from this.buffer
    System.arraycopy(buffer, originalBufferPos, bytes, 0, bufferedBytes);

    // And now copy all the chunks
    int tempPos = 0;
    for (final byte[] chunk : chunks) {
      System.arraycopy(chunk, 0, bytes, tempPos, chunk.length);
      tempPos += chunk.length;
    }

    return bytes;
  }

  /**
   * Attempt to read the data in one byte array when it's safe to do. Returns {@code null}
   * if the size to read is too large and needs to be allocated in smaller chunks.
   */
  private byte[] readBytesSlowOneChunk(final int size) throws IOException {
    if (size == 0) {
      return ByteUtil.EMPTY_BYTE_ARRAY;
    }

    if (size < 0) {
      throw ByteExceptions.NEGATIVE_SIZE;
    }

    final int bufferedBytes = bufferSize - pos;
    // Number of bytes whe need to read from the InputStream
    int sizeLeft = size - bufferedBytes;

    if (sizeLeft < DEFAULT_BUFFER_SIZE || sizeLeft <= input.available()) {
      // Either the bytes we need are known to be available, or the required buffer
      // is within an allowed threshold - go ahead and allocate the buffer now.
      final byte[] bytes = new byte[size];

      // Copy all of the buffered bytes to the result buffer.
      System.arraycopy(buffer, pos, bytes, 0, bufferedBytes);
      totalBytesRead += bufferSize;
      pos = 0;
      bufferSize = 0;

      // Fill the remaining bytes from the InputStream
      int tempPos = 0;
      while (tempPos < bytes.length) {
        int n = input.read(bytes, tempPos, size - tempPos);

        if (n == -1) {
          throw ByteExceptions.TRUNCATED_DATA;
        }

        totalBytesRead += n;
        tempPos += n;
      }

      return bytes;
    }

    return null;
  }

  /**
   * Reads the remaining data in small chunks from the {@link InputStream}.
   */
  private List<byte[]> readBytesSlowRemainingChunks(int sizeLeft) throws IOException {
    final List<byte[]> chunks = new ArrayList<>();

    while (sizeLeft > 0) {
      final byte[] chunk = new byte[Math.min(sizeLeft, DEFAULT_BUFFER_SIZE)];
      int tempPos = 0;

      while (tempPos < chunk.length) {
        final int n = input.read(chunk, tempPos, chunk.length - tempPos);

        if (n == -1) {
          throw ByteExceptions.TRUNCATED_DATA;
        }

        totalBytesRead += n;
        tempPos += n;
      }

      sizeLeft -= chunk.length;
      chunks.add(chunk);
    }

    return chunks;
  }

  @Override
  public boolean readBool() throws IOException {
    return readByte() != 0;
  }

  @Override
  public float readFloat() throws IOException {
    return Float.intBitsToFloat(readInt32());
  }

  @Override
  public double readDouble() throws IOException {
    return Double.longBitsToDouble(readInt64());
  }

  @Override
  public String readString() throws IOException {
    final int size = readInt32();

    if (size > 0 && size <= (bufferSize - pos)) {
      // We already have the bytes in a contiguous buffer,
      // so just copy directly from it.
      final String result = new String(buffer, pos, size, StandardCharsets.UTF_8);
      pos += size;

      return result;
    }

    if (size == 0) {
      return "";
    }

    if (size <= bufferSize) {
      refillBuffer(size);
      String result = new String(buffer, pos, size, StandardCharsets.UTF_8);
      pos += size;

      return result;
    }

    // Slow path: build a byte array first then copy it.
    return new String(readBytes(size), StandardCharsets.UTF_8);
  }

  @Override
  public void skipBytes(int size) throws IOException {
    if (size <= (bufferSize - pos) && size >= 0) {
      // We already have all the bytes we need.
      pos += size;
    } else {
      skipBytesSlow(size);
    }
  }

  private void skipBytesSlow(final int size) throws IOException {
    if (size < 0) {
      throw ByteExceptions.NEGATIVE_SIZE;
    }

    // Skipping more bytes than are in the buffer. First skip what we have.
    int tempPos = bufferSize - pos;
    pos = bufferSize;

    // Keep refilling the buffer until we get to the point we wanted to skip to.
    refillBuffer(1);

    while (size - tempPos > bufferSize) {
      tempPos += bufferSize;
      pos = bufferSize;
      refillBuffer(1);
    }

    pos = size - tempPos;
  }
}
