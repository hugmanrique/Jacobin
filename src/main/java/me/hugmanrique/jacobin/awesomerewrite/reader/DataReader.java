package me.hugmanrique.jacobin.awesomerewrite.reader;

import me.hugmanrique.jacobin.awesomerewrite.Readable;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;

/**
 * A {@link Readable} implementation that reads bytes from an {@link InputStream}.
 *
 * <p>This class only supports negative skipping if the {@link InputStream#markSupported()}
 * method of the underlying stream returns {@code true}.
 *
 * @author Hugo Manrique
 * @since 24/12/2018
 */
public class DataReader implements Readable {

    private static final int MAX_NEGATIVE_SKIP = Integer.MAX_VALUE;

    protected final InputStream stream;
    protected final AtomicLong offset;

    public DataReader(InputStream stream) {
        this.stream = Objects.requireNonNull(stream, "stream");
        this.offset = new AtomicLong();

        if (supportsNegativeSkips()) {
            // Mark the zero offset so we can return to it later
            stream.mark(MAX_NEGATIVE_SKIP);
        }
    }

    @Override
    public long getOffset() {
        return offset.get();
    }

    @Override
    public void setOffset(long newPosition) throws IOException {
        // Convert the absolute offset into a relative skip
        skip(newPosition - getOffset());
    }

    @Override
    public void skip(long offset) throws IOException {
        if (offset < 0L) {
            if (!supportsNegativeSkips()) {
                throw new UnsupportedOperationException("This reader does not support negative skips");
            }

            // Since we are going back to 0, we need to calculate
            // the new absolute offset of the skip
            offset = this.offset.get() + offset;
            reset();
        }

        this.offset.addAndGet(stream.skip(offset));
    }

    @Override
    public void reset() throws IOException {
        // Go back to 0 and mark it so we can return to it later
        stream.reset();
        stream.mark(MAX_NEGATIVE_SKIP);

        // This will only get called if the reset is successful
        offset.set(0);
    }

    @Override
    public boolean supportsNegativeSkips() {
        return stream.markSupported();
    }

    @Override
    public int available() throws IOException {
        return stream.available();
    }

    @Override
    public int readByte() throws IOException {
        int value = stream.read();

        if (value == -1) {
            throw new EOFException();
        }

        offset.incrementAndGet();

        return value;
    }

    @Override
    public int read(byte[] buffer, int offset, int length) throws IOException {
        int readBytes = stream.read(buffer, offset, length);
        this.offset.addAndGet(readBytes);

        return readBytes;
    }

    @Override
    public int read(byte[] buffer) throws IOException {
        int readBytes = stream.read(buffer);
        this.offset.addAndGet(readBytes);

        return readBytes;
    }
}
