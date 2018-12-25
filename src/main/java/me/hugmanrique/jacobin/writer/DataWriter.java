package me.hugmanrique.jacobin.writer;

import me.hugmanrique.jacobin.Writable;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;

/**
 * A {@link Writable} implementation that writes bytes to an {@link OutputStream}.
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

        writeZeroBytes(offset);
        this.offset.addAndGet(offset);
    }

    /**
     * Writes {@code amount} zero bytes ({@code 0x0}) of data to the internal stream.
     * If the amount to skip is less than {@link #BLOCK_SIZE}, the operation will be
     * performed in a single {@link OutputStream#write(byte[])} call. Otherwise,
     * a byte array of size {@link #BLOCK_SIZE} will be allocated and will be used
     * to perform repeated {@link OutputStream#write(byte[])} calls.
     *
     * @param amount the amount of bytes to zero-write.
     * @throws IOException if an I/O error occurs.
     */
    protected void writeZeroBytes(long amount) throws IOException {
        if (amount < BLOCK_SIZE) {
            // We can perform the write in a single operation.
            byte[] zeroBlock = new byte[(int) amount];

            stream.write(zeroBlock);
        } else {
            // Perform write operations allocating a single array of size `BLOCK_SIZE`
            byte[] zeroBlock = new byte[BLOCK_SIZE];
            int totalBlocks = (int) (amount / BLOCK_SIZE);

            for (int i = 0; i < totalBlocks; i++) {
                // Write full block of zeros
                stream.write(zeroBlock);
            }

            // Write remaining block
            stream.write(zeroBlock, 0, (int) (amount % BLOCK_SIZE));
        }
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
    public void close() throws IOException {
        stream.close();
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
