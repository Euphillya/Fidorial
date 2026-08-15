package fr.euphyllia.fidorial.gradle.patcher

import fr.euphyllia.fidorial.gradle.patcher.extension.DependencyPatcherExtension
import fr.euphyllia.fidorial.gradle.patcher.task.ApplyPatchesTask
import fr.euphyllia.fidorial.gradle.patcher.task.ExtractPatchedFilesTask
import fr.euphyllia.fidorial.gradle.patcher.task.RebuildPatchesTask
import io.codechicken.diffpatch.util.PatchMode
import org.gradle.api.NamedDomainObjectProvider
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.Configuration
import org.gradle.api.file.DuplicatesStrategy
import org.gradle.api.file.RegularFile
import org.gradle.api.plugins.JavaPlugin
import org.gradle.api.provider.Provider
import org.gradle.api.tasks.SourceSet
import org.gradle.api.tasks.SourceSetContainer
import org.gradle.api.tasks.Sync
import org.gradle.api.tasks.TaskProvider
import org.gradle.api.tasks.bundling.Jar
import org.gradle.kotlin.dsl.create
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.register

private const val PATCHER_TASK_GROUP = "dependency patcher"
private const val INTERNAL_PATCHER_TASK_GROUP = "dependency patcher internal"
private const val DIFF_PATCH_VERSION = "io.codechicken:DiffPatch:2.1.0.43"

class DependencyPatcherPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        val extension = project.extensions.create<DependencyPatcherExtension>("dependencyPatcher")

        val diffPatch =
            project.configurations.dependencyScope("diffPatch") {
                dependencies.add(
                    project.dependencies.create(DIFF_PATCH_VERSION),
                )
            }

        val diffPatchTool =
            project.configurations.resolvable("diffPatchResolvable") {
                extendsFrom(diffPatch)
            }

        extension.patchSets.configureEach {
            configure(project, diffPatchTool)
        }
    }

    private fun PatchSet.configure(
        project: Project,
        diffPatchTool: NamedDomainObjectProvider<out Configuration>,
    ) {
        val patchSet = this
        val capitalized = name.replaceFirstChar(Char::uppercase)

        configureConventions(project)
        // add to these by default
        patchSet.addPatchedLibraryTo.convention(
            listOf(
                project.configurations.named(JavaPlugin.IMPLEMENTATION_CONFIGURATION_NAME),
                project.configurations.named(JavaPlugin.TEST_IMPLEMENTATION_CONFIGURATION_NAME),
            ),
        )

        val originalSources =
            project.configurations.resolvable("${name}Sources") {
                isTransitive = false

                dependencies.addLater(
                    library.map {
                        project.dependencies.create(
                            it.copy().apply {
                                artifact {
                                    classifier = patchSet.classifier.get()
                                    type = "jar"
                                }
                            },
                        )
                    },
                )
            }

        val originalBinary =
            project.configurations.resolvable("${name}Binary") {
                isTransitive = false
                dependencies.addLater(
                    library.map {
                        project.dependencies.create(it)
                    },
                )
            }

        val originalSourcesJar =
            project.layout.file(
                originalSources.flatMap { it.elements.map { it.single().asFile } },
            )

        val originalBinaryJar =
            project.layout.file(
                originalBinary.flatMap { it.elements.map { it.single().asFile } },
            )

        val patchedZip = project.layout.buildDirectory.file("dependency-patcher/$name/patched.zip")
        val rejects = project.layout.buildDirectory.dir("dependency-patcher/$name/rejects")
        val patches = patchSet.patchesDir
        patches.map { it.asFile.mkdirs() }

        val applyPatches =
            project.tasks.register<ApplyPatchesTask>("apply${capitalized}Patches") {
                group = PATCHER_TASK_GROUP
                description = "Applies patches for '${patchSet.name}'."

                originalJar.set(originalSourcesJar)
                patchesDir.set(
                    patches.filter { it.asFile.exists() }
                )
                outputZip.set(patchedZip)
                rejectsDir.set(rejects)
                mode.set(patchMode)
                minFuzz.set(patchSet.minFuzz)
                maxOffset.set(patchSet.maxOffset)

                toolClasspath.from(diffPatchTool)
            }

        project.tasks.register<Sync>("setup${capitalized}PatchWorkspace") {
            group = PATCHER_TASK_GROUP
            description = "Builds a workspace for '${patchSet.name}' from the current patched source."

            from(project.zipTree(applyPatches.flatMap { it.outputZip }))
            into(workspaceDir)
        }

        project.tasks.register<RebuildPatchesTask>("rebuild${capitalized}Patches") {
            group = PATCHER_TASK_GROUP
            description = "Diffs the '${patchSet.name}' patch workspace against the original sources and generates patches."
            originalJar.set(originalSourcesJar)
            workspaceDir.set(patchSet.workspaceDir)
            patchesDir.set(patchSet.patchesDir)
            context.set(patchSet.context)
            toolClasspath.from(diffPatchTool)
        }

        project.pluginManager.withPlugin("java") {
            configureJava(
                project = project,
                patchSet = patchSet,
                capitalized = capitalized,
                applyPatches = applyPatches,
                originalBinaryJar = originalBinaryJar,
            )
        }
    }

    private fun PatchSet.configureConventions(project: Project) {
        patchesDir.convention(
            project.layout.projectDirectory.dir("patches/$name"),
        )

        workspaceDir.convention(
            project.layout.projectDirectory.dir("patches-workspace/$name"),
        )

        patchMode.convention(PatchMode.OFFSET)
        minFuzz.convention(0.75f)
        maxOffset.convention(100)
        context.convention(3)
        classifier.convention("sources")
    }

    private fun configureJava(
        project: Project,
        patchSet: PatchSet,
        capitalized: String,
        applyPatches: TaskProvider<ApplyPatchesTask>,
        originalBinaryJar: Provider<RegularFile>,
    ) {
        val sourceSets = project.extensions.getByType<SourceSetContainer>()

        patchSet.sourceSetsToAddTo.convention(
            listOf(
                sourceSets.named(SourceSet.MAIN_SOURCE_SET_NAME).get(),
            ),
        )

        val extractPatchedFiles =
            project.tasks.register<ExtractPatchedFilesTask>("extract${capitalized}PatchedFiles") {
                group = INTERNAL_PATCHER_TASK_GROUP
                description = "Extracts changed files for '${patchSet.name}'."
                patchedZip.set(applyPatches.flatMap { it.outputZip })
                patchesDir.set(patchSet.patchesDir)
                outputDir.set(project.layout.buildDirectory.dir("generated/sources/dependency-patcher/${patchSet.name}"))
            }

        val patchSourceSet =
            sourceSets.create("${patchSet.name}Patch") {
                java.srcDir(extractPatchedFiles.flatMap { it.outputDir })
            }

        project.configurations.named(patchSourceSet.compileOnlyConfigurationName) {
            dependencies.add(project.dependencies.create(project.files(originalBinaryJar)))
        }

        val patchedJar =
            project.tasks.register<Jar>("patched${capitalized}Jar") {
                group = INTERNAL_PATCHER_TASK_GROUP

                archiveBaseName.set(patchSet.name)
                archiveClassifier.set("patched")
                destinationDirectory.set(project.layout.buildDirectory.dir("dependency-patcher/${patchSet.name}"))
                duplicatesStrategy = DuplicatesStrategy.EXCLUDE
                from(patchSourceSet.output)
                from(project.zipTree(originalBinaryJar))
            }

        patchSet.addPatchedLibraryTo.get().forEach { config ->
            config.configure {
                dependencies.add(
                    project.dependencies.create(project.files(patchedJar)),
                )
            }
        }
    }
}
