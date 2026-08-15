package fr.euphyllia.fidorial.gradle.patcher.jpms

import org.gradle.api.file.DirectoryProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.InputDirectory
import org.gradle.api.tasks.PathSensitive
import org.gradle.api.tasks.PathSensitivity
import org.gradle.process.CommandLineArgumentProvider

abstract class PatchModuleArgumentProvider : CommandLineArgumentProvider {
    @get:Input
    abstract val module: Property<String>

    @get:InputDirectory
    @get:PathSensitive(PathSensitivity.RELATIVE)
    abstract val classesDirectory: Property<String>

    override fun asArguments(): Iterable<String> {
        val moduleName = module.orNull ?: return emptyList()

        return listOf(
            "--patch-module",
            "$moduleName=${classesDirectory.get()}",
        )
    }
}
