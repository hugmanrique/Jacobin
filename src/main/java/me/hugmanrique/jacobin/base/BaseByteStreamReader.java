package me.hugmanrique.jacobin.base;

import me.hugmanrique.jacobin.ByteStreamReader;
import me.hugmanrique.jacobin.util.Unsigned;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.atomic.AtomicLong;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Hugo Manrique
 * @since 02/09/2018
 */
public abstract class BaseByteStreamReader implements ByteStreamReader {
    private static final int MAX_NEGATIVE_SKIP_LIMIT = Integer.MAX_VALUE;

    protected final InputStream stream;
    protected final AtomicLong offset;

    public BaseByteStreamReader(InputStream stream) {
        this.stream = checkNotNull(stream, "stream");
        this.offset = new AtomicLong();

        if (supportsNegativeSkips()) {
            stream.mark(MAX_NEGATIVE_SKIP_LIMIT);
        }
    }

    /**
     * Goes back to the start of the stream and sets a mark.
     *
     * @throws IOException if this reader doesn't support negative skips
     * @see #supportsNegativeSkips() to check whether this reader supports negative skips
     */
    private void reset() throws IOException {
        offset.set(0);
        stream.reset();
        stream.mark(MAX_NEGATIVE_SKIP_LIMIT);
    }

    @Override
    public long getOffset() {
        return offset.get();
    }

    @Override
    public void setOffset(long newPosition) throws IOException {
        skip(newPosition - getOffset());
    }

    @Override
    public void skip(long size) throws IOException {
        if (size < 0) {
            if (!supportsNegativeSkips()) {
                throw new UnsupportedOperationException("This reader does not support negative skips");
            }

            // Since we're going back to 0, we need to calculate
            // the absolute byte position of the skip
            size = offset.get() + size;
            reset();
        }

        offset.addAndGet(stream.skip(size));
    }

    @Override
    public boolean supportsNegativeSkips() {
        return stream.markSupported();
    }

    @Override
    public void close() throws IOException {
        stream.close();
    }

    @Override
    public int available() throws IOException {
        return stream.available();
    }

    @Override
    public int read(byte[] buffer, int offset, int length) throws IOException {
        return stream.read(buffer, offset, length);
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
    public int readUInt16() throws IOException {
        return Unsigned.unsignedInt16(readInt16());
    }

    @Override
    public long readUInt32() throws IOException {
        return Unsigned.unsignedInt32(readInt32());
    }

    @Override
    public long readUInt64() throws IOException {
        return Unsigned.unsignedInt64(readInt64());
    }
}
