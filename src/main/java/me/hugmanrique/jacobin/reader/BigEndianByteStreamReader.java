package me.hugmanrique.jacobin.reader;

import me.hugmanrique.jacobin.base.BaseByteStreamReader;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author Hugo Manrique
 * @since 02/09/2018
 */
public class BigEndianByteStreamReader extends BaseByteStreamReader {
    public BigEndianByteStreamReader(InputStream stream) {
        super(stream);
    }

    @Override
    public short readInt16() throws IOException {
        return (short) (((readByte() & 0xFF) << 8)
                | (readByte() & 0xFF));
    }

    @Override
    public int readInt32() throws IOException {
        return ((readByte() & 0xFF) << 24)
                | ((readByte() & 0xFF) << 16)
                | ((readByte() & 0xFF) << 8)
                | (readByte() & 0xFF);
    }

    @Override
    public long readInt64() throws IOException {
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
