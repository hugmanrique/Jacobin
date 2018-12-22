package me.hugmanrique.jacobin.experimental.temp;

import me.hugmanrique.jacobin.experimental.Structure;
import me.hugmanrique.jacobin.reader.ByteStreamReader;
import me.hugmanrique.jacobin.writer.ByteStreamWriter;

import java.io.IOException;

/**
 * @author Hugo Manrique
 * @since 22/12/2018
 */
public class Person implements Structure {
    private final String name;
    private final int id;
    private final String email;

    public Person(ByteStreamReader reader) throws IOException {
        this.name = "Foo Bar";
        this.id = reader.readInt32();
        this.email = null;
    }

    @Override
    public void write(ByteStreamWriter writer) throws IOException {
        writer.writeInt32(id);
    }
}
