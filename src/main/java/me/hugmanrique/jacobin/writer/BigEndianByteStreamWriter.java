package me.hugmanrique.jacobin.writer;

import me.hugmanrique.jacobin.base.BaseByteStreamWriter;
import me.hugmanrique.jacobin.order.BigEndianWriter;

import java.io.OutputStream;

/**
 * Big-endian implementation of {@link ByteStreamWriter}.
 *
 * @author Hugo Manrique
 * @since 03/09/2018
 */
public class BigEndianByteStreamWriter extends BaseByteStreamWriter implements BigEndianWriter {
    public BigEndianByteStreamWriter(OutputStream stream) {
        super(stream);
    }
}
