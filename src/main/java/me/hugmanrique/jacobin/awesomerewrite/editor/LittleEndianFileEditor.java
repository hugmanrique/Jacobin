package me.hugmanrique.jacobin.awesomerewrite.editor;

import me.hugmanrique.jacobin.awesomerewrite.readable.LittleEndianReadable;
import me.hugmanrique.jacobin.awesomerewrite.writable.LittleEndianWritable;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteOrder;

/**
 * A {@link ByteOrder#LITTLE_ENDIAN} implementation of {@link FileEditor}.
 *
 * @author Hugo Manrique
 * @since 25/12/2018
 */
public class LittleEndianFileEditor extends FileEditor implements LittleEndianReadable, LittleEndianWritable {

    public LittleEndianFileEditor(RandomAccessFile file) {
        super(file);
    }

    public LittleEndianFileEditor(File file, boolean synchronousWrites) throws IOException {
        super(file, synchronousWrites);
    }

    public LittleEndianFileEditor(File file) throws IOException {
        super(file);
    }

    @Override
    public ByteOrder getByteOrder() {
        return ByteOrder.LITTLE_ENDIAN;
    }
}
