package me.hugmanrique.jacobin.inout;

import me.hugmanrique.jacobin.InOutByteStream;
import me.hugmanrique.jacobin.InOutByteStreamBuilder;

import java.io.File;
import java.io.IOException;
import java.nio.ByteOrder;

/**
 * @author Hugo Manrique
 * @since 03/09/2018
 */
public class InOutByteStreamTest {
    private static int fileCount = 0;

    protected final ByteOrder byteOrder;

    public InOutByteStreamTest(ByteOrder byteOrder) {
        this.byteOrder = byteOrder;
    }

    private File createTempFile() throws IOException {
        File temp = File.createTempFile(byteOrder + "inOutTest" + fileCount++, ".tmp");
        temp.deleteOnExit();

        return temp;
    }

    protected InOutByteStream createStream() throws IOException {
        return new InOutByteStreamBuilder()
                .file(createTempFile())
                .order(byteOrder)
                .build();
    }
}
