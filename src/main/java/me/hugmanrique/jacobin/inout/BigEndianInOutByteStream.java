package me.hugmanrique.jacobin.inout;

import me.hugmanrique.jacobin.order.BigEndianReader;
import me.hugmanrique.jacobin.order.BigEndianWriter;

import java.io.File;
import java.io.IOException;

/**
 * Big-endian implementation of {@link InOutByteStream}.
 *
 * @author Hugo Manrique
 * @since 03/09/2018
 */
public class BigEndianInOutByteStream extends InOutByteStream implements BigEndianReader, BigEndianWriter {

    public BigEndianInOutByteStream(File file, boolean synchronousWrites) throws IOException {
        super(file, synchronousWrites);
    }

    public BigEndianInOutByteStream(File file) throws IOException {
        super(file);
    }
}
