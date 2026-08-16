package fr.euphyllia.fidorial.gradle.patcher.workers

import io.codechicken.diffpatch.cli.DiffOperation
import io.codechicken.diffpatch.util.Input
import io.codechicken.diffpatch.util.LogLevel
import io.codechicken.diffpatch.util.Output
import org.gradle.api.logging.Logging
import org.gradle.api.provider.Property
import org.gradle.workers.WorkAction
import org.gradle.workers.WorkParameters
import java.nio.file.Path

interface RebuildPatchesParams : WorkParameters {
    val originalJar: Property<String>
    val workspaceDir: Property<String>
    val patchesDir: Property<String>
    val context: Property<Int>
}

abstract class RebuildPatchesWorkAction : WorkAction<RebuildPatchesParams> {
    private val logger = Logging.getLogger(RebuildPatchesWorkAction::class.java)

    override fun execute() {
        val p = parameters

        val originalPath = Path.of(p.originalJar.get())
        val workspacePath = Path.of(p.workspaceDir.get())
        val patchesPath = Path.of(p.patchesDir.get())

        patchesPath.toFile().deleteRecursively()
        patchesPath.toFile().mkdirs()

        val operation =
            DiffOperation
                .builder()
                .logTo(logger::lifecycle)
                .level(LogLevel.ERROR)
                .summary(false)
                .autoHeader(true)
                .context(p.context.get())
                .baseInput(Input.MultiInput.detectedArchive(originalPath))
                .changedInput(Input.MultiInput.folder(workspacePath))
                .patchesOutput(Output.MultiOutput.folder(patchesPath))
                .ignorePrefix("META-INF")
                .build()
                .operate()

        logger.lifecycle("Rebuilt ${operation.summary?.changedFiles} patches")
    }
}
