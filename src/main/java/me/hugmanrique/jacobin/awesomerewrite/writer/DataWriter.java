package me.hugmanrique.jacobin.awesomerewrite.writer;

import me.hugmanrique.jacobin.awesomerewrite.Writable;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;

/**
 * A {@link Writable} implementation that writes bytes to a {@link OutputStream}.
 *
 * <p>By default, this class does not support negative skipping. Subclasses are encouraged
 * to override the {@link #skip(long)} and {@link #supportsNegativeSkips()} methods and
 * provide this functionality.
 *
 * @author Hugo Manrique
 * @since 25/12/2018
 */
public class DataWriter implements Writable {

    // This will determine the zeroed array's block size used when skipping over
    // a huge number of bytes. This will be practically never used, but will
    // allocate `2^31/2^3=2^28` bytes of memory (256 MB).
    private static final int BLOCK_SIZE = Integer.MAX_VALUE / 8;

    protected final OutputStream stream;
    protected final AtomicLong offset;

    protected DataWriter(OutputStream stream) {
        this.stream = Objects.requireNonNull(stream, "stream");
        this.offset = new AtomicLong();
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
            throw new UnsupportedOperationException("This writer does not support negative skips");
        }

        if (offset < BLOCK_SIZE) {
            // We can perform the skip in a single `write(byte[])` operation.
            byte[] zeroBlock = new byte[(int) offset];

            stream.write(zeroBlock);
        } else {
            // Perform `write(byte[])` operations using arrays of size `BLOCK_SIZE`
            byte[] zeroBlock = new byte[BLOCK_SIZE];
            int totalBlocks = (int) (offset / BLOCK_SIZE);

            for (int i = 0; i < totalBlocks; i++) {
                // Write full blocks of zeros
                stream.write(zeroBlock);
            }

            // Write remaining non-full block
            stream.write(zeroBlock, 0, (int) (offset % BLOCK_SIZE));
        }

        this.offset.addAndGet(offset);
    }

    @Override
    public void reset() throws IOException {
        setOffset(0L);
    }

    @Override
    public boolean supportsNegativeSkips() {
        return false;
    }

    @Override
    public void writeByte(int value) throws IOException {
        stream.write(value);
        offset.incrementAndGet();
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
}
