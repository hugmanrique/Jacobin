package me.hugmanrique.jacobin.writer;

import org.junit.Ignore;
import org.junit.Test;

import java.nio.ByteOrder;

/**
 * The writing test implementation creates a writer and a reader instance.
 * This means we depend on the reader's implementation being correct, but
 * their behavior is tested on the {@code reader} package.
 *
 * @author Hugo Manrique
 * @since 22/12/2018
 */
@Ignore
public class EndiannessByteStreamWriterTest extends ByteStreamWriterTest {

    public EndiannessByteStreamWriterTest(ByteOrder byteOrder) {
        super(byteOrder);
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
}
