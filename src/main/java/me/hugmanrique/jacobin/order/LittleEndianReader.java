package me.hugmanrique.jacobin.order;

import java.io.IOException;

/**
 * Little-endian byte stream reader implementation.
 *
 * @author Hugo Manrique
 * @since 03/09/2018
 */
public interface LittleEndianReader extends ByteOrderReader {
    @Override
    default short readInt16() throws IOException {
        return (short) (((readByte() & 0xFF))
                | ((readByte() & 0xFF) << 8));
    }

    @Override
    default int readInt32() throws IOException {
        return ((readByte() & 0xFF))
                | ((readByte() & 0xFF) << 8)
                | ((readByte() & 0xFF) << 16)
                | ((readByte() & 0xFF) << 24);
    }

    @Override
    default long readInt64() throws IOException {
        return ((readByte() & 0xFFL)
                | ((readByte() & 0xFFL) << 8)
                | ((readByte() & 0xFFL) << 16)
                | ((readByte() & 0xFFL) << 24)
                | ((readByte() & 0xFFL) << 32)
                | ((readByte() & 0xFFL) << 40)
                | ((readByte() & 0xFFL) << 48)
                | ((readByte() & 0xFFL) << 56));
    }
}
