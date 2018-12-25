package me.hugmanrique.jacobin.writer;

import me.hugmanrique.jacobin.reader.DataReader;
import org.junit.Ignore;

import java.io.ByteArrayOutputStream;
import java.util.function.Function;

/**
 * @author Hugo Manrique
 * @since 25/12/2018
 */
@Ignore
public class ReaderOutputStream<T extends DataReader> extends ByteArrayOutputStream {

    private final Function<byte[], T> readerFactory;

    public ReaderOutputStream(Function<byte[], T> readerFactory) {
        this.readerFactory = readerFactory;
    }

    public ReaderOutputStream(int size, Function<byte[], T> readerFactory) {
        super(size);
        this.readerFactory = readerFactory;
    }

    public T read() {
        return readerFactory.apply(toByteArray());
    }
}
