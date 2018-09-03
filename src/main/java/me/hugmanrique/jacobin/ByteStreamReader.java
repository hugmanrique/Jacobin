package me.hugmanrique.jacobin;

import com.google.errorprone.annotations.CanIgnoreReturnValue;
import me.hugmanrique.jacobin.order.ByteOrderReader;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;

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
     * @throws IOException if the new position is larger than the internal stream's size
     */
    void setOffset(long newPosition) throws IOException;

    /**
     * Discards {@code size} bytes of data from the internal stream. This method will block
     * until the full amount has been skipped. Does not close the internal stream.
     *
     * @param size the number of bytes to skip
     * @throws IOException if an I/O error occurs
     */
    void skip(long size) throws IOException;

    /**
     * Returns an estimate of the number of bytes that can be reade from the
     * internal stream without blocking by the next read (or skip) invocation.
     *
     * @throws IOException if an I/O error occurs
     */
    int available() throws IOException;

    /**
     * Reads some bytes from the internal stream and stores them into the buffer array {@code buffer}.
     * This method blocks until {@code length} bytes of input data have been read into the array, or
     * end of file is detected. The number of bytes read is returned, possibly zero. Does not close
     * the internal stream.
     *
     * A caller can detect EOF if the number of bytes read is less than {@code length}. All subsequent
     * calls on the same stream will return zero.
     *
     * @param buffer the buffer into which the data is read
     * @param offset an int specifying the offset into the data
     * @param length an int specifying the number of bytes to read
     * @return the number of bytes read
     * @throws IOException if an I/O error occurs
     * @throws NullPointerException if {@code buffer} is null
     * @throws IndexOutOfBoundsException if {@code offset} is negative, or {@code length} is negative, or
     *                                   {@code offset + length} is greater than the length of the array
     *                                   {@code buffer}.
     */
    @CanIgnoreReturnValue
    int read(byte[] buffer, int offset, int length) throws IOException;

    /**
     * Returns whether this reader supports going back to an already read
     * byte offset by checking if the underlaying {@link InputStream} supports
     * marks.
     *
     * @see #getOffset()
     * @see InputStream#markSupported()
     */
    boolean supportsNegativeSkips();
}
