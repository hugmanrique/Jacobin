package me.hugmanrique.jacobin.awesomerewrite.writer;

import me.hugmanrique.jacobin.awesomerewrite.writable.BigEndianWritable;

import java.io.OutputStream;
import java.nio.ByteOrder;

/**
 * A {@link ByteOrder#BIG_ENDIAN} {@link DataWriter} implementation.
 *
 * @author Hugo Manrique
 * @since 25/12/2018
 */
public class BigEndianDataWriter extends DataWriter implements BigEndianWritable {

    public BigEndianDataWriter(OutputStream stream) {
        super(stream);
    }
}
