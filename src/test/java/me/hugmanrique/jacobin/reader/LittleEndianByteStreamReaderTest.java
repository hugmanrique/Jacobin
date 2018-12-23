package me.hugmanrique.jacobin.reader;

import org.junit.Test;

import java.io.IOException;
import java.util.stream.IntStream;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

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
    }

    @Test
    public void testCompleteArrayRead() throws Exception {
        byte[] original = new byte[] { 0x12, 0x34, 0x56 };
        ByteStreamReader reader = newReader(original);

        byte[] data = new byte[original.length];
        int readByteCount = reader.read(data);

        assertArrayEquals("Read bytes must be correct", original, data);
        assertEquals("Reader should have read correct number of bytes", original.length, readByteCount);

        readByteCount = reader.read(data);
        assertEquals("Subsequent calls must return -1 (EOF)", -1, readByteCount);
    }

    /**
     * Reads 3 bytes from the byte stream and copies them to the {@code n + 2}th index of {@code data}.
     */
    @Test
    public void testPartialArrayRead() throws IOException {
        int bytesToRead = 3;
        int readOffset = 2;

        byte[] original = new byte[] { 0x78, 0x9, 0x56, 0x12 };

        ByteStreamReader reader = newReader(original);
        byte[] data = new byte[original.length + 4];

        int readByteCount = reader.read(data, readOffset, bytesToRead);

        for (int i = 0; i < readByteCount; i++) {
            int readIndex = i + readOffset;

            assertEquals("Byte must be stored in the correct index", original[i], data[readIndex]);
        }

        // Test uninitialized indexes
        IntStream.of(0, 1, 5, 6, 7)
                .forEach(index -> {
                    assertEquals("Array item should be uninitialized", 0, data[index]);
                });

        assertEquals("Reader should have read correct number of bytes", bytesToRead, readByteCount);
    }
}
