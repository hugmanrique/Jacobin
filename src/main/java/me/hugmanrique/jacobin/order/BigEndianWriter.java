package me.hugmanrique.jacobin.order;

import java.io.IOException;

/**
 * Big-endian byte stream writer implementation.
 *
 * @author Hugo Manrique
 * @since 03/09/2018
 */
public interface BigEndianWriter extends ByteOrderWriter {

    @Override
    default void writeInt16(short value) throws IOException {
        writeByte((value >> 8) & 0xFF);
        writeByte(value & 0xFF);
    }

    @Override
    default void writeInt32(int value) throws IOException {
        writeByte((value >> 24) & 0xFF);
        writeByte((value >> 16) & 0xFF);
        writeByte((value >> 8) & 0xFF);
        writeByte(value & 0xFF);
    }

    @Override
    default void writeInt64(long value) throws IOException {
        writeByte((byte) ( value >> 56) & 0xFF);
        writeByte((byte) ( value >> 48) & 0xFF);
        writeByte((byte) ( value >> 40) & 0xFF);
        writeByte((byte) ( value >> 32) & 0xFF);
        writeByte((byte) ( value >> 24) & 0xFF);
        writeByte((byte) ( value >> 16) & 0xFF);
        writeByte((byte) ( value >> 8) & 0xFF);
        writeByte((byte) (value & 0xFF));
    }
}
