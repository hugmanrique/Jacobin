package me.hugmanrique.jacobin.editor;

import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertEquals;

/**
 * @author Hugo Manrique
 * @since 25/12/2018
 */
public class FileEditorTest {

    @Test
    public void testWriteAndRead() throws IOException {
        File tempFile = TestFileEditorUtils.createTempFile("writeRead");

        try (FileEditor editor = new LittleEndianFileEditor(tempFile)) {
            editor.writeByte(0x12);
            editor.setOffset(0);

            assertEquals(0x12, editor.readByte());
            assertEquals(1, editor.getOffset());
        }
    }

    @Test
    public void testBackAndRead() throws IOException {
        File tempFile = TestFileEditorUtils.createTempFile("backAndRead");

        try (FileEditor editor = new BigEndianFileEditor(tempFile)) {
            editor.skip(2);
            editor.writeByte(0x34);

            editor.reset();

            // Jumped bytes should be zeroed out
            assertEquals(0, editor.readByte());
            assertEquals(0, editor.readByte());
        }
    }
}
