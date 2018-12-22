package me.hugmanrique.jacobin.reader;

import org.junit.Test;

import java.nio.ByteOrder;

/**
 * @author Hugo Manrique
 * @since 02/09/2018
 */
public class BigEndianByteStreamReaderTest extends ByteStreamReaderTest {

    public BigEndianByteStreamReaderTest() {
        this.byteOrder = ByteOrder.BIG_ENDIAN;
    }

    @Test
    public void testLittleEndian() throws Exception {
        assertReadByte(bytes(0x12), 0x12);
        assertReadByte(bytes(0xFE), 0xFE);

        assertReadInt16(bytes(0x12, 0x34), (short) 0x1234);
        assertReadInt16(bytes(0xAB, 0xCD), (short) 0xABCD);

        assertReadInt32(bytes(0x12, 0x34, 0x56, 0x78), 0x12345678);
        assertReadInt32(bytes(0x9A, 0xBC, 0xDE, 0xF0), 0x9ABCDEF0);

        assertReadInt64(
                bytes(0x12, 0x34, 0x56, 0x78, 0x9A, 0xBC, 0xDE, 0xF0),
                0x123456789ABCDEF0L
        );

        assertReadInt64(
                bytes(0x9A, 0xBC, 0xDE, 0xF0, 0x12, 0x34, 0x56, 0x78),
                0x9ABCDEF012345678L
        );
    }
}
