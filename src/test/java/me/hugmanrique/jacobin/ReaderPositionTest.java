package me.hugmanrique.jacobin;

import org.junit.Test;

import java.io.EOFException;
import java.io.IOException;

import static org.junit.Assert.assertEquals;

/**
 * @author Hugo Manrique
 * @since 02/09/2018
 */
public class ReaderPositionTest extends ByteStreamReaderTest {
    @Test
    public void testPositiveSkip() throws IOException {
        ByteStreamReader reader = newReader(bytes(0x12, 0x34, 0x56));

        // Skip one byte
        reader.skip(1);
        assertEquals(0x34, reader.readByte());

        // Don't skip any bytes
        reader.skip(0);
        assertEquals(0x56, reader.readByte());

        assertDataConsumed(3, reader);
    }

    @Test
    public void testNegativeSkip() throws IOException {
        ByteStreamReader reader = newReader(bytes(0x12, 0x34, 0x56));

        // Skip two bytes and go back
        reader.skip(2);
        reader.skip(-1);
        assertEquals(0x34, reader.readByte());

        // Go back to the origin position
        reader.skip(-2);
        assertEquals(0x12, reader.readByte());
    }

    @Test
    public void testSetOffset() throws IOException {
        ByteStreamReader reader = newReader(bytes(0x12, 0x34, 0x56));

        reader.setOffset(2);
        assertEquals(0x56, reader.readByte());

        // Offset should get incremented
        assertEquals(3, reader.getOffset());
    }

    @Test(expected = EOFException.class)
    public void testSkipThrowsEOF() throws IOException {
        ByteStreamReader reader = newReader(bytes(0x12));

        reader.skip(2);
        reader.readByte();
    }
}
