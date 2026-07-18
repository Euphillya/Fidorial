package fr.fidorial.registrygen;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;

public class WorkingSpace implements AutoCloseable {

    private final Path rootDirectory;
    private final Path serverJar;
    private final Path dataDirectory;
    private final Path generatedDirectory;

    public WorkingSpace(final Path rootDirectory) {
        this.rootDirectory = rootDirectory;
        this.serverJar = rootDirectory.resolve("jar/server.jar");
        this.dataDirectory = rootDirectory.resolve("data");
        this.generatedDirectory = rootDirectory.resolve("generated");
    }

    public Path rootDirectory() {
        return rootDirectory;
    }

    public Path serverJar() {
        return serverJar;
    }

    public Path dataDirectory() {
        return dataDirectory;
    }

    public Path generatedDirectory() {
        return generatedDirectory;
    }

    /**
     * Closes this resource, relinquishing any underlying resources. This method is invoked
     * automatically on objects managed by the {@code try}-with-resources statement.
     *
     * @throws Exception if this resource cannot be closed
     */
    @Override
    public void close() throws Exception {

        deleteWorkingDirectories();
    }

    private void deleteWorkingDirectories() throws IOException {

        if (Files.notExists(rootDirectory)) {
            return;
        }

        try (final var paths = Files.walk(rootDirectory)) {

            paths.sorted(Comparator.reverseOrder()).forEach(path -> {
                try {
                    Files.deleteIfExists(path);
                } catch (final IOException exception) {
                    throw new UncheckedIOException(exception);
                }
            });
        }
    }
}