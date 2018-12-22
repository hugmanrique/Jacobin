package me.hugmanrique.jacobin.order;

import java.io.IOException;

/**
 * Big-endian byte stream reader implementation.
 *
 * @author Hugo Manrique
 * @since 03/09/2018
 */
public interface BigEndianReader extends ByteOrderReader {

    @Override
    default short readInt16() throws IOException {
        return (short) (((readByte() & 0xFF) << 8)
                | (readByte() & 0xFF));
    }

    @Override
    default int readInt32() throws IOException {
        return ((readByte() & 0xFF) << 24)
                | ((readByte() & 0xFF) << 16)
                | ((readByte() & 0xFF) << 8)
                | (readByte() & 0xFF);
    }

    @Override
    default long readInt64() throws IOException {
        return ((readByte() & 0xFFL) << 56)
                | ((readByte() & 0xFFL) << 48)
                | ((readByte() & 0xFFL) << 40)
                | ((readByte() & 0xFFL) << 32)
                | ((readByte() & 0xFFL) << 24)
                | ((readByte() & 0xFFL) << 16)
                | ((readByte() & 0xFFL) << 8)
                | (readByte() & 0xFFL);
    }
}
