package me.hugmanrique.jacobin.order;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * A byte-order dependent byte stream writer interface.
 *
 * @author Hugo Manrique
 * @since 03/09/2018
 */
public interface ByteOrderWriter {

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
    default void writeUInt16(int value) throws IOException {
        writeInt16((short) value);
    }

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
    default void writeUInt32(long value) throws IOException {
        writeInt32((int) value);
    }

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
    default void writeUInt64(long value) throws IOException {
        // TODO Print warning?
        writeInt64(value);
    }

    /**
     * Writes the UTF-8 {@link String} byte representation of every character
     * in the string {@code value}. Note that the number of written bytes
     * depends on the {@link StandardCharsets#UTF_8} charset, and hence may
     * not be equal to {@code string.length()}.
     *
     * @param value the string to be written
     * @return the number of bytes written
     * @throws IOException if an I/O error occurs
     */
    int writeUTF(String value) throws IOException;
}
