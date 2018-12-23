package me.hugmanrique.jacobin.reader;

import com.google.errorprone.annotations.CanIgnoreReturnValue;
import me.hugmanrique.jacobin.order.ByteOrderReader;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

/**
 * Provides a way to read data from a stream.
 *
 * @author Hugo Manrique
 * @since 02/09/2018
 */
public interface ByteStreamReader extends ByteOrderReader, Closeable {

    /**
     * Returns this reader's position, i.e. the offset of the internal stream.
     *
     * @return the position of this reader
     * @throws IOException if an I/O error occurs
     */
    long getOffset() throws IOException;

    /**
     * Sets this reader's position, i.e. the offset of the internal stream.
     *
     * @param newPosition the new position value; must be non-negative and no
     *                    larger than the internal stream's size.
     * @throws IOException if the new position is larger than the internal stream's size,
     *                     the internal stream does not support seek, or an I/O error occurs
     */
    void setOffset(long newPosition) throws IOException;

    /**
     * Discards {@code size} bytes of data from the internal stream. This method will block
     * until the full amount has been skipped. Does not close the internal stream.
     *
     * @param size the number of bytes to skip
     * @throws IOException if this reader doesn't support negative skips
     */
    void skip(long size) throws IOException;

    /**
     * Resets this reader's position, i.e. the offset of the internal stream is set to zero.
     *
     * @throws IOException if an I/O error occurs
     */
    void reset() throws IOException;

    /**
     * Returns an estimate of the number of bytes that can be reade from the
     * internal stream without blocking by the next read (or skip) invocation.
     *
     * @throws IOException if an I/O error occurs
     */
    int available() throws IOException;

    /**
     * Reads some bytes from the internal stream and stores them in the {@code buffer} array.
     * This method blocks until {@code length} bytes of input data have been read into the array,
     * or end of file is detected. The number of bytes read is returned, possibly zero.
     * This method does not close the internal stream.
     *
     * A caller can detect EOF if the number of bytes read is less than {@code length}. All subsequent
     * calls on the same stream will return {@code -1}.
     *
     * @param buffer the buffer into which the data is read
     * @param offset the start offset in array {@code buffer} at which the data is written
     * @param length the maximum number of bytes to read
     * @return the number of bytes read, or {@code -1} if the end of the stream has been reached
     * @throws IOException if an I/O error occurs
     * @throws NullPointerException if {@code buffer} is null
     * @throws IndexOutOfBoundsException if {@code offset} is negative, or {@code length} is negative, or
     *                                   {@code offset + length} is greater than the length of the array
     *                                   {@code buffer}.
     */
    @CanIgnoreReturnValue
    int read(byte[] buffer, int offset, int length) throws IOException;

    /**
     * Reads some bytes from the internal stream and stores them in the {@code buffer} array.
     * This method blocks until {@code buffer.length} bytes of input data have been read into the
     * array, or end of file is detected. The number of bytes read is returned, possibly zero.
     * This method does not close the internal stream.
     *
     * A caller can detect EOF if the number of bytes read is less than the {@code buffer} array's length.
     * All subsequent calls on the same stream will return {@code -1}.
     *
     * @param buffer the buffer into which the data is read
     * @return the number of bytes read, or {@code -1} if the end of the stream has been reached
     * @throws IOException if an I/O error occurs
     * @throws NullPointerException if {@code buffer} is null
     */
    @CanIgnoreReturnValue
    default int read(byte[] buffer) throws IOException {
        return read(buffer, 0, buffer.length);
    }

    /**
     * Returns whether this reader supports going back to an already read
     * byte offset by checking if the underlying {@link InputStream} supports
     * the {@code mark} and {@code reset} methods.
     *
     * @see #getOffset()
     * @see InputStream#markSupported()
     */
    boolean supportsNegativeSkips();

    @Override
    default String readUTF(int length) throws IOException {
        byte[] bytes = new byte[length];
        read(bytes, 0, length);

        return new String(bytes, StandardCharsets.UTF_8);
    }
}
