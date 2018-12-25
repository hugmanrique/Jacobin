package me.hugmanrique.jacobin.reader;

import org.junit.Test;

import java.io.IOException;

import static me.hugmanrique.jacobin.reader.TestDataReaderUtils.assertConsumed;
import static org.junit.Assert.assertEquals;

/**
 * @author Hugo Manrique
 * @since 25/12/2018
 */
public class BigEndianDataReaderTest {

    private BigEndianDataReader createReader(int... bytesAsInts) {
        return new BigEndianDataReader(
            TestDataReaderUtils.createInputStream(bytesAsInts)
        );
    }

    @Test
    public void testNumberRead() throws IOException {
        BigEndianDataReader reader = createReader(0x12, 0x34);
        assertEquals((short) 0x1234, reader.readInt16());
        assertConsumed(reader);

        reader = createReader(0xAB, 0xCD);
        assertEquals((short) 0xABCD, reader.readInt16());
        assertConsumed(reader);

        reader = createReader(0x12, 0x34, 0x56, 0x78);
        assertEquals(0x12345678, reader.readInt32());
        assertConsumed(reader);

        reader = createReader(0x9A, 0xBC, 0xDE, 0xF0);
        assertEquals(0x9ABCDEF0, reader.readInt32());
        assertConsumed(reader);

        reader = createReader(0x12, 0x34, 0x56, 0x78, 0x9A, 0xBC, 0xDE, 0xF0);
        assertEquals(0x123456789ABCDEF0L, reader.readInt64());
        assertConsumed(reader);

        reader = createReader(0x9A, 0xBC, 0xDE, 0xF0, 0x12, 0x34, 0x56, 0x78);
        assertEquals(0x9ABCDEF012345678L, reader.readInt64());
        assertConsumed(reader);
    }
}
