package fr.euphyllia.fidorial.gradle.patcher.task

import org.gradle.api.DefaultTask
import org.gradle.api.file.ArchiveOperations
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.file.FileSystemOperations
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.tasks.CacheableTask
import org.gradle.api.tasks.InputDirectory
import org.gradle.api.tasks.InputFile
import org.gradle.api.tasks.InputFiles
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.PathSensitive
import org.gradle.api.tasks.PathSensitivity
import org.gradle.api.tasks.SkipWhenEmpty
import org.gradle.api.tasks.TaskAction
import java.io.File
import javax.inject.Inject

private const val PATCH_FILE_SUFFIX = ".patch"

@CacheableTask
abstract class ExtractPatchedFilesTask : DefaultTask() {

    @get:Inject
    abstract val fileSystemOperations: FileSystemOperations

    @get:Inject
    abstract val archiveOperations: ArchiveOperations

    @get:InputFile
    @get:PathSensitive(PathSensitivity.NONE)
    abstract val patchedZip: RegularFileProperty

    @get:InputDirectory
    @get:PathSensitive(PathSensitivity.RELATIVE)
    @get:SkipWhenEmpty
    abstract val patchesDir: DirectoryProperty

    @get:OutputDirectory
    abstract val outputDir: DirectoryProperty

    @TaskAction
    fun extract() {
        val paths = patchedPaths(patchesDir.get().asFile)

        if (paths.isEmpty()) {
            outputDir.get().asFile.deleteRecursively()
            return
        }

        fileSystemOperations.sync {
            from(archiveOperations.zipTree(patchedZip))
            into(outputDir)
            include { it.path in paths }
        }
    }

    private fun patchedPaths(root: File): Set<String> =
        root.walkTopDown()
            .filter { it.isFile && it.name.endsWith(PATCH_FILE_SUFFIX) }
            .map { it.relativeTo(root).invariantSeparatorsPath }
            .map { it.removeSuffix(PATCH_FILE_SUFFIX) }
            .toSet()
}
