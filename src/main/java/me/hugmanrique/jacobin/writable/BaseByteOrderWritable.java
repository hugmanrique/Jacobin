package me.hugmanrique.jacobin.writable;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * @author Hugo Manrique
 * @since 25/12/2018
 */
public interface BaseByteOrderWritable extends ByteOrderWritable {

    @Override
    default void writeUInt16(int value) throws IOException {
        writeInt16((short) value);
    }

    @Override
    default void writeUInt32(long value) throws IOException {
        writeInt32((int) value);
    }

    @Override
    default void writeUInt64(long value) throws IOException {
        writeInt64(value);
    }

    @Override
    default int writeUTF(String value) throws IOException {
        byte[] utfBytes = value.getBytes(StandardCharsets.UTF_8);

        write(utfBytes);

        return utfBytes.length;
    }
}
