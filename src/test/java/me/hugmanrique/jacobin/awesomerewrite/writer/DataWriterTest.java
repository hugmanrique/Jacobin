package me.hugmanrique.jacobin.awesomerewrite.writer;

import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static org.junit.Assert.assertEquals;

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





        /*
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
         */

    }

    @Test(expected = UnsupportedOperationException.class)
    public void testNegativeSkip() {


    }
}
