package main.Files;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Read {
    public static String readFileToString(String filePath) throws IOException {
        return Files.readString(Path.of(filePath));
    }
}
