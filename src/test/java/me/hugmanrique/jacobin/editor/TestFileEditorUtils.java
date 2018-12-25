package me.hugmanrique.jacobin.editor;

import java.io.File;
import java.io.IOException;

/**
 * @author Hugo Manrique
 * @since 25/12/2018
 */
public class TestFileEditorUtils {

    static File createTempFile(String name) throws IOException {
        File tempFile = File.createTempFile(name + "EditorTest", ".tmp");
        tempFile.deleteOnExit();

        return tempFile;
    }
}
