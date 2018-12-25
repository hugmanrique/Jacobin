package me.hugmanrique.jacobin.awesomerewrite.reader;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import static org.junit.Assert.assertEquals;

/**
 * @author Hugo Manrique
 * @since 25/12/2018
 */
public class TestDataReaderUtils {

    static ByteArrayInputStream createInputStream(int... bytesAsInts) {
        byte[] bytes = new byte[bytesAsInts.length];

        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = (byte) bytesAsInts[i];
        }

        return new ByteArrayInputStream(bytes);
    }

    static void assertConsumed(DataReader reader) throws IOException {
        assertEquals("Reader offset should be at the end of the stream", 0, reader.available());
    }
}
