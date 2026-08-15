package fr.euphyllia.fidorial.gradle.patcher.task

import fr.euphyllia.fidorial.gradle.patcher.workers.ApplyPatchesWorkAction
import io.codechicken.diffpatch.util.PatchMode
import org.gradle.api.DefaultTask
import org.gradle.api.file.ConfigurableFileCollection
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.CacheableTask
import org.gradle.api.tasks.Classpath
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.InputDirectory
import org.gradle.api.tasks.Optional
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.OutputFile
import org.gradle.api.tasks.PathSensitive
import org.gradle.api.tasks.PathSensitivity
import org.gradle.api.tasks.SkipWhenEmpty
import org.gradle.api.tasks.TaskAction
import org.gradle.workers.WorkerExecutor
import javax.inject.Inject
import kotlin.io.path.copyTo

@CacheableTask
abstract class ApplyPatchesTask : DefaultTask() {
    @get:Inject
    abstract val workerExecutor: WorkerExecutor

    @get:Classpath
    abstract val originalJar: RegularFileProperty

    @get:InputDirectory
    @get:PathSensitive(PathSensitivity.RELATIVE)
    @get:Optional
    abstract val patchesDir: DirectoryProperty

    @get:OutputFile
    abstract val outputZip: RegularFileProperty

    @get:OutputDirectory
    abstract val rejectsDir: DirectoryProperty

    @get:Input
    abstract val mode: Property<PatchMode>

    @get:Input
    abstract val minFuzz: Property<Float>

    @get:Input
    abstract val maxOffset: Property<Int>

    @get:Classpath
    abstract val toolClasspath: ConfigurableFileCollection

    @TaskAction
    fun apply() {
        val original = originalJar.get().asFile
        val output = outputZip.get().asFile
        val patches = patchesDir.orNull?.asFile

        if (patches == null) {
            original.toPath().copyTo(output.toPath(), overwrite = true)
            return
        }

        workerExecutor.classLoaderIsolation {
            classpath.from(toolClasspath)
        }
            .submit(ApplyPatchesWorkAction::class.java) {
                originalJar.set(original.absolutePath)
                patchesDir.set(patches.absolutePath)
                outputZip.set(output.absolutePath)
                rejectsDir.set(
                    this@ApplyPatchesTask.rejectsDir.get().asFile.absolutePath,
                )
                mode.set(this@ApplyPatchesTask.mode)
                minFuzz.set(this@ApplyPatchesTask.minFuzz)
                maxOffset.set(this@ApplyPatchesTask.maxOffset)
            }
    }
}
