package me.hugmanrique.jacobin.awesomerewrite.reader;

import me.hugmanrique.jacobin.awesomerewrite.readable.BigEndianReadable;

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
