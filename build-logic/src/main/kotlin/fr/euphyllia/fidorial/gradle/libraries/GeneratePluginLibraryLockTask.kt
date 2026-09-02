package fr.euphyllia.fidorial.gradle.libraries

import org.gradle.api.DefaultTask
import org.gradle.api.GradleException
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.component.ModuleComponentIdentifier
import org.gradle.api.artifacts.result.ResolvedArtifactResult
import org.gradle.api.file.ConfigurableFileCollection
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.api.provider.ListProperty
import org.gradle.api.tasks.CacheableTask
import org.gradle.api.tasks.Classpath
import org.gradle.api.tasks.Internal
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.SourceSetContainer
import org.gradle.api.tasks.TaskAction
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.register

/**
 * Writes a plugin's `META-INF/fidorial/libraries.list`.
 *
 * The graph is flattened and checksummed here, at build time, so the server never needs a
 * Maven resolver at runtime — and so a plugin's dependency set is the same on every
 * machine that ever loads it.
 */
@CacheableTask
abstract class GeneratePluginLibraryLockTask : DefaultTask() {

    @get:Internal
    abstract val libraryArtifacts: ListProperty<ResolvedArtifactResult>

    @get:Classpath
    abstract val libraryFiles: ConfigurableFileCollection

    @get:OutputDirectory
    abstract val outputDirectory: DirectoryProperty

    @TaskAction
    fun generate() {
        val output = outputDirectory.get().asFile
        output.deleteRecursively()

        val lines = libraryArtifacts.get().map { artifact ->
            val identifier = artifact.id.componentIdentifier
            if (identifier !is ModuleComponentIdentifier) {
                throw GradleException(
                    "${artifact.file} has no Maven coordinates, so the server cannot download it. " +
                            "Publish it to a repository, or keep it as a normal `implementation` dependency " +
                            "and shade it yourself.",
                )
            }
            val coordinates = LibraryLists.coordinatesOf(identifier, artifact.file)
            "$coordinates ${LibraryLists.sha256(artifact.file)} ${artifact.file.length()}"
        }.sorted()

        val file = output.resolve("META-INF/fidorial/libraries.list")
        file.parentFile.mkdirs()
        file.writeText(
            LibraryLists.header("Downloaded by the server before this plugin is loaded.", name) +
                    lines.joinToString("\n", postfix = "\n"),
        )
        logger.lifecycle("Plugin library lock: ${lines.size} artifact(s)")
    }
}

/**
 * Adds a `fidorialLibrary` configuration to a plugin project.
 *
 * Dependencies declared this way are on the compile and runtime classpath as usual, and the
 * resolved graph — transitive dependencies included — is written into the plugin jar.
 */
class PluginLibrariesPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        val library = project.configurations.create("fidorialLibrary") {
            isCanBeConsumed = false
            isCanBeResolved = true
            description = "Libraries the server downloads before loading this plugin"
        }

        project.configurations.named("compileOnly") { extendsFrom(library) }
        project.configurations.named("testImplementation") { extendsFrom(library) }

        val lock = project.tasks.register<GeneratePluginLibraryLockTask>("generateLibraryLock") {
            group = "build"
            description = "Writes META-INF/fidorial/libraries.list into the plugin jar."
            libraryArtifacts.set(library.incoming.artifacts.resolvedArtifacts)
            libraryFiles.from(library)
            outputDirectory.set(project.layout.buildDirectory.dir("generated/fidorial-libraries"))
        }

        project.extensions.getByType<JavaPluginExtension>()
        project.extensions.getByType<SourceSetContainer>().named("main") {
            resources.srcDir(lock)
        }
    }
}
