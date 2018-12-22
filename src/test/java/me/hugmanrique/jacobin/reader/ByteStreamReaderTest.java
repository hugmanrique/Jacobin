package me.hugmanrique.jacobin.reader;

import java.io.IOException;
import java.nio.ByteOrder;

import static org.junit.Assert.assertEquals;

/**
 * @author Hugo Manrique
 * @since 02/09/2018
 */
public class ByteStreamReaderTest {

    protected static byte[] bytes(int... bytesAsInts) {
        byte[] bytes = new byte[bytesAsInts.length];

        for (int i = 0; i < bytesAsInts.length; i++) {
            bytes[i] = (byte) bytesAsInts[i];
        }

        return bytes;
    }

    protected void assertDataConsumed(int length, ByteStreamReader reader) throws IOException {
        assertEquals(
                "Reader offset should be moved by " + length + " positions",
                length,
                reader.getOffset()
        );
    }

    protected void assertDataConsumed(byte[] data, ByteStreamReader reader) throws IOException {
        assertDataConsumed(data.length, reader);
    }

    protected ByteOrder byteOrder = ByteOrder.LITTLE_ENDIAN;

    // Note: this reader doesn't need to be closed
    protected ByteStreamReader newReader(byte[] data) {
        return new ByteStreamReaderBuilder()
                .stream(data)
                .order(byteOrder)
                .build();
    }

    protected void assertReadByte(byte[] data, int value) throws Exception {
        ByteStreamReader reader = newReader(data);

        assertEquals(value, reader.readByte());
        assertDataConsumed(data, reader);
    }

    protected void assertReadInt16(byte[] data, short value) throws Exception {
        ByteStreamReader reader = newReader(data);

        assertEquals(value, reader.readInt16());
        assertDataConsumed(data, reader);
    }

    protected void assertReadInt32(byte[] data, int value) throws Exception {
        ByteStreamReader reader = newReader(data);

        assertEquals(value, reader.readInt32());
        assertDataConsumed(data, reader);
    }

    protected void assertReadInt64(byte[] data, long value) throws Exception {
        ByteStreamReader reader = newReader(data);

        assertEquals(value, reader.readInt64());
        assertDataConsumed(data, reader);
    }
}
