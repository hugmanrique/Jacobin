package me.hugmanrique.jacobin.readable;

import me.hugmanrique.jacobin.util.UnsignedInts;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;

/**
 * @author Hugo Manrique
 * @since 24/12/2018
 */
public interface BaseByteOrderReadable extends ByteOrderReadable {

    @Override
    default int readUInt16() throws IOException {
        return UnsignedInts.unsignedInt16(readInt16());
    }

    @Override
    default long readUInt32() throws IOException {
        return UnsignedInts.unsignedInt32(readInt32());
    }

    @Override
    default long readUInt64() throws IOException {
        return readInt64();
    }

    @Override
    default BigInteger safeReadUInt64() throws IOException {
        return UnsignedInts.unsignedInt64(readInt64());
    }

    @Override
    default String readUTF(int length) throws IOException {
        byte[] utfBytes = new byte[length];

        read(utfBytes, 0, length);

        return new String(utfBytes, StandardCharsets.UTF_8);
    }
}
