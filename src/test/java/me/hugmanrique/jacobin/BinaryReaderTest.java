package me.hugmanrique.jacobin;

import junit.framework.TestCase;
import me.hugmanrique.jacobin.reader.LittleEndianBinaryReader;

import java.io.ByteArrayInputStream;
import java.io.FilterInputStream;
import java.io.IOException;
import java.util.Arrays;

/**
 * @author Hugo Manrique
 * @since 10/06/2018
 */
public class BinaryReaderTest extends TestCase {
  private static byte[] bytes(int... bytesAsInts) {
    byte[] bytes = new byte[bytesAsInts.length];

    for (int i = 0; i < bytesAsInts.length; i++) {
      bytes[i] = (byte) bytesAsInts[i];
    }

    return bytes;
  }

  /**
   * An InputStream which limits the number of bytes it reads at a time.
   * We use this to make sure the BinaryReader doesn't screw up when reading
   * in small blocks.
   */
  private static final class SmallBlockInputStream extends FilterInputStream {
    private final int blockSize;

    public SmallBlockInputStream(byte[] data, int blockSize) {
      super(new ByteArrayInputStream(data));
      this.blockSize = blockSize;
    }

    @Override
    public int read(byte[] b) throws IOException {
      return super.read(b, 0, Math.min(b.length, blockSize));
    }

    @Override
    public int read(byte[] b, int off, int len) throws IOException {
      return super.read(b, off, Math.min(len, blockSize));
    }
  }

  private static BinaryReader newReader(byte[] data, int blockSize) {
    return new LittleEndianBinaryReader(new SmallBlockInputStream(data, blockSize));
  }

  private void assertDataConsumed(byte[] data, BinaryReader reader) throws IOException {
    assertEquals(data.length, reader.getTotalBytesRead());
    assertTrue(reader.isAtEnd());
  }

  private void assertReadLittleEndian16(byte[] data, short value) throws Exception {
    for (int blockSize = 1; blockSize <= 16; blockSize *= 2) {
      BinaryReader reader = newReader(data, blockSize);

      assertEquals(value, reader.readInt16());
      assertTrue(reader.isAtEnd());
    }
  }

  private void assertReadLittleEndian32(byte[] data, int value) throws Exception {
    for (int blockSize = 1; blockSize <= 16; blockSize *= 2) {
      BinaryReader reader = newReader(data, blockSize);

      assertEquals(value, reader.readInt32());
      assertTrue(reader.isAtEnd());
    }
  }

  private void assertReadLittleEndian64(byte[] data, long value) throws Exception {
    for (int blockSize = 1; blockSize <= 16; blockSize *= 2) {
      BinaryReader reader = newReader(data, blockSize);

      assertEquals(value, reader.readInt64());
      assertTrue(reader.isAtEnd());
    }
  }

  public void testReadLittleEndian() throws Exception {
    assertReadLittleEndian16(bytes(0x34, 0x12), (short) 0x1234);
    assertReadLittleEndian16(bytes(0xcd, 0xab), (short) 0xabcd);

    assertReadLittleEndian32(bytes(0x78, 0x56, 0x34, 0x12), 0x12345678);
    assertReadLittleEndian32(bytes(0xf0, 0xde, 0xbc, 0x9a), 0x9abcdef0);

    assertReadLittleEndian64(
      bytes(0xf0, 0xde, 0xbc, 0x9a, 0x78, 0x56, 0x34, 0x12), 0x123456789abcdef0L);
    assertReadLittleEndian64(
      bytes(0x78, 0x56, 0x34, 0x12, 0xf0, 0xde, 0xbc, 0x9a), 0x9abcdef012345678L);
  }

  public void testReadLargeByteStringFromInputStream() throws Exception {
    byte[] bytes = new byte[1024 * 1024];
    for (int i = 0; i < bytes.length; i++) {
      bytes[i] = (byte) (i & 0xFF);
    }

    BinaryReader reader = new LittleEndianBinaryReader(
      new ByteArrayInputStream(bytes) {
        @Override
        public synchronized int available() {
          return 0;
        }
      }
    );

    byte[] result = reader.readBytes(bytes.length);
    assertTrue(Arrays.equals(bytes, result));
  }
}
