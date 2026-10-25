package net.bandit.many_bows;

import static org.junit.jupiter.api.Assertions.fail;

import com.google.gson.JsonParser;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;

class ResourceJsonTest {

    @Test
    void allResourceJsonIsValid() throws IOException {
        Path root = Path.of("src/main/resources");
        try (Stream<Path> files = Files.walk(root)) {
            for (Path p : (Iterable<Path>) files.filter(f -> f.toString().endsWith(".json"))::iterator) {
                try (Reader r = Files.newBufferedReader(p)) {
                    JsonParser.parseReader(r);
                } catch (Exception e) {
                    fail(p + ": " + e.getMessage());
                }
            }
        }
    }
}
