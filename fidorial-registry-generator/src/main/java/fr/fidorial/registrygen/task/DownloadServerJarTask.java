package fr.fidorial.registrygen.task;

import fr.fidorial.registrygen.model.ServerDownload;
import org.gradle.api.DefaultTask;
import org.gradle.api.file.RegularFileProperty;
import org.gradle.api.provider.Property;
import org.gradle.api.tasks.CacheableTask;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.OutputFile;
import org.gradle.api.tasks.TaskAction;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import static fr.fidorial.registrygen.download.ServerJarURLGrabber.fetchServerDownload;

/**
 * The DownloadServerJarTask is a Gradle task responsible for downloading the Minecraft server JAR
 * file for a specific Minecraft version. It utilizes server metadata to locate the download URL
 * and retrieves the server JAR file.
 *
 * @since 0.1.0
 */
@CacheableTask
public abstract class DownloadServerJarTask extends DefaultTask {

    @Input
    public abstract Property<String> getMinecraftVersion();

    @OutputFile
    public abstract RegularFileProperty getServerJar();

    @TaskAction
    public void download() throws IOException {

        final ServerDownload serverDownload = fetchServerDownload(getMinecraftVersion().get());

        final var destination = getServerJar().get().getAsFile().toPath();

        Files.createDirectories(destination.getParent());
        try (final var input = serverDownload.url().toURL().openStream()) {
            Files.copy(input, destination, StandardCopyOption.REPLACE_EXISTING);
        }
    }
}
