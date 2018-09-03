package me.hugmanrique.jacobin;

import java.io.Closeable;
import java.io.IOException;

/**
 * Provides a way to write data into a stream.
 *
 * @author Hugo Manrique
 * @since 03/09/2018
 */
public interface ByteStreamWriter extends Closeable {
    /**
     * Returns this writer's position, i.e. the offset of the internal stream.
     *
     * @return the position of this writer
     */
    long getOffset();

    /**
     * Writes {@code 0x0 size} bytes of data to the internal stream. Does not close the
     * internal stream.
     *
     * @param size the non-negative number of bytes to null and skip
     * @throws IOException if an I/O error occurs
     * @throws IllegalArgumentException if {@code size} is negative
     */
    void skip(int size) throws IOException;

    /**
     * Writes {@code length} bytes from the {@code data} array, in order, to the internal stream.
     *
     * @param data the data to write to the stream
     * @param offset an int specifying the offset in the data
     * @param length an int specifying the number of bytes to write
     * @throws IOException if an I/O error occurs
     * @throws NullPointerException if {@code data} is null
     * @throws IndexOutOfBoundsException if {@code offset} is negative, or {@code length} is negative, or
     *                                   {@code offset + length} is greater than the length of the array
     *                                   {@code data}.
     */
    void write(byte[] data, int offset, int length) throws IOException;

    /**
     * Writes all the bytes in array {@code data} to the internal stream.
     *
     * @param data the data to write to the stream
     * @throws IOException if an I/O error occurs
     * @throws NullPointerException if {@code data} is null
     */
    void write(byte[] data) throws IOException;

    /**
     * Writes the 8 low-order bits of {@code value} to the internal stream.
     * The 24-high order bits of {@code value} are ignored.
     *
     * @param value the 8-bit value to be written
     * @throws IOException if an I/O error occurs
     */
    void writeByte(int value) throws IOException;

    /**
     * Writes two bytes to the internal stream.
     *
     * @param value the 16-bit value to be written
     * @throws IOException if an I/O error occurs
     */
    void writeInt16(short value) throws IOException;

    /**
     * Writes two bytes in the range {@code 0} through {@code 65,535}
     * to the internal stream.
     *
     * @param value the unsigned 16-bit value to be written
     * @throws IOException if an I/O error occurs
     */
    void writeUInt16(int value) throws IOException;

    /**
     * Writes four bytes to the internal stream.
     *
     * @param value the 32-bit value to be written
     * @throws IOException if an I/O error occurs
     */
    void writeInt32(int value) throws IOException;

    /**
     * Writes four bytes in the range {@code 0} through
     * {@code 4,294,967,295} to the internal stream.
     *
     * @param value the unsigned 32-bit value to be written
     * @throws IOException if an I/O error occurs
     */
    void writeUInt32(long value) throws IOException;

    /**
     * Writes eight bytes to the internal stream.
     *
     * @param value the 64-bit value to be written
     * @throws IOException if an I/O error occurs
     */
    void writeInt64(long value) throws IOException;

    /**
     * Writes eight bytes in the range {@code 0} through
     * {@code 9,223,372,036,854,775,807} to the internal stream.
     *
     * Java doesn't have a native {@code uint64} primitive type so values
     * above {@code 2^63-1} are not guaranteed to be written correctly.
     *
     * @param value the unsigned 64-bit value to be written
     * @throws IOException if an I/O error occurs
     */
    void writeUInt64(long value) throws IOException;
}
