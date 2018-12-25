package me.hugmanrique.jacobin.awesomerewrite.reader;

import java.io.ByteArrayInputStream;

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
}
