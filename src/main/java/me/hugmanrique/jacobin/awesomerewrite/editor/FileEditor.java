package me.hugmanrique.jacobin.awesomerewrite.editor;

import me.hugmanrique.jacobin.awesomerewrite.Readable;
import me.hugmanrique.jacobin.awesomerewrite.Writable;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

import static java.util.Objects.requireNonNull;

/**
 * A {@code FileEditor} provides a {@link Readable} and {@link Writable} implementation
 * that supports both reading and writing to a random access file. A random access file
 * behaves like a large array of bytes stored in the file system.
 *
 * @author Hugo Manrique
 * @since 25/12/2018
 * @see RandomAccessFile
 */
public abstract class FileEditor implements Readable, Writable, Closeable {

    protected final RandomAccessFile file;

    /**
     * Creates a random access file stream wrapper to read from and write to.
     *
     * @param file the random access file to be read and written.
     */
    FileEditor(RandomAccessFile file) {
        this.file = file;
    }

    /**
     * Creates a random access file stream to read from and write to the file
     * specified by the {@link File} argument.
     *
     * @param file the file to be read and written.
     * @param synchronousWrites whether each update to the file's content should be written
     *                          synchronously to the underlying storage device.
     * @throws IOException if an I/O error occurs
     */
    FileEditor(File file, boolean synchronousWrites) throws IOException {
        this(
            new RandomAccessFile(
                requireNonNull(file, "file"),
                synchronousWrites ? "rwd" : "rw"
            )
        );
    }

    /**
     * Creates a random access file stream to read from and write to the file
     * specified by the {@link File} argument.
     *
     * <p>Writes will be written asynchronously to the underlying storage device,
     * use the {@link #FileEditor(File, boolean)} constructor for specifying
     * synchronous file writing.
     *
     * @param file the file to be read and written.
     * @throws IOException if an I/O error occurs.
     */
    FileEditor(File file) throws IOException {
        this(file, false);
    }

    @Override
    public long getOffset() throws IOException {
        return file.getFilePointer();
    }

    @Override
    public void setOffset(long newPosition) throws IOException {
        file.seek(newPosition);
    }

    @Override
    public void skip(long offset) throws IOException {
        setOffset(getOffset() + offset);
    }

    @Override
    public void reset() throws IOException {
        setOffset(0);
    }

    @Override
    public boolean supportsNegativeSkips() {
        return true;
    }

    /**
     * @deprecated {@link RandomAccessFile}s can have a number of available bytes larger than
     * {@link Integer#MAX_VALUE}, in which case an {@link IllegalStateException} will be thrown.
     * Callers should use {@link #safeAvailable()} instead.
     */
    @Override
    @Deprecated
    public int available() throws IOException {
        long available = safeAvailable();

        if (available < Integer.MAX_VALUE) {
            return (int) available;
        }

        throw new IllegalStateException("Number of available bytes does not fit in an integer (" + available + ")");
    }

    /**
     * Returns the number of bytes that can be read from the file relative to the current offset.
     *
     * @return the number of bytes that can be read (or skipped over) without expanding the
     *         file size.
     * @throws IOException if an I/O error occurs.
     */
    public long safeAvailable() throws IOException {
        return file.length() - getOffset();
    }

    @Override
    public void close() throws IOException {
        file.close();
    }

    @Override
    public int readByte() throws IOException {
        return file.read();
    }

    @Override
    public int read(byte[] buffer, int offset, int length) throws IOException {
        return file.read(buffer, offset, length);
    }

    @Override
    public int read(byte[] buffer) throws IOException {
        return file.read(buffer);
    }

    @Override
    public void writeByte(int value) throws IOException {
        file.write(value);
    }

    @Override
    public void write(byte[] data, int offset, int length) throws IOException {
        file.write(data, offset, length);
    }

    @Override
    public void write(byte[] data) throws IOException {
        file.write(data);
    }
}
