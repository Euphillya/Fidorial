package fr.fidorial.registrygen.task;

import org.gradle.api.DefaultTask;
import org.gradle.api.file.DirectoryProperty;
import org.gradle.api.provider.MapProperty;
import org.gradle.api.provider.Property;
import org.gradle.api.tasks.CacheableTask;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.InputDirectory;
import org.gradle.api.tasks.OutputDirectory;
import org.gradle.api.tasks.PathSensitive;
import org.gradle.api.tasks.PathSensitivity;
import org.gradle.api.tasks.TaskAction;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;

/**
 * The GenerateRegistriesTask is a Gradle task designed for generating registry source files
 * based on Minecraft version-specific reports. This task processes input reports, applies
 * provided registry mappings, and generates source files in the specified output directory.
 *
 * @since 0.1.0
 */
@CacheableTask
public abstract class GenerateRegistriesTask extends DefaultTask {

    @Input
    public abstract Property<String> getMinecraftVersion();

    @Input
    public abstract Property<String> getGeneratedPackage();

    @Input
    public abstract MapProperty<String, String> getRegistries();

    @InputDirectory
    @PathSensitive(PathSensitivity.RELATIVE)
    public abstract DirectoryProperty getReportsDirectory();

    @OutputDirectory
    public abstract DirectoryProperty getGeneratedSourcesDirectory();

    @TaskAction
    public void generate() throws IOException {
        final Path output = getGeneratedSourcesDirectory().get().getAsFile().toPath();
        recreateDirectory(output);

        //TODO: Generate the sources
    }

    private static void recreateDirectory(final Path directory) throws IOException {
        if (Files.exists(directory)) {
            try (final var paths = Files.walk(directory)) {
                for (final Path path : paths.sorted(Comparator.reverseOrder()).toList()) {
                    Files.deleteIfExists(path);
                }
            }
        }
        Files.createDirectories(directory);
    }
}
