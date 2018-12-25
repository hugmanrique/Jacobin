package me.hugmanrique.jacobin.reader;

import me.hugmanrique.jacobin.readable.LittleEndianReadable;

import java.io.InputStream;
import java.nio.ByteOrder;

/**
 * A {@link ByteOrder#LITTLE_ENDIAN} {@link DataReader} implementation.
 *
 * @author Hugo Manrique
 * @since 24/12/2018
 */
public class LittleEndianDataReader extends DataReader implements LittleEndianReadable {

    public LittleEndianDataReader(InputStream stream) {
        super(stream);
    }
}
