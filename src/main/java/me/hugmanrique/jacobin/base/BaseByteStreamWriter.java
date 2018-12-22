package me.hugmanrique.jacobin.base;

import me.hugmanrique.jacobin.writer.ByteStreamWriter;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.atomic.AtomicLong;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Hugo Manrique
 * @since 03/09/2018
 */
public abstract class BaseByteStreamWriter implements ByteStreamWriter {

    protected final OutputStream stream;
    protected final AtomicLong offset;

    public BaseByteStreamWriter(OutputStream stream) {
        this.stream = checkNotNull(stream, "stream");
        this.offset = new AtomicLong();
    }

    @Override
    public long getOffset() {
        return offset.get();
    }

    @Override
    public void skip(int size) throws IOException {
        checkArgument(size >= 0, "Attempted to skip a negative number of bytes");

        byte[] zeroed = new byte[size];

        stream.write(zeroed);
        offset.addAndGet(size);
    }

    @Override
    public void close() throws IOException {
        stream.close();
    }

    @Override
    public void write(byte[] data, int offset, int length) throws IOException {
        stream.write(data, offset, length);
        this.offset.addAndGet(length);
    }

    @Override
    public void write(byte[] data) throws IOException {
        stream.write(data);
        this.offset.addAndGet(data.length);
    }

    @Override
    public void writeByte(int value) throws IOException {
        stream.write(value);
        this.offset.incrementAndGet();
    }

    @Override
    public int writeUTF(String value) throws IOException {
        byte[] bytes = value.getBytes(StandardCharsets.UTF_8);
        write(bytes);

        return bytes.length;
    }
}
