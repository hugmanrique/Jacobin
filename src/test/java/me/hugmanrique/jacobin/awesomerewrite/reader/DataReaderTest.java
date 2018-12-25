package me.hugmanrique.jacobin.awesomerewrite.reader;

import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.stream.IntStream;

import static me.hugmanrique.jacobin.awesomerewrite.reader.TestDataReaderUtils.createInputStream;
import static org.junit.Assert.assertEquals;

/**
 * @author Hugo Manrique
 * @since 25/12/2018
 */
public class DataReaderTest {

    @Test
    public void testOffsets() throws IOException {
        int[] bytes = new int[] { 0x12, 0x34, 0x56, 0x78 };

        DataReader reader = new DataReader(
            createInputStream(bytes)
        );

        assertEquals("Initial offset must be zero", 0, reader.getOffset());

        reader.setOffset(2);
        assertEquals("Offset after setting must be equal", 2, reader.getOffset());

        reader.skip(1);
        assertEquals("Skip must update the offset", 3, reader.getOffset());

        reader.skip(-2);
        assertEquals("Negative skip must update the offset", 1, reader.getOffset());

        reader.setOffset(0);
        assertEquals("Negative offset setting must update the offset", 0, reader.getOffset());

        reader.skip(3);
        reader.reset();

        assertEquals("Resetting should set the offset to zero", 0, reader.getOffset());

        int available = reader.available();
        assertEquals("Available bytes must be equal to array size", bytes.length, available);

        reader.skip(4);
        assertEquals("Skips larger than the stream's size should update offset correctly", 4, reader.getOffset());
    }

    private static final int[] READ_ARRAY = new int[] { 0xAB, 0xCD, 0xEF, 0x12, 0x34};

    private static DataReader createReadTestReader() {
        return new DataReader(createInputStream(READ_ARRAY));
    }

    @Test
    public void testSingleRead() throws IOException {
        DataReader reader = createReadTestReader();

        int firstByte = reader.readByte();
        assertEquals("Byte read must be equal", READ_ARRAY[0], firstByte);

        reader.skip(2);
        int fourthByte = reader.readByte();

        assertEquals("Byte read after skip must be equal", READ_ARRAY[3], fourthByte);
    }

    @Test
    public void testTotalRead() throws IOException {
        DataReader reader = createReadTestReader();
        byte[] buffer = new byte[3];
        int initialOffset = 1;

        reader.setOffset(initialOffset);

        reader.read(buffer);

        for (int i = initialOffset; i < initialOffset + buffer.length; i++) {
            assertEquals("Array byte read must be equal", (byte) READ_ARRAY[i], buffer[i - initialOffset]);
        }
    }

    @Test
    public void testPartialRead() throws IOException {
        DataReader reader = createReadTestReader();
        byte[] buffer = new byte[6];
        int initialOffset = 2;

        reader.setOffset(initialOffset);

        reader.read(buffer, 2, 3);

        // Check read bytes
        for (int i = initialOffset; i < initialOffset + 3; i++) {
            assertEquals("Array byte read should be in right index", (byte) READ_ARRAY[i], buffer[i]);
        }

        // Check other bytes
        IntStream.of(0, 1, 5).forEach(emptyIndex -> {
            assertEquals("Non-read byte should be zero", 0, buffer[emptyIndex]);
        });
    }
}
