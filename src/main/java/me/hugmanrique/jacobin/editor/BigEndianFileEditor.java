package me.hugmanrique.jacobin.editor;

import me.hugmanrique.jacobin.readable.BigEndianReadable;
import me.hugmanrique.jacobin.writable.BigEndianWritable;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteOrder;

/**
 * A {@link ByteOrder#BIG_ENDIAN} implementation of {@link FileEditor}.
 *
 * @author Hugo Manrique
 * @since 25/12/2018
 */
public class BigEndianFileEditor extends FileEditor implements BigEndianReadable, BigEndianWritable {

    public BigEndianFileEditor(RandomAccessFile file) {
        super(file);
    }

    public BigEndianFileEditor(File file, boolean synchronousWrites) throws IOException {
        super(file, synchronousWrites);
    }

    public BigEndianFileEditor(File file) throws IOException {
        super(file);
    }

    @Override
    public ByteOrder getByteOrder() {
        return ByteOrder.BIG_ENDIAN;
    }
}
