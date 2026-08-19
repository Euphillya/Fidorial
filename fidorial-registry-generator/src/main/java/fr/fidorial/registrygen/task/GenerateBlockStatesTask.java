package fr.fidorial.registrygen.task;

import fr.fidorial.registrygen.generate.RegistryGenerator;
import org.gradle.api.DefaultTask;
import org.gradle.api.file.DirectoryProperty;
import org.gradle.api.file.RegularFileProperty;
import org.gradle.api.tasks.CacheableTask;
import org.gradle.api.tasks.InputFile;
import org.gradle.api.tasks.OutputDirectory;
import org.gradle.api.tasks.PathSensitive;
import org.gradle.api.tasks.PathSensitivity;
import org.gradle.api.tasks.TaskAction;

import javax.inject.Inject;
import java.io.IOException;
import java.io.UncheckedIOException;

@CacheableTask
public abstract class GenerateBlockStatesTask extends DefaultTask {

    @InputFile
    @PathSensitive(PathSensitivity.NONE)
    public abstract RegularFileProperty getBlocksReport();

    @OutputDirectory
    public abstract DirectoryProperty getGeneratedSourcesDirectory();

    @Inject
    public GenerateBlockStatesTask() {
    }

    @TaskAction
    public void generate() {
        try {
            new RegistryGenerator().generateBlockStates(
                    getBlocksReport().get().getAsFile().toPath(),
                    getGeneratedSourcesDirectory().get().getAsFile().toPath()
            );
        } catch (final IOException e) {
            throw new UncheckedIOException("Failed to generate block states", e);
        }
    }
}
