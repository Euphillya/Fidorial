package fr.euphyllia.fidorial.gradle.patcher.workers

import io.codechicken.diffpatch.cli.PatchOperation
import io.codechicken.diffpatch.util.Input
import io.codechicken.diffpatch.util.LogLevel
import io.codechicken.diffpatch.util.Output
import io.codechicken.diffpatch.util.PatchMode
import io.codechicken.diffpatch.util.archiver.ArchiveFormat
import org.gradle.api.GradleException
import org.gradle.api.logging.Logging
import org.gradle.api.provider.Property
import org.gradle.workers.WorkAction
import org.gradle.workers.WorkParameters
import java.nio.file.Path

interface ApplyPatchesParams : WorkParameters {
    val originalJar: Property<String>
    val patchesDir: Property<String>
    val outputZip: Property<String>
    val rejectsDir: Property<String>
    val mode: Property<PatchMode>
    val minFuzz: Property<Float>
    val maxOffset: Property<Int>
}

abstract class ApplyPatchesWorkAction : WorkAction<ApplyPatchesParams> {
    private val logger = Logging.getLogger(RebuildPatchesWorkAction::class.java)

    override fun execute() {
        val params = parameters

        val originalPath = Path.of(params.originalJar.get())
        val patchesPath = Path.of(params.patchesDir.get())
        val outputPath = Path.of(params.outputZip.get())
        val rejectsPath = Path.of(params.rejectsDir.get())

        val operation =
            PatchOperation
                .builder()
                .logTo(logger::lifecycle)
                .level(LogLevel.ERROR)
                .summary(false)
                .baseInput(Input.MultiInput.archive(ArchiveFormat.ZIP, originalPath))
                .patchesInput(Input.MultiInput.folder(patchesPath))
                .patchedOutput(Output.MultiOutput.archive(ArchiveFormat.ZIP, outputPath))
                .rejectsOutput(Output.MultiOutput.folder(rejectsPath))
                .mode(params.mode.get())
                .minFuzz(params.minFuzz.get())
                .maxOffset(params.maxOffset.get())
                .ignorePrefix("META-INF")
                .build()
                .operate()

        if (operation.exit != 0) {
            throw GradleException(
                "Failed to apply patches from '$patchesPath' onto '$originalPath'. See rejected hunks in '$rejectsPath'.",
            )
        }

        logger.lifecycle("Applied ${operation.summary?.changedFiles} patches")
    }
}
