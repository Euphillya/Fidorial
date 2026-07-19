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

import static fr.fidorial.registrygen.download.ServerJarURLGrabber.fetchServerDownload;

@CacheableTask
public abstract class DownloadServerJarTask extends DefaultTask {

    @Input
    public abstract Property<String> getMinecraftVersion();

    @OutputFile
    public abstract RegularFileProperty getServerJar();

    @TaskAction
    public void download() {

        try {
            final ServerDownload serverDownload = fetchServerDownload(getMinecraftVersion().get());
        } catch(final IOException e) {
            throw new RuntimeException(e);
        }
    }
}
