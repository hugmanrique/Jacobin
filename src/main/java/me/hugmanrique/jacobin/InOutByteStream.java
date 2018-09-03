package me.hugmanrique.jacobin;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Combined byte streams reader and writer class.
 *
 * @author Hugo Manrique
 * @since 03/09/2018
 */
public abstract class InOutByteStream implements ByteStreamReader, ByteStreamWriter, Closeable {
    private final RandomAccessFile file;

    /**
     * Creates a new {@link InOutByteStream} instance that will read and write
     * to/from the given file.
     *
     * @param file the file to be read and written
     * @param synchronousWrites whether each update to the file's content should be written
     *                          synchronously to the underlying storage device.
     * @throws IOException if an I/O error occurs
     */
    public InOutByteStream(File file, boolean synchronousWrites) throws IOException {
        checkNotNull(file, "file");
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
    public int available() {
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
