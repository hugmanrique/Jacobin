package me.hugmanrique.jacobin.writer;

import me.hugmanrique.jacobin.ByteStreamWriter;
import me.hugmanrique.jacobin.base.BaseByteStreamWriter;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Little-endian implementation of {@link ByteStreamWriter}.
 *
 * @author Hugo Manrique
 * @since 03/09/2018
 */
public class LittleEndianByteStreamWriter extends BaseByteStreamWriter {
    public LittleEndianByteStreamWriter(OutputStream stream) {
        super(stream);
    }

    @Override
    public void writeInt16(short value) throws IOException {
        writeByte(value & 0xFF);
        writeByte((value >> 8) & 0xFF);
    }

    @Override
    public void writeInt32(int value) throws IOException {
        writeByte(value & 0xFF);
        writeByte((value >> 8) & 0xFF);
        writeByte((value >> 16) & 0xFF);
        writeByte((value >> 24) & 0xFF);
    }

    @Override
    public void writeInt64(long value) throws IOException {
        writeByte((byte) (value & 0xFF));
        writeByte((byte) ( value >> 8) & 0xFF);
        writeByte((byte) ( value >> 16) & 0xFF);
        writeByte((byte) ( value >> 24) & 0xFF);
        writeByte((byte) ( value >> 32) & 0xFF);
        writeByte((byte) ( value >> 40) & 0xFF);
        writeByte((byte) ( value >> 48) & 0xFF);
        writeByte((byte) ( value >> 56) & 0xFF);
    }
}
