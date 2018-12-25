package me.hugmanrique.jacobin.writable;

import com.google.errorprone.annotations.CanIgnoreReturnValue;
import me.hugmanrique.jacobin.Writable;

import java.io.IOException;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;

/**
 * An endianness (byte-order) dependent {@link Writable}.
 *
 * @author Hugo Manrique
 * @since 25/12/2018
 */
public interface ByteOrderWritable extends Writable {

    /**
     * The byte order of this Writable used to correctly read the bytes
     * of a multibyte value.
     *
     * @return the byte order of this Writable.
     * @see ByteOrder for an enumeration of byte orders.
     */
    ByteOrder getByteOrder();

    // Number writing methods

    /**
     * Writes two bytes to the stream.
     *
     * @param value the signed 16-bit value to be written.
     * @throws IOException if an I/O error occurs.
     */
    void writeInt16(short value) throws IOException;

    /**
     * Writes the 16 low-order bits of {@code value} to the stream.
     * The 16 high-order bits of {@code value} are ignored.
     *
     * @param value the unsigned 16-bit value to be written.
     * @throws IOException if an I/O error occurs.
     */
    void writeUInt16(int value) throws IOException;

    /**
     * Writes four bytes to the stream.
     *
     * @param value the signed 32-bit value to be written.
     * @throws IOException if an I/O error occurs.
     */
    void writeInt32(int value) throws IOException;

    /**
     * Writes the 32 low-order bits of {@code value} to the stream.
     * The 32 high-order bits of {@code value} are ignored.
     *
     * @param value the unsigned 32-bit value to be written.
     * @throws IOException if an I/O error occurs.
     */
    void writeUInt32(long value) throws IOException;

    /**
     * Writes eight bytes to the stream.
     *
     * @param value the signed 64-bit value to be written.
     * @throws IOException if an I/O error occurs.
     */
    void writeInt64(long value) throws IOException;

    /**
     * Writes the 64 bits of {@code value} to the stream. The passed {@code value}
     * is in the range {@link Long#MIN_VALUE} through {@link Long#MAX_VALUE}.
     *
     * @param value a signed 64-bit value representing the unsigned 64-bit value to be written.
     * @throws IOException if an I/O error occurs.
     */
    void writeUInt64(long value) throws IOException;

    // String writing methods

    /**
     * Writes the UTF-8 {@link String} byte representation of every character in
     * the string {@code value}. Note that the number of written bytes depends on
     * the {@link StandardCharsets#UTF_8} charset, and hence may not be equal to
     * {@code value.length(}.
     *
     * @param value the string to be written.
     * @return the number of bytes written.
     * @throws IOException if an I/O error occurs.
     */
    @CanIgnoreReturnValue
    int writeUTF(String value) throws IOException;
}
