package me.hugmanrique.jacobin;

import me.hugmanrique.jacobin.order.ByteOrderWriter;

import java.io.Closeable;
import java.io.IOException;

/**
 * Provides a way to write data into a stream.
 *
 * @author Hugo Manrique
 * @since 03/09/2018
 */
public interface ByteStreamWriter extends ByteOrderWriter, Closeable {
    /**
     * Returns this writer's position, i.e. the offset of the internal stream.
     *
     * @return the position of this writer
     * @throws IOException if an I/O error occurs
     */
    long getOffset() throws IOException;

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
}
