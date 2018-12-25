package me.hugmanrique.jacobin.awesomerewrite.reader;

import org.junit.Test;

import java.io.IOException;

import static me.hugmanrique.jacobin.awesomerewrite.reader.TestDataReaderUtils.assertConsumed;
import static me.hugmanrique.jacobin.awesomerewrite.reader.TestDataReaderUtils.createInputStream;
import static org.junit.Assert.assertEquals;

/**
 * @author Hugo Manrique
 * @since 25/12/2018
 */
public class LittleEndianDataReaderTest {

    private LittleEndianDataReader createReader(int... bytesAsInts) {
        return new LittleEndianDataReader(
            createInputStream(bytesAsInts)
        );
    }

    @Test
    public void testNumberRead() throws IOException {
        LittleEndianDataReader reader = createReader(0x34, 0x12);
        assertEquals((short) 0x1234, reader.readInt16());
        assertConsumed(reader);

        reader = createReader(0xCD, 0xAB);
        assertEquals((short) 0xABCD, reader.readInt16());
        assertConsumed(reader);

        reader = createReader(0x78, 0x56, 0x34, 0x12);
        assertEquals(0x12345678, reader.readInt32());
        assertConsumed(reader);

        reader = createReader(0xF0, 0xDE, 0xBC, 0x9A);
        assertEquals(0x9ABCDEF0, reader.readInt32());
        assertConsumed(reader);

        reader = createReader(0xF0, 0xDE, 0xBC, 0x9A, 0x78, 0x56, 0x34, 0x12);
        assertEquals(0x123456789ABCDEF0L, reader.readInt64());
        assertConsumed(reader);

        reader = createReader(0x78, 0x56, 0x34, 0x12, 0xF0, 0xDE, 0xBC, 0x9A);
        assertEquals(0x9ABCDEF012345678L, reader.readInt64());
        assertConsumed(reader);
    }
}
