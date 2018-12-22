package me.hugmanrique.jacobin.writer;

import me.hugmanrique.jacobin.base.BaseByteStreamWriter;
import me.hugmanrique.jacobin.order.LittleEndianWriter;

import java.io.OutputStream;

/**
 * Little-endian implementation of {@link ByteStreamWriter}.
 *
 * @author Hugo Manrique
 * @since 03/09/2018
 */
public class LittleEndianByteStreamWriter extends BaseByteStreamWriter implements LittleEndianWriter {

    public LittleEndianByteStreamWriter(OutputStream stream) {
        super(stream);
    }
}
