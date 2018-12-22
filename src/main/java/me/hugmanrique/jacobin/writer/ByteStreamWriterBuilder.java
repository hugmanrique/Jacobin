package me.hugmanrique.jacobin.writer;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.nio.ByteOrder;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Creates a new {@link ByteStreamWriter} from different kinds of
 * sources with a defined {@link ByteOrder}.
 *
 * @author Hugo Manrique
 * @since 03/09/2018
 */
public final class ByteStreamWriterBuilder {

    private OutputStream stream;
    private ByteOrder order = ByteOrder.nativeOrder();

    public ByteStreamWriterBuilder() {}

    /**
     * Creates a new {@link ByteStreamWriter} instance to write to
     * the given {@link OutputStream}.
     */
    public ByteStreamWriterBuilder stream(OutputStream stream) {
        this.stream = stream;
        return this;
    }

    /**
     * Creates a new {@link ByteStreamWriter} instance to write to
     * a {@link ByteArrayOutputStream}.
     */
    public ByteStreamWriterBuilder bytes() {
        this.stream = new ByteArrayOutputStream();
        return this;
    }

    /**
     * Creates a new {@link ByteStreamWriter} instance write to a
     * {@link ByteArrayOutputStream} which will hold {@code size} bytes
     * before resizing.
     *
     * @throws IllegalArgumentException if {@code size} is negative
     */
    public ByteStreamWriterBuilder bytes(int size) {
        this.stream = new ByteArrayOutputStream(size);
        return this;
    }

    /**
     * Sets the byte order of the {@link ByteStreamWriter}.
     */
    public ByteStreamWriterBuilder order(ByteOrder order) {
        this.order = checkNotNull(order, "byte order");
        return this;
    }

    /**
     * Creates a new {@link ByteStreamWriter} from different kinds of
     * sources with a defined {@link ByteOrder} (which
     * defaults to the native endianness).
     *
     * @return the new {@link ByteStreamWriter}
     * @see ByteOrder#nativeOrder()
     */
    public ByteStreamWriter build() {
        checkNotNull(stream, "stream");

        if (order.equals(ByteOrder.LITTLE_ENDIAN)) {
            return new LittleEndianByteStreamWriter(stream);
        } else if (order.equals(ByteOrder.BIG_ENDIAN)) {
            return new BigEndianByteStreamWriter(stream);
        }

        throw new UnsupportedOperationException(order + " byte order not supported");
    }
}
