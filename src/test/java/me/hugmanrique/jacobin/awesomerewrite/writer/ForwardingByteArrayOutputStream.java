package me.hugmanrique.jacobin.awesomerewrite.writer;

import me.hugmanrique.jacobin.awesomerewrite.reader.DataReader;

import java.io.ByteArrayOutputStream;
import java.util.function.Function;

/**
 * @author Hugo Manrique
 * @since 25/12/2018
 */
public class ForwardingByteArrayOutputStream extends ByteArrayOutputStream {

    private final Function<byte[], DataReader> readerCreator;

    public ForwardingByteArrayOutputStream(Function<byte[], DataReader> readerCreator) {
        this.readerCreator = readerCreator;
    }

    public ForwardingByteArrayOutputStream(int size, Function<byte[], DataReader> readerCreator) {
        super(size);
        this.readerCreator = readerCreator;
    }

    public DataReader getReader() {
        return readerCreator.apply(toByteArray());
    }
}
