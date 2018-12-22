package me.hugmanrique.jacobin.reader;

import org.junit.Test;

import java.nio.charset.StandardCharsets;

/**
 * @author Hugo Manrique
 * @since 02/09/2018
 */
public class LittleEndianByteStreamReaderTest extends ByteStreamReaderTest {

    @Test
    public void testLittleEndian() throws Exception {
        assertReadByte(bytes(0x12), 0x12);
        assertReadByte(bytes(0xFE), 0xFE);

        assertReadInt16(bytes(0x34, 0x12), (short) 0x1234);
        assertReadInt16(bytes(0xCD, 0xAB), (short) 0xABCD);

        assertReadInt32(bytes(0x78, 0x56, 0x34, 0x12), 0x12345678);
        assertReadInt32(bytes(0xF0, 0xDE, 0xBC, 0x9A), 0x9ABCDEF0);

        assertReadInt64(
            bytes(0xF0, 0xDE, 0xBC, 0x9A, 0x78, 0x56, 0x34, 0x12),
            0x123456789ABCDEF0L
        );

        assertReadInt64(
            bytes(0x78, 0x56, 0x34, 0x12, 0xF0, 0xDE, 0xBC, 0x9A),
            0x9ABCDEF012345678L
        );

        String testString = "foo";
        byte[] stringBytes = testString.getBytes(StandardCharsets.UTF_8);

        assertReadString(stringBytes, "foo");


    }
}
