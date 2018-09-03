package me.hugmanrique.jacobin.inout;

import me.hugmanrique.jacobin.InOutByteStream;
import org.junit.Test;

import java.nio.ByteOrder;

import static org.junit.Assert.assertEquals;

/**
 * @author Hugo Manrique
 * @since 03/09/2018
 */
public class LittleEndianInOutByteStreamTest extends InOutByteStreamTest {
    public LittleEndianInOutByteStreamTest() {
        super(ByteOrder.LITTLE_ENDIAN);
    }

    @Test
    public void testWriteAndRead() throws Exception {
        try (InOutByteStream stream = createStream()) {
            stream.writeByte(0x12);

            // Go back one byte and read
            stream.setOffset(0);
            assertEquals(0x12, stream.readByte());
        }
    }

    @Test
    public void testAheadRead() throws Exception {
        try (InOutByteStream stream = createStream()) {
            stream.skip(2);
            stream.writeByte(0x34);

            stream.setOffset(0);

            // These two bytes should be zeroed out
            assertEquals(0x0, stream.readByte());
            assertEquals(0x0, stream.readByte());
        }
    }
}
