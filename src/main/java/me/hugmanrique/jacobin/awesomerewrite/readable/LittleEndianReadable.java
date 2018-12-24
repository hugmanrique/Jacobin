package me.hugmanrique.jacobin.awesomerewrite.readable;

import me.hugmanrique.jacobin.awesomerewrite.Readable;

import java.io.IOException;
import java.nio.ByteOrder;

/**
 * A {@link ByteOrder#LITTLE_ENDIAN} {@link Readable} implementation.
 *
 * @author Hugo Manrique
 * @since 24/12/2018
 */
public interface LittleEndianReadable extends BaseByteOrderReadable {

    @Override
    default ByteOrder getByteOrder() {
        return ByteOrder.LITTLE_ENDIAN;
    }

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
