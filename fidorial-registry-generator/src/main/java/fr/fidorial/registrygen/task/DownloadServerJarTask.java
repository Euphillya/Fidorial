package fr.fidorial.registrygen.task;

import org.gradle.api.DefaultTask;
import org.gradle.api.file.RegularFileProperty;
import org.gradle.api.provider.Property;
import org.gradle.api.tasks.CacheableTask;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.OutputFile;
import org.gradle.api.tasks.TaskAction;

@CacheableTask
public abstract class DownloadServerJarTask extends DefaultTask {

    @Input
    public abstract Property<String> getMinecraftVersion();

    @OutputFile
    public abstract RegularFileProperty getServerJar();

    @TaskAction
    public void download() {

        //TODO: Download the server jar
    }
}
