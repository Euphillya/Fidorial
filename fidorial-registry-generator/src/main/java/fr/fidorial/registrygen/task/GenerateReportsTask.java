package fr.fidorial.registrygen.task;

import org.gradle.api.DefaultTask;
import org.gradle.api.file.DirectoryProperty;
import org.gradle.api.file.RegularFileProperty;
import org.gradle.api.provider.ListProperty;
import org.gradle.api.provider.Property;
import org.gradle.api.tasks.CacheableTask;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.InputFile;
import org.gradle.api.tasks.OutputDirectory;
import org.gradle.api.tasks.PathSensitive;
import org.gradle.api.tasks.PathSensitivity;
import org.gradle.api.tasks.TaskAction;

import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

@CacheableTask
public abstract class GenerateReportsTask extends DefaultTask {

    @Input
    public abstract Property<String> getMinecraftVersion();

    @Input
    public abstract Property<String> getJavaExecutable();

    @Input
    public abstract ListProperty<String> getDataGeneratorArguments();

    @InputFile
    @PathSensitive(PathSensitivity.NONE)
    public abstract RegularFileProperty getServerJar();

    @OutputDirectory
    public abstract DirectoryProperty getDataDirectory();

    @TaskAction
    public void generate() throws IOException, InterruptedException {
        final var dataDirectory = getDataDirectory().get().getAsFile().toPath();
        Files.createDirectories(dataDirectory);

        final List<String> command = new ArrayList<>();
        command.add(getJavaExecutable().get());
        command.add("-DbundlerMainClass=net.minecraft.data.Main");
        command.add("-jar");
        command.add(getServerJar().get().getAsFile().getAbsolutePath());
        command.addAll(getDataGeneratorArguments().get());
        command.add("--output");
        command.add(dataDirectory.toAbsolutePath().toString());

        getLogger().lifecycle("Running Minecraft data generator for Minecraft {}", getMinecraftVersion().get());
        final Process process = new ProcessBuilder(command)
                .directory(dataDirectory.toFile())
                .inheritIO()
                .start();

        final int exitCode = process.waitFor();
        if (exitCode != 0) {
            throw new IOException("Minecraft data generator exited with code " + exitCode);
        }
    }
}
