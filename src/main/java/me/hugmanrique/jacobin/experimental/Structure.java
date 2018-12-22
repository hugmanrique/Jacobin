package me.hugmanrique.jacobin.experimental;

import me.hugmanrique.jacobin.writer.ByteStreamWriter;

import java.io.IOException;

/**
 * @author Hugo Manrique
 * @since 22/12/2018
 */
public interface Structure {

    void write(ByteStreamWriter writer) throws IOException;
}
