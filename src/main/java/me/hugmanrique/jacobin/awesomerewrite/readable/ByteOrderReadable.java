package me.hugmanrique.jacobin.awesomerewrite.readable;

import me.hugmanrique.jacobin.awesomerewrite.Readable;

import java.io.EOFException;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;

/**
 * An endianness (byte-order) dependent {@link Readable}.
 *
 * @author Hugo Manrique
 * @since 24/12/2018
 */
public interface ByteOrderReadable extends Readable {

    /**
     * The byte order of this Readable used to correctly read the bytes
     * of a multibyte value.
     *
     * @return the byte order of this Readable.
     * @see ByteOrder for an enumeration of byte orders.
     */
    ByteOrder getByteOrder();

    // Number reading methods

    /**
     * Reads two bytes from the stream, and returns a {@code short} value.
     *
     * @return the signed 16-bit value read.
     * @throws EOFException if the stream reaches the end before reading all the bytes.
     * @throws IOException if an I/O error occurs.
     */
    short readInt16() throws IOException;

    /**
     * Reads two bytes from the stream, zero-extends them to type {@code int},
     * and returns the result, which is therefore in the range {@code 0}
     * through {@code 2<sup>16</sup>-1}.
     *
     * @return the unsigned 16-bit value read.
     * @throws EOFException if the stream reaches the end before reading all the bytes.
     * @throws IOException if an I/O error occurs.
     */
    int readUInt16() throws IOException;

    /**
     * Reads four bytes from the stream, and returns a {@code int} value.
     *
     * @return the signed 32-bit value read.
     * @throws EOFException if the stream reaches the end before reading all the bytes.
     * @throws IOException if an I/O error occurs.
     */
    int readInt32() throws IOException;

    /**
     * Reads four bytes from the stream, zero-extends them to type {@code long},
     * and returns the result, which is therefore in the range {@code 0}
     * through {@code 2<sup>32</sup>-1}.
     *
     * @return the unsigned 32-bit value read.
     * @throws EOFException if the stream reaches the end before reading all the bytes.
     * @throws IOException if an I/O error occurs.
     */
    long readUInt32() throws IOException;

    /**
     * Reads eight bytes from the stream, and returns a {@code long} value.
     *
     * @return the signed 64-bit value read.
     * @throws EOFException if the stream reaches the end before reading all the bytes.
     * @throws IOException if an I/O error occurs.
     */
    long readInt64() throws IOException;

    /**
     * Reads eight bytes from the stream and returns a {@code long} value in the
     * range {@link Long#MIN_VALUE} through {@link Long#MAX_VALUE}.
     *
     * <p>The returned unsigned value can be handled using the provided utility
     * methods available in the {@link Long} class.
     *
     * @return a signed 64-bit value representing the unsigned 64-bit value read.
     * @throws EOFException if the stream reaches the end before reading all the bytes.
     * @throws IOException if an I/O error occurs.
     */
    long readUInt64() throws IOException;

    /**
     * Reads eight bytes from the stream and returns a {@link BigInteger} value in
     * the range {@code 0} through {@code 2<sup>64</sup>-1}.
     *
     * @return the unsigned 64-bit value read.
     * @throws EOFException if the stream reaches the end before reading all the bytes.
     * @throws IOException if an I/O error occurs.
     */
    BigInteger safeReadUInt64() throws IOException;

    // String reading methods

    /**
     * Reads {@code length} bytes from the stream and returns their UTF-8 {@link String}
     * representation. Note that the length of the returned {@link String} depends on the
     * {@link StandardCharsets#UTF_8} charset, and hence may not be equal to {@code length}.
     *
     * @param length the maximum number of bytes to read.
     * @return the Unicode string read.
     * @throws EOFException if the stream reaches the end before reading all the bytes.
     * @throws IOException if an I/O error occurs.
     */
    String readUTF(int length) throws IOException;

    // TODO Add a readUTF() method that reads until a null terminator (\0) is read
}
