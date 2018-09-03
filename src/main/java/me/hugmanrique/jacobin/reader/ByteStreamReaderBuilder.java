package me.hugmanrique.jacobin.reader;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.ByteOrder;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkPositionIndex;

/**
 * Creates a new {@link ByteStreamReader} from different kinds of
 * sources with a defined {@link ByteOrder}.
 *
 * @author Hugo Manrique
 * @since 02/09/2018
 */
public final class ByteStreamReaderBuilder {
    private InputStream stream;
    private ByteOrder order = ByteOrder.LITTLE_ENDIAN;

    public ByteStreamReaderBuilder() {}

    /**
     * Creates a new {@link ByteStreamReader} instance to read from the
     * given {@link InputStream}.
     */
    public ByteStreamReaderBuilder stream(InputStream stream) {
        this.stream = stream;
        return this;
    }

    /**
     * Creates a new {@link ByteStreamReader} instance to read from the
     * {@code bytes} array from the beginning.
     */
    public ByteStreamReaderBuilder stream(byte[] bytes) {
        stream(bytes, 0);
        return this;
    }

    /**
     * Creates a new {@link ByteStreamReader} instance to read from the
     * {@code bytes} array, starting at the given position.
     *
     * @throws IndexOutOfBoundsException if {@code start} is negative or greater than the
     *                                   length of the array
     */
    public ByteStreamReaderBuilder stream(byte[] bytes, int start) {
        stream(bytes, start, bytes.length);
        return this;
    }

    /**
     * Creates a new {@link ByteStreamReader} instance to read {@code length} bytes
     * from the {@code bytes} array, starting at the given position.
     *
     * @throws IndexOutOfBoundsException if {@code start} or {@code length} are negative
     *                                   or greater than the length of the array
     */
    public ByteStreamReaderBuilder stream(byte[] bytes, int start, int length) {
        checkNotNull(bytes, "bytes");
        checkPositionIndex(start, bytes.length, "start offset");
        checkPositionIndex(length, bytes.length, "length");

        this.stream = new ByteArrayInputStream(bytes, start, length);
        return this;
    }

    /**
     * Sets the byte order of the {@link ByteStreamReader}.
     */
    public ByteStreamReaderBuilder order(ByteOrder order) {
        this.order = checkNotNull(order, "byte order");
        return this;
    }

    /**
     * Creates a new {@link ByteStreamReader} from different kinds of
     * sources with a defined {@link ByteOrder}.
     *
     * @return the new {@link ByteStreamReader}
     */
    public ByteStreamReader build() {
        checkNotNull(stream, "stream");

        if (order.equals(ByteOrder.LITTLE_ENDIAN)) {
            return new LittleEndianByteStreamReader(stream);
        } else if (order.equals(ByteOrder.BIG_ENDIAN)) {
            return new BigEndianByteStreamReader(stream);
        }

        throw new UnsupportedOperationException(order + " byte order not supported");
    }
}
