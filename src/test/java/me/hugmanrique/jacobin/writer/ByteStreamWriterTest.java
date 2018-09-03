package me.hugmanrique.jacobin.writer;

import me.hugmanrique.jacobin.reader.ByteStreamReader;
import me.hugmanrique.jacobin.reader.ByteStreamReaderBuilder;
import org.junit.Before;

import java.io.ByteArrayOutputStream;
import java.nio.ByteOrder;

import static org.junit.Assert.assertEquals;

/**
 * @author Hugo Manrique
 * @since 03/09/2018
 */
public class ByteStreamWriterTest {
    protected final ByteOrder byteOrder;

    protected ByteArrayOutputStream stream; // Note: this stream doesn't need to be closed
    protected ByteStreamWriter writer;
    private ByteStreamReader reader;

    public ByteStreamWriterTest(ByteOrder byteOrder) {
        this.byteOrder = byteOrder;
    }

    @Before
    public void createWriter() {
        stream = new ByteArrayOutputStream();
        writer = new ByteStreamWriterBuilder()
                .stream(stream)
                .order(byteOrder)
                .build();
    }

    protected ByteStreamReader newReader() {
        return this.reader = new ByteStreamReaderBuilder()
                .stream(stream.toByteArray())
                .order(byteOrder)
                .build();
    }

    protected void assertWriteByte(int value) throws Exception {
        newReader();

        assertEquals(value, reader.readByte());
        assertEquals("Writer must write one byte", 0, reader.available());
    }

    protected void assertWriteInt16(short value) throws Exception {
        newReader();

        assertEquals(value, reader.readInt16());
        assertEquals("Writer must write 2 bytes", 0, reader.available());
    }

    protected void assertWriteInt32(int value) throws Exception {
        newReader();

        assertEquals(value, reader.readInt32());
        assertEquals("Writer must write 4 bytes", 0, reader.available());
    }

    protected void assertWriteInt64(long value) throws Exception {
        newReader();

        assertEquals(value, reader.readInt64());
        assertEquals("Writer must write 8 bytes", 0, reader.available());
    }
}
