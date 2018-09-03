package me.hugmanrique.jacobin;

import me.hugmanrique.jacobin.inout.LittleEndianInOutByteStream;

import java.io.File;
import java.io.IOException;
import java.nio.ByteOrder;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Creates a new {@link InOutByteStream} that will read and write
 * to/from the given file with a defined {@link ByteOrder}.
 * @author Hugo Manrique
 * @since 03/09/2018
 */
public class InOutByteStreamBuilder {
    private File file;
    private boolean synchronous;
    private ByteOrder order = ByteOrder.LITTLE_ENDIAN;

    public InOutByteStreamBuilder() {}

    /**
     * Creates a new {@link InOutByteStream} instance to read
     * and write from/to the given {@link File}.
     */
    public InOutByteStreamBuilder file(File file) {
        this.file = file;
        return this;
    }

    /**
     * Requires each update to the file's content to be written
     * synchronously to the underlying storage device.
     */
    public InOutByteStreamBuilder synchronous() {
        this.synchronous = true;
        return this;
    }

    /**
     * Sets the byte order of the {@link InOutByteStream}.
     */
    public InOutByteStreamBuilder order(ByteOrder order) {
        this.order = checkNotNull(order, "byte order");
        return this;
    }

    /**
     * Creates a new {@link InOutByteStream} that will read and write
     * to/from the given file with a defined {@link ByteOrder}.
     *
     * @return the new {@link InOutByteStream}
     * @throws IOException if an I/O error occurs
     */
    public InOutByteStream build() throws IOException {
        checkNotNull(file, "file");

        if (order.equals(ByteOrder.LITTLE_ENDIAN)) {
            return new LittleEndianInOutByteStream(file, synchronous);
        }

        throw new UnsupportedOperationException(order + " byte order not supported");
    }
}
