package me.hugmanrique.jacobin.reader;

import me.hugmanrique.jacobin.ByteStreamReader;
import me.hugmanrique.jacobin.base.BaseByteStreamReader;
import me.hugmanrique.jacobin.order.LittleEndianReader;

import java.io.InputStream;

/**
 * Little-endian implementation of {@link ByteStreamReader}.
 *
 * @author Hugo Manrique
 * @since 02/09/2018
 */
public class LittleEndianByteStreamReader extends BaseByteStreamReader implements LittleEndianReader {
    public LittleEndianByteStreamReader(InputStream stream) {
        super(stream);
    }
}
