package me.hugmanrique.jacobin.writer;

import java.nio.ByteOrder;

/**
 * @author Hugo Manrique
 * @since 22/12/2018
 */
public class BigEndianByteStreamWriterTest extends EndiannessByteStreamWriterTest {

    public BigEndianByteStreamWriterTest() {
        super(ByteOrder.BIG_ENDIAN);
    }
}
