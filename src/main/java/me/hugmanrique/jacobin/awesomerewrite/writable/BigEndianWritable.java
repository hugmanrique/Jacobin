package me.hugmanrique.jacobin.awesomerewrite.writable;

import me.hugmanrique.jacobin.awesomerewrite.Writable;

import java.io.IOException;
import java.nio.ByteOrder;

/**
 * A {@link ByteOrder#BIG_ENDIAN} {@link Writable} implementation.
 *
 * @author Hugo Manrique
 * @since 25/12/2018
 */
public interface BigEndianWritable extends BaseByteOrderWritable {

    @Override
    default ByteOrder getByteOrder() {
        return ByteOrder.BIG_ENDIAN;
    }

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
