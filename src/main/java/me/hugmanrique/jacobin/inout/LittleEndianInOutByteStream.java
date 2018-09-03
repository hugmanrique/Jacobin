package me.hugmanrique.jacobin.inout;

import me.hugmanrique.jacobin.InOutByteStream;
import me.hugmanrique.jacobin.order.LittleEndianReader;
import me.hugmanrique.jacobin.order.LittleEndianWriter;

import java.io.File;
import java.io.IOException;

/**
 * Little-endian implementation of {@link InOutByteStream}.
 *
 * @author Hugo Manrique
 * @since 03/09/2018
 */
public class LittleEndianInOutByteStream extends InOutByteStream implements LittleEndianReader, LittleEndianWriter {
    public LittleEndianInOutByteStream(File file, boolean synchronousWrites) throws IOException {
        super(file, synchronousWrites);
    }

    public LittleEndianInOutByteStream(File file) throws IOException {
        super(file);
    }
}
