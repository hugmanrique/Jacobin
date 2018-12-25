package me.hugmanrique.jacobin.awesomerewrite.reader;

import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;

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

    @Test
    public void testReads() throws IOException {
        int[] bytes = new int[] { 0xAB, 0xCD, 0xEF, 0x12, 0x34};

        DataReader reader = new DataReader(
            createInputStream(bytes)
        );

        int firstByte = reader.readByte();
        assertEquals("Byte read must be equal", bytes[0], firstByte);

        reader.skip(2);
        int fourthByte = reader.readByte();

        assertEquals("Byte read after skip must be equal", bytes[3], fourthByte);

        // Complete read

        byte[] buffer = new byte[3];
        int initialOffset = 1;

        reader.setOffset(initialOffset);

        reader.read(buffer);

        for (int i = initialOffset; i < initialOffset + buffer.length; i++) {
            assertEquals("Array byte read must be equal", (byte) bytes[i], buffer[i - initialOffset]);
        }

        // Partial read

        buffer = new byte[6];
        initialOffset = 2;

        reader.setOffset(initialOffset);

        reader.read(buffer, 2, 3);

        // Check read bytes
        for (int i = initialOffset; i < initialOffset + 3; i++) {
            assertEquals("Array byte read should be in right index", (byte) bytes[i], buffer[i]);
        }

        // Check other bytes
        for (int emptyIndex : Arrays.asList(0, 1, 5)) {
            assertEquals("Non-read byte should be zero", 0, buffer[emptyIndex]);
        }
    }
}
