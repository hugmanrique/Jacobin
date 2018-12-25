package me.hugmanrique.jacobin.awesomerewrite;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;

/**
 * A {@code Readable} provides methods for reading bytes from a binary stream.
 * These methods read bytes starting at the current {@code offset} and advance the
 * {@code offset} past the bytes read.
 *
 * <p>It is generally true of all the methods that read bytes that if end of file is reached
 * before the desired number of bytes has been read, an {@link EOFException} is thrown.
 * If any byte cannot be read for any other reason, an {@link IOException} is thrown.
 *
 * @author Hugo Manrique
 * @since 23/12/2018
 */
public interface Readable {

    // Offset methods

    /**
     * Returns the current offset, i.e. this Readable's position at which the
     * next read occurs.
     *
     * @return the offset from the beginning of the data.
     * @throws IOException if an I/O error occurs.
     */
    long getOffset() throws IOException;

    /**
     * Sets the offset, measured from the beginning of the data, at which the
     * next read occurs.
     *
     * <p>Going back to a previous offset, i.e. {@code {@link #getOffset()} - newPosition}
     * is positive is only supported if the underlying {@link InputStream} supports the
     * {@code mark} and {@code reset} methods.
     *
     * @param newPosition the new position value; must be non-negative and
     *                    no larger than the internal stream's size.
     * @throws IllegalArgumentException if {@code newPosition} is non-positive.
     * @throws UnsupportedOperationException if the new offset is less than {@link #getOffset()}
     *                                       and this Readable does not support negative skips.
     * @throws IOException if the new position is larger than the internal stream's size,
     *                     the internal stream does not support seek operations,
     *                     or an I/O error occurs.
     * @see #supportsNegativeSkips() to check whether this Readable supports negative skips.
     */
    void setOffset(long newPosition) throws IOException;

    /**
     * Discards {@code offset} bytes of data from the internal stream. This method will
     * block until the full amount has been skipped. This method does not close the
     * internal stream.
     *
     * <p>Negative skips are supported only if the underlying {@link InputStream} supports
     * the {@code mark} and {@code reset} methods.
     *
     * @param offset the number of bytes to skip.
     * @throws IllegalArgumentException if {@code offset} is negative and the resulting offset
     *                                  from this operation is negative.
     * @throws UnsupportedOperationException if {@code offset} is negative and this Readable
     *                                       does not support negative skips.
     * @throws IOException if the internal stream does not support seek operations,
     *                     or an I/O error occurs.
     * @see #supportsNegativeSkips() to check whether this Readable supports negative skips.
     */
    void skip(long offset) throws IOException;

    /**
     * Sets the offset, measured from the beginning of the data, to zero.
     *
     * @throws UnsupportedOperationException if this Readable does not support negative skips.
     * @throws IOException if an I/O error occurs.
     * @see #supportsNegativeSkips() to check whether this Readable supports negative skips.
     */
    void reset() throws IOException;

    /**
     * Returns whether this Readable supports going back to already read positions by
     * checking if the underlying {@link InputStream} supports the {@code mark} and
     * {@code reset} methods.
     *
     * @return whether this Readable supports negative skips or not.
     * @see InputStream#markSupported()
     */
    boolean supportsNegativeSkips();

    /**
     * Returns an estimate of the number of bytes that can be read from the
     * underlying stream without block by the next read (or skip) invocation.
     *
     * <p>Note that while some implementations of {@link InputStream} will return the
     * total number of bytes in the stream, many will not. It is never correct to
     * use the return value of this method to allocate a buffer intended to hold
     * all data in this Readable.
     *
     * @return an estimate of the number of bytes that can be read (or skipped over)
     *         without blocking or {@code 0} when it reaches the end of the stream.
     * @throws IOException if an I/O error occurs.
     */
    int available() throws IOException;

    // Primitive read methods

    /**
     * Reads and returns one byte from the stream, zero-extends it to type {@code int}, and
     * returns the result, which is therefore in the range {@code 0} through {@code 255}.
     *
     * @return the unsigned 8-bit value read.
     * @throws EOFException if the stream reaches the end before reading the byte.
     * @throws IOException if an I/O error occurs.
     */
    int readByte() throws IOException;

    /**
     * Reads {@code length} bytes from the stream and stores them in the {@code buffer} array.
     * This method does not close the internal stream.
     *
     * <p>This method blocks until {@code length} bytes of data have been read from the stream,
     * or end of file is detected.
     *
     * <p>The number of read bytes is returned, possibly zero. A caller can detect EOF if
     * the number of bytes read is less than {@code length}. All subsequent read calls will
     * return {@code -1}.
     *
     * @param buffer the buffer into which the data is read.
     * @param offset the start offset in array {@code buffer} at which the data is written.
     * @param length the maximum number of bytes to read.
     * @return the number of bytes read, or {@code -1} if the end of the stream has been reached.
     * @throws IOException if an I/O error occurs.
     * @throws NullPointerException if {@code buffer} is null.
     * @throws IndexOutOfBoundsException if {@code offset} or {@code length} is negative, or
     *                                   {@code offset + length} is greater than the length of
     *                                   the {@code buffer} array.
     */
    int read(byte[] buffer, int offset, int length) throws IOException;

    /**
     * Reads {@code buffer.length} bytes from the stream and stores them in the {@code buffer}
     * array. This method does not close the internal stream.
     *
     * <p>This method blocks until {@code buffer.length} bytes of input data have been read from
     * the stream, or end of file is detected.
     *
     * <p>The number of read bytes is returned, possibly zero. A caller can detect EOF if the
     * number of bytes read is less than {@code buffer.length}. All subsequent read calls will
     * return {@code -1}.
     *
     * @param buffer the buffer into which the data is read.
     * @return the number of bytes read, or {@code -1} if the end of the stream has been reached.
     * @throws IOException if an I/O error occurs.
     */
    int read(byte[] buffer) throws IOException;
}
