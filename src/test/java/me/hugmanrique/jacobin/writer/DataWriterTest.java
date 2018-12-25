package me.hugmanrique.jacobin.writer;

import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.stream.IntStream;

import static org.junit.Assert.*;

/**
 * @author Hugo Manrique
 * @since 25/12/2018
 */
public class DataWriterTest {

    @Test
    public void testOffsets() throws IOException {
        ByteArrayOutputStream stream = new ByteArrayOutputStream(4);
        DataWriter writer = new DataWriter(stream);

        assertEquals("Initial offset must be zero", 0, writer.getOffset());

        writer.setOffset(2);
        assertEquals("Offset after setting must be right", 2, writer.getOffset());

        writer.skip(1);
        assertEquals("Skip must update the offset", 3, writer.getOffset());

        writer.skip(4);
        assertEquals("Skips larger than the stream's size should update the offset", 7, writer.getOffset());
    }

    @Test
    public void testNegativeOffsets() throws IOException {
        ByteArrayOutputStream stream = new ByteArrayOutputStream(2);
        DataWriter writer = new DataWriter(stream);

        // Move pointer to offset 1
        writer.skip(1);

        assertFalse("Default implementation does not support negative skips", writer.supportsNegativeSkips());

        try {
            writer.skip(-1);
            writer.setOffset(0);

            fail();
        } catch (UnsupportedOperationException ignored) {}
    }

    @Test
    public void testSingleWrite() throws IOException {
        ByteArrayOutputStream stream = new ByteArrayOutputStream(4);
        DataWriter writer = new DataWriter(stream);

        writer.writeByte(0x12);

        assertEquals("Byte read must be equal", 0x12, stream.toByteArray()[0]);

        writer.skip(2);
        writer.writeByte(0x34);

        assertEquals("Byte read after skip must be equal", 0x34, stream.toByteArray()[3]);
    }

    @Test
    public void testTotalWrite() throws IOException {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        DataWriter writer = new DataWriter(stream);

        byte[] toWrite = new byte[] { 0x12, 0x34, 0x56 };

        writer.write(toWrite);

        assertArrayEquals(stream.toByteArray(), toWrite);
    }

    @Test
    public void testPartialWrite() throws IOException {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        DataWriter writer = new DataWriter(stream);
        int initialOffset = 2;

        byte[] toWrite = new byte[] { 0x12, 0x34, 0x56, 0x78 };

        writer.skip(initialOffset);

        // Will write { 0x34, 0x56 }
        writer.write(toWrite, 1, 2);

        byte[] result = stream.toByteArray();

        for (int i = 2; i < initialOffset + 2; i++) {
            assertEquals("Array byte read should be in right index", toWrite[i - 1], result[i]);
        }

        IntStream.of(0, 1).forEach(emptyIndex -> {
            assertEquals("Non-read byte should be zero", 0, result[emptyIndex]);
        });
    }

    @Test
    public void testWriteUTF() throws IOException {
        String testString = "abcdef12345678\uD83E\uDD2A \uD83D\uDE00 \uD83D\uDE02";
        byte[] stringBytes = testString.getBytes(StandardCharsets.UTF_8);

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        LittleEndianDataWriter writable = new LittleEndianDataWriter(stream);

        // Since UTF-8 is interpreted as a sequence of bytes,
        // there is no endian problem.
        writable.writeUTF(testString);

        byte[] actualValue = stream.toByteArray();

        assertArrayEquals(stringBytes, actualValue);
    }
}
