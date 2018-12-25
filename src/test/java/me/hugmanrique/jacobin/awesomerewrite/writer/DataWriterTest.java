package me.hugmanrique.jacobin.awesomerewrite.writer;

import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

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
}
