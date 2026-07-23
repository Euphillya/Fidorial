package fr.fidorial.registrygen;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.Objects;

/**
 * Represents a workspace environment for generating Minecraft version-specific registries.
 * The `WorkingSpace` class manages directories and files associated with the
 * Minecraft version, server JAR, data output, and reports.
 *
 * This class provides facilities to:
 * - Initialize necessary directory structures.
 * - Access paths associated with the workspace (e.g., server JAR location).
 * - Verify the existence of expected files or directories, such as reports or the server JAR.
 * - Clean up workspace directories, either for a specific Minecraft version or the entire root.
 *
 * @since 0.1.0
 */
public final class WorkingSpace {

    private final String minecraftVersion;

    private final Path rootDirectory;
    private final Path minecraftDirectory;
    private final Path versionDirectory;

    private final Path jarDirectory;
    private final Path serverJar;

    private final Path dataDirectory;
    private final Path reportsDirectory;

    /**
     * Creates a version-specific registry-generation workspace.
     *
     * @param rootDirectory persistent working root, such as {@code build/working}
     * @param minecraftVersion target Minecraft version
     */
    public WorkingSpace(final Path rootDirectory, final String minecraftVersion) {
        Objects.requireNonNull(rootDirectory, "rootDirectory");
        Objects.requireNonNull(minecraftVersion, "minecraftVersion");

        if (minecraftVersion.isBlank()) {
            throw new IllegalArgumentException(
                    "Minecraft version cannot be blank."
            );
        }

        this.minecraftVersion = minecraftVersion.trim();

        this.rootDirectory = rootDirectory
                .toAbsolutePath()
                .normalize();

        this.minecraftDirectory = this.rootDirectory.resolve("minecraft");

        this.versionDirectory = minecraftDirectory.resolve(this.minecraftVersion);

        this.jarDirectory = versionDirectory.resolve("jar");
        this.serverJar = jarDirectory.resolve("server.jar");

        this.dataDirectory = versionDirectory.resolve("data");
        this.reportsDirectory = dataDirectory.resolve("reports");
    }

    /**
     * Creates the directories required by the registry-generation pipeline.
     *
     * @throws IOException when one of the directories cannot be created
     */
    public void initialize() throws IOException {
        Files.createDirectories(jarDirectory);
        Files.createDirectories(dataDirectory);
    }

    /**
     * Returns the target Minecraft version.
     *
     * @return Minecraft version
     */
    public String minecraftVersion() {
        return minecraftVersion;
    }

    /**
     * Returns the persistent working root.
     *
     * <p>Example:</p>
     *
     * <pre>{@code
     * fidorial-registry-generator/build/working
     * }</pre>
     *
     * @return working root
     */
    public Path rootDirectory() {
        return rootDirectory;
    }

    /**
     * Returns the directory containing all Minecraft-version workspaces.
     *
     * <p>Example:</p>
     *
     * <pre>{@code
     * fidorial-registry-generator/build/working/minecraft
     * }</pre>
     *
     * @return Minecraft workspace directory
     */
    public Path minecraftDirectory() {
        return minecraftDirectory;
    }

    /**
     * Returns the workspace for the selected Minecraft version.
     *
     * <p>Example:</p>
     *
     * <pre>{@code
     * fidorial-registry-generator/build/working/minecraft/26.2
     * }</pre>
     *
     * @return version-specific workspace
     */
    public Path versionDirectory() {
        return versionDirectory;
    }

    /**
     * Returns the directory containing the downloaded server JAR.
     *
     * @return server JAR directory
     */
    public Path jarDirectory() {
        return jarDirectory;
    }

    /**
     * Returns the location of the official Minecraft server JAR.
     *
     * <p>Example:</p>
     *
     * <pre>{@code
     * build/working/minecraft/26.2/jar/server.jar
     * }</pre>
     *
     * @return server JAR path
     */
    public Path serverJar() {
        return serverJar;
    }

    /**
     * Returns the output directory passed to Mojang's data generator.
     *
     * <p>Mojang will normally create directories such as {@code reports} beneath this directory.</p>
     *
     * @return data-generator output directory
     */
    public Path dataDirectory() {
        return dataDirectory;
    }

    /**
     * Returns the expected Mojang registry-report directory.
     *
     * <p>Example:</p>
     *
     * <pre>{@code
     * build/working/minecraft/26.2/data/reports
     * }</pre>
     *
     * @return generated reports directory
     */
    public Path reportsDirectory() {
        return reportsDirectory;
    }

    /**
     * Returns whether a server JAR currently exists in this workspace.
     *
     * @return {@code true} when the server JAR exists as a regular file
     */
    public boolean hasServerJar() {
        return Files.isRegularFile(serverJar);
    }

    /**
     * Returns whether Mojang's reports directory currently exists.
     *
     * @return {@code true} when the reports directory exists
     */
    public boolean hasReports() {
        return Files.isDirectory(reportsDirectory);
    }

    /**
     * Deletes the directory associated with the current Minecraft version and all
     * of its contents recursively.
     *
     * This method targets the directory represented by the {@code versionDirectory}
     * field of the {@code WorkingSpace} class. It removes all files and
     * subdirectories within it. If the directory does not exist, the method exits without
     * performing any action.
     *
     * This operation is typically used to clean up resources specific to a Minecraft
     * version workspace. Use this method cautiously, as it permanently deletes all
     * contents within the directory and cannot be undone.
     *
     * @throws IOException if an I/O error occurs while deleting files or directories
     */
    public void deleteVersionDirectory() throws IOException {
        deleteRecursively(versionDirectory);
    }


    /**
     * Deletes the root directory and all of its contents recursively.
     *
     * This method removes the persistent working root directory specified by the
     * {@code rootDirectory} field. All nested files and subdirectories are
     * deleted as part of this operation. If the directory does not exist,
     * the method exits without performing any action.
     *
     * This operation is intended for scenarios where the entire workspace
     * structure needs to be cleaned up. Use this with caution as it cannot
     * be undone.
     *
     * @throws IOException if an I/O error occurs while deleting files or directories
     */
    public void deleteRootDirectory() throws IOException {
        deleteRecursively(rootDirectory);
    }

    private static void deleteRecursively(final Path directory)
            throws IOException {

        if (Files.notExists(directory)) {
            return;
        }

        try (final var paths = Files.walk(directory)) {
            final var orderedPaths = paths
                    .sorted(Comparator.reverseOrder())
                    .toList();

            for (final Path path : orderedPaths) {
                Files.deleteIfExists(path);
            }
        }
    }
}