package me.hugmanrique.jacobin.writer;

import org.junit.Test;

import java.nio.ByteOrder;

/**
 * @author Hugo Manrique
 * @since 03/09/2018
 */
public class LittleEndianByteStreamWriterTest extends EndiannessByteStreamWriterTest {

    public LittleEndianByteStreamWriterTest() {
        super(ByteOrder.LITTLE_ENDIAN);
    }

    // UTF-8 is endianness-independent, so string writing is only tested once.
    @Test
    public void testStringWrite() throws Exception {
        String testString = "abcdef12345678";

        writer.writeUTF(testString);
        assertWriteString(testString);
    }
}
