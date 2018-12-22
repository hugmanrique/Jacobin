package me.hugmanrique.jacobin.writer;

import org.junit.Test;

import java.nio.ByteOrder;

/**
 * @author Hugo Manrique
 * @since 03/09/2018
 */
public class LittleEndianByteStreamWriterTest extends ByteStreamWriterTest {

    public LittleEndianByteStreamWriterTest() {
        super(ByteOrder.LITTLE_ENDIAN);
    }

    @Test
    public void testByteWrite() throws Exception {
        writer.writeByte(0x12);
        assertWriteByte(0x12);
    }

    @Test
    public void testInt16Write() throws Exception {
        writer.writeInt16((short) 0x1234);
        assertWriteInt16((short) 0x1234);
    }

    @Test
    public void testInt32Write() throws Exception {
        writer.writeInt32(0x12345678);
        assertWriteInt32(0x12345678);
    }

    @Test
    public void testInt64Write() throws Exception {
        writer.writeInt64(0x123456789ABCDEF0L);
        assertWriteInt64(0x123456789ABCDEF0L);
    }

    // UTF-8 is endianness-independent, so the string writing
    // test is only executed here.
    @Test
    public void testStringWrite() throws Exception {
        String testString = "abcdef12345678";

        writer.writeUTF(testString);
        assertWriteString(testString);
    }
}
