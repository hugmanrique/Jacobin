package me.hugmanrique.jacobin.writer;

import me.hugmanrique.jacobin.ByteStreamReader;
import org.junit.Test;

import java.io.IOException;
import java.nio.ByteOrder;

import static org.junit.Assert.assertEquals;

/**
 * @author Hugo Manrique
 * @since 03/09/2018
 */
public class WriterSkipTest extends ByteStreamWriterTest {
    public WriterSkipTest() {
        super(ByteOrder.LITTLE_ENDIAN);
    }

    @Test
    public void testPositiveSkip() throws Exception {
        // Write two 0x0 bytes
        writer.skip(2);

        ByteStreamReader reader = newReader();
        assertEquals(0x0, reader.readByte());
        assertEquals(0x0, reader.readByte());
        assertEquals(2, writer.getOffset());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNegativeSkip() throws IOException {
        writer.skip(-2);
    }
}
