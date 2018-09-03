package me.hugmanrique.jacobin.reader;

import me.hugmanrique.jacobin.base.BaseByteStreamReader;
import me.hugmanrique.jacobin.order.BigEndianReader;

import java.io.InputStream;

/**
 * @author Hugo Manrique
 * @since 02/09/2018
 */
public class BigEndianByteStreamReader extends BaseByteStreamReader implements BigEndianReader {
    public BigEndianByteStreamReader(InputStream stream) {
        super(stream);
    }
}
