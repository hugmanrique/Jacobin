package me.hugmanrique.jacobin;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * Combined byte streams reader and writer implementations.
 *
 * @author Hugo Manrique
 * @since 03/09/2018
 */
public abstract class InOutByteStream implements ByteStreamReader, ByteStreamWriter, Closeable {
    private final RandomAccessFile file;

    public InOutByteStream(File file, boolean synchronousWrites) throws IOException {
        this.file = new RandomAccessFile(file, synchronousWrites ? "rwd" : "rw");
    }

    public InOutByteStream(File file) throws IOException {
        this(file, false);
    }

    @Override
    public long getOffset() throws IOException {
        return file.getFilePointer();
    }

    @Override
    public void setOffset(long newPosition) throws IOException {
        checkArgument(newPosition >= 0, "Attempted to set a negative offset");

        file.seek(newPosition);
    }

    @Override
    public void skip(long size) throws IOException {
        setOffset(getOffset() + size);
    }

    @Override
    public void skip(int size) throws IOException {
        skip((long) size);
    }

    @Override
    public int available() throws IOException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void close() throws IOException {
        file.close();
    }

    @Override
    public int read(byte[] buffer, int offset, int length) throws IOException {
        return file.read(buffer, offset, length);
    }

    @Override
    public int readByte() throws IOException {
        return file.read();
    }

    @Override
    public void write(byte[] data, int offset, int length) throws IOException {
        file.write(data, offset, length);
    }

    @Override
    public void write(byte[] data) throws IOException {
        file.write(data);
    }

    @Override
    public void writeByte(int value) throws IOException {
        file.write(value);
    }

    @Override
    public boolean supportsNegativeSkips() {
        return true;
    }
}
