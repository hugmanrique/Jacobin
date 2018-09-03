package me.hugmanrique.jacobin.order;

import me.hugmanrique.jacobin.util.Unsigned;

import java.io.EOFException;
import java.io.IOException;

/**
 * A byte-order dependent byte stream reader interface.
 *
 * @author Hugo Manrique
 * @since 03/09/2018
 */
public interface ByteOrderReader {
    /**
     * Reads and returns one byte from the stream, zero-extends it to type {@code int}, and returns
     * the result, which is therefore in the range {@code 0} through {@code 255}.
     *
     * @return the unsigned 8-bit value read
     * @throws EOFException if the stream reaches the end before reading the byte
     * @throws IOException if an I/O error occurs
     */
    int readByte() throws IOException;

    /**
     * Reads two input bytes and returns a {@code short} value.
     *
     * @return the 16-bit value read
     * @throws EOFException if the stream reaches the end before reading all the bytes
     * @throws IOException if an I/O error occurs
     */
    short readInt16() throws IOException;

    /**
     * Reads two input bytes and returns an {@code int} value in the range {@code 0}
     * through {@code 65,535}.
     *
     * @return the unsigned 16-bit value read
     * @throws EOFException if the stream reaches the end before reading all the bytes
     * @throws IOException if an I/O error occurs
     */
    default int readUInt16() throws IOException {
        return Unsigned.unsignedInt16(readInt16());
    }

    /**
     * Reads four input bytes and returns an {@code int} value.
     *
     * @return the 32-bit value read
     * @throws EOFException if the stream reaches the end before reading all the bytes
     * @throws IOException if an I/O error occurs
     */
    int readInt32() throws IOException;

    /**
     * Reads four input bytes and returns a {@code long} value in the range {@code 0}
     * through {@code 4,294,967,295}.
     *
     * @return the unsigned 32-bit value read
     * @throws EOFException if the stream reaches the end before reading all the bytes
     * @throws IOException if an I/O error occurs
     */
    default long readUInt32() throws IOException {
        return Unsigned.unsignedInt32(readInt32());
    }

    /**
     * Reads eight input bytes and returns a {@code long} value.
     *
     * @return the 64-bit value read
     * @throws EOFException if the stream reaches the end before reading all the bytes
     * @throws IOException if an I/O error occurs
     */
    long readInt64() throws IOException;

    /**
     * Reads eight input bytes and returns a {@code long} value in the range {@code 0}
     * through {@code 18,446,744,073,709,551,615}. The {@link Long} class contains
     * methods to handle unsigned longs.
     *
     * @return the unsigned 64-bit value read
     * @throws EOFException if the stream reaches the end before reading all the bytes
     * @throws IOException if an I/O error occurs
     */
    default long readUInt64() throws IOException {
        return Unsigned.unsignedInt64(readInt64());
    }
}
