package me.hugmanrique.jacobin.writer;

import me.hugmanrique.jacobin.reader.LittleEndianDataReader;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteOrder;
import java.util.function.Function;

import static org.junit.Assert.assertEquals;

/**
 * @author Hugo Manrique
 * @since 25/12/2018
 */
public class LittleEndianDataWriterTest {

    private static Function<byte[], LittleEndianDataReader> READER_FACTORY =
            bytes -> new LittleEndianDataReader(
                new ByteArrayInputStream(bytes)
            );

    private ReaderOutputStream<LittleEndianDataReader> createReader() {
        return new ReaderOutputStream<>(READER_FACTORY);
    }

    @Test
    public void testByteOrder() {
        LittleEndianDataWriter writer = new LittleEndianDataWriter(
            new ByteArrayOutputStream()
        );

        assertEquals(ByteOrder.LITTLE_ENDIAN, writer.getByteOrder());
    }

    @Test
    public void testNumberWrite() throws IOException {
        ReaderOutputStream<LittleEndianDataReader> stream = createReader();

        LittleEndianDataWriter writer = new LittleEndianDataWriter(stream) {
            @Override
            public void reset() {
                ((ReaderOutputStream) stream).reset();
            }
        };

        writer.writeInt16((short) 0x1234);
        assertEquals((short) 0x1234, stream.read().readInt16());
        assertEquals(stream.size(), 2);

        writer.reset();

        writer.writeInt16((short) 0xABCD);
        assertEquals((short) 0xABCD, stream.read().readInt16());
        assertEquals(stream.size(), 2);

        writer.reset();

        writer.writeInt32(0x12345678);
        assertEquals(0x12345678, stream.read().readInt32());
        assertEquals(stream.size(), 4);

        writer.reset();

        writer.writeInt32(0x9ABCDEF0);
        assertEquals(0x9ABCDEF0, stream.read().readInt32());
        assertEquals(stream.size(), 4);

        writer.reset();

        writer.writeInt64(0x123456789ABCDEF0L);
        assertEquals(0x123456789ABCDEF0L, stream.read().readInt64());
        assertEquals(stream.size(), 8);

        writer.reset();

        writer.writeInt64(0x9ABCDEF012345678L);
        assertEquals(0x9ABCDEF012345678L, stream.read().readInt64());
        assertEquals(stream.size(), 8);
    }
}
