package me.hugmanrique.jacobin.writer;

import me.hugmanrique.jacobin.writable.LittleEndianWritable;

import java.io.OutputStream;
import java.nio.ByteOrder;

/**
 * A {@link ByteOrder#LITTLE_ENDIAN} {@link DataWriter} implementation.
 *
 * @author Hugo Manrique
 * @since 25/12/2018
 */
public class LittleEndianDataWriter extends DataWriter implements LittleEndianWritable {

    public LittleEndianDataWriter(OutputStream stream) {
        super(stream);
    }
}
