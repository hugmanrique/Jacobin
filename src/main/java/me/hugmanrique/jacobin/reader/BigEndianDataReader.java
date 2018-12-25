package me.hugmanrique.jacobin.reader;

import me.hugmanrique.jacobin.readable.BigEndianReadable;

import java.io.InputStream;
import java.nio.ByteOrder;

/**
 * A {@link ByteOrder#BIG_ENDIAN} {@link DataReader} implementation.
 *
 * @author Hugo Manrique
 * @since 24/12/2018
 */
public class BigEndianDataReader extends DataReader implements BigEndianReadable {

    public BigEndianDataReader(InputStream stream) {
        super(stream);
    }
}
