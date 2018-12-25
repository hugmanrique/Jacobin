package me.hugmanrique.jacobin.awesomerewrite;

import java.io.IOException;

/**
 * A {@code Writable} provides methods for writing bytes to a binary stream.
 * These methods write bytes starting at the current {@code offset} and advance the
 * {@code offset} past the bytes written.
 *
 * <p>It is generally true of all the methods that write bytes that if a byte cannot be
 * written for any reason, an {@link IOException} is thrown.
 *
 * @author Hugo Manrique
 * @since 24/12/2018
 */
public interface Writable {

    // Offset methods

    /**
     * Returns the current offset, i.e. this Writable's position at which the
     * next write occurs.
     *
     * @return the offset from the beginning of the data.
     * @throws IOException if an I/O error occurs.
     */
    long getOffset() throws IOException;

    /**
     * Sets the offset, measured from the beginning of the data, at which the
     * next write occurs.
     *
     * <p>Going back to a previous offset, i.e. {@code {@link #getOffset()} - newPosition}
     * is positive is only supported if the underlying stream supports resetting.
     *
     * @param newPosition the new position value; must be non-negative and
     *                    no larger than the internal stream's size.
     * @throws IllegalArgumentException if {@code newPosition} is non-positive.
     * @throws UnsupportedOperationException if the new offset is less than {@link #getOffset()}
     *                                       and this Writable does not support negative skips.
     * @throws IOException if the new position is larger than the internal stream's size,
     *                     the internal stream does not support seek operations,
     *                     or an I/O error occurs.
     * @see #supportsNegativeSkips() to check whether this Writable supports negative skips.
     */
    void setOffset(long newPosition) throws IOException;

    /**
     * Writes {@code offset} zero bytes ({@code 0x0}) of data to the internal stream.
     * This method will block until the full amount has been skipped. This method
     * does not close the internal stream.
     *
     * <p>Negative skips are supported only if the underlying stream supports resetting.
     *
     * @param offset the number of bytes to skip.
     * @throws UnsupportedOperationException if {@code offset} is negative and this Writable
     *                                       does not supports negative skips.
     * @throws IOException if the internal stream does not support seek operations,
     *                     or an I/O error occurs.
     * @see #supportsNegativeSkips() to check whether this Writable supports negative skips.
     */
    void skip(long offset) throws IOException;

    /**
     * Sets the offset, measured from the beginning of the data, to zero.
     *
     * @throws UnsupportedOperationException if this Writable does not support negative skips.
     * @throws IOException if an I/O error occurs.
     * @see #supportsNegativeSkips() to check whether this Writable supports negative skips.
     */
    void reset() throws IOException;

    /**
     * Returns whether this Writable supports going back to already written positions
     * by checking if the underlying stream supports resetting.
     *
     * @return whether this Writable supports negative skips or not.
     */
    boolean supportsNegativeSkips();

    // Primitive write methods

    /**
     * Writes the 8 low-order bits of {@code value} to the stream. The 24-high order
     * bits of {@code value} are ignored.
     *
     * @param value the 8-bit value to be written.
     * @throws IOException if an I/O error occurs
     */
    void writeByte(int value) throws IOException;

    /**
     * Writes {@code length} bytes from the {@code data} array, in order, to the stream.
     * This method does not close the internal stream. If {@code length} is zero, then no
     * bytes are written. Otherwise, the byte {@code data[offset]} is written first, then
     * {@code data[offset + 1]}, and so on; the last byte written is
     * {@code data[offset + length - 1]}.
     *
     * <p>This method blocks until {@code length} bytes of data have been written to the stream.
     *
     * @param data the array where the data is read from.
     * @param offset the start offset in array {@code data} at which the data is read.
     * @param length the number of bytes to write.
     * @throws IOException if an I/O error occurs.
     * @throws NullPointerException if {@code data} is null.
     * @throws IndexOutOfBoundsException if {@code offset} or {@code length} is negative, or
     *                                   {@code offset + length} is greater than the length of
     *                                   the {@code data} array.
     */
    void write(byte[] data, int offset, int length) throws IOException;

    /**
     * Writes all the bytes in the {@code data} array, in order, to the stream.
     * This method does not close the internal stream. If {@code data.length} is zero, then
     * no bytes are written. Otherwise, the byte {@code data[0]} is written first, then
     * {@code data[1]}, and so on; the last byte written is {@code data[data.length - 1]}.
     *
     * <p>This method blocks until {@code length} bytes of data have been written to the stream.
     *
     * @param data the array where the data is read from.
     * @throws IOException if an I/O error occurs.
     * @throws NullPointerException if {@code data} is null.
     */
    void write(byte[] data) throws IOException;
}
