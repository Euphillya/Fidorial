package fr.euphyllia.fidorial.gradle.patcher.task

import fr.euphyllia.fidorial.gradle.patcher.workers.RebuildPatchesWorkAction
import org.gradle.api.DefaultTask
import org.gradle.api.file.ConfigurableFileCollection
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Classpath
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.InputDirectory
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.UntrackedTask
import org.gradle.workers.WorkerExecutor
import javax.inject.Inject

@UntrackedTask(because = "Rebuild should always run when requested.")
abstract class RebuildPatchesTask : DefaultTask() {
    @get:Inject
    abstract val workerExecutor: WorkerExecutor

    @get:Classpath
    abstract val originalJar: RegularFileProperty

    @get:InputDirectory
    abstract val workspaceDir: DirectoryProperty

    @get:OutputDirectory
    abstract val patchesDir: DirectoryProperty

    @get:Input
    abstract val context: Property<Int>

    @get:Classpath
    abstract val toolClasspath: ConfigurableFileCollection

    @TaskAction
    fun rebuild() {
        val originalJarPath = originalJar.get().asFile.absolutePath
        val workspacePath = workspaceDir.get().asFile.absolutePath
        val patchesPath = patchesDir.get().asFile.absolutePath
        val patchContext = context.get()

        val queue =
            workerExecutor.classLoaderIsolation {
                classpath.from(toolClasspath)
            }

        queue.submit(RebuildPatchesWorkAction::class.java) {
            originalJar.set(originalJarPath)
            workspaceDir.set(workspacePath)
            patchesDir.set(patchesPath)
            context.set(patchContext)
        }
    }
}
