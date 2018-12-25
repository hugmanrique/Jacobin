package me.hugmanrique.jacobin.awesomerewrite.writer;

import me.hugmanrique.jacobin.awesomerewrite.reader.BigEndianDataReader;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.function.Function;

import static org.junit.Assert.assertEquals;

/**
 * @author Hugo Manrique
 * @since 25/12/2018
 */
public class BigEndianDataWriterTest {

    private static Function<byte[], BigEndianDataReader> READER_FACTORY =
            bytes -> new BigEndianDataReader(
                    new ByteArrayInputStream(bytes)
            );

    private ReaderOutputStream<BigEndianDataReader> createReader() {
        return new ReaderOutputStream<>(READER_FACTORY);
    }

    @Test
    public void testNumberWrite() throws IOException {
        ReaderOutputStream<BigEndianDataReader> stream = createReader();

        BigEndianDataWriter writer = new BigEndianDataWriter(stream) {
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
