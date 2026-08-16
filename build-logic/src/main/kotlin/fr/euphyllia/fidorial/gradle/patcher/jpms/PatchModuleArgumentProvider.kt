package fr.euphyllia.fidorial.gradle.patcher.jpms

import org.gradle.api.file.RegularFileProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.InputFile
import org.gradle.api.tasks.Optional
import org.gradle.api.tasks.PathSensitive
import org.gradle.api.tasks.PathSensitivity
import org.gradle.process.CommandLineArgumentProvider

abstract class PatchModuleArgumentProvider : CommandLineArgumentProvider {

    @get:Input
    @get:Optional
    abstract val module: Property<String>

    @get:Input
    @get:Optional
    abstract val joinedModule: Property<String>

    @get:InputFile
    @get:PathSensitive(PathSensitivity.RELATIVE)
    abstract val patchModule: RegularFileProperty

    override fun asArguments(): Iterable<String> {
        if (!module.isPresent) {
            return emptyList()
        }

        val moduleName = module.get()

        return buildList {
            add("--patch-module")
            add("$moduleName=${patchModule.get().asFile.absolutePath}")

            add("--add-reads")
            add("$moduleName=${joinedModule.orNull ?: "ALL-UNNAMED"}")
        }
    }
}
