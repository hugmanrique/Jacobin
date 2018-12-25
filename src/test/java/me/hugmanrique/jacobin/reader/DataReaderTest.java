package me.hugmanrique.jacobin.reader;

import me.hugmanrique.jacobin.readable.ByteOrderReadable;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.stream.IntStream;

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
            TestDataReaderUtils.createInputStream(bytes)
        );

        assertEquals("Initial offset must be zero", 0, reader.getOffset());

        reader.setOffset(2);
        assertEquals("Offset after setting must be right", 2, reader.getOffset());

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

    @Test(expected = IllegalArgumentException.class)
    public void testNegativeSkip() throws IOException {
        DataReader reader = new DataReader(TestDataReaderUtils.createInputStream(0x1));

        reader.skip(-1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNegativeOffset() throws IOException {
        DataReader reader = new DataReader(TestDataReaderUtils.createInputStream(0xA));

        reader.setOffset(-5);
    }

    private static final int[] READ_ARRAY = new int[] { 0xAB, 0xCD, 0xEF, 0x12, 0x34};

    private static DataReader createReadTestReader() {
        return new DataReader(TestDataReaderUtils.createInputStream(READ_ARRAY));
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
        int initialOffset = 2;

        reader.setOffset(initialOffset);

        reader.read(buffer);

        for (int i = initialOffset; i < initialOffset + buffer.length; i++) {
            assertEquals("Array byte read must be equal", (byte) READ_ARRAY[i], buffer[i - initialOffset]);
        }

        TestDataReaderUtils.assertConsumed(reader);
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

    @Test(expected = EOFException.class)
    public void testEOFRead() throws IOException {
        DataReader reader = new DataReader(TestDataReaderUtils.createInputStream(0x12, 0x34));

        reader.skip(2);
        reader.readByte();
    }

    @Test
    public void testReadUTF() throws IOException {
        String testString = "abcdef12345678\uD83E\uDD2A \uD83D\uDE00 \uD83D\uDE02";
        byte[] stringBytes = testString.getBytes(StandardCharsets.UTF_8);

        ByteOrderReadable reader = new LittleEndianDataReader(
            new ByteArrayInputStream(stringBytes)
        );

        String actualValue = reader.readUTF(stringBytes.length);

        assertEquals(testString, actualValue);
        TestDataReaderUtils.assertConsumed((DataReader) reader);
    }
}
