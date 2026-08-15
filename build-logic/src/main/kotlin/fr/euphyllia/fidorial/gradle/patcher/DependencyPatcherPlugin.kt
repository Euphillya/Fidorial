package fr.euphyllia.fidorial.gradle.patcher

import fr.euphyllia.fidorial.gradle.patcher.extension.DependencyPatcherExtension
import fr.euphyllia.fidorial.gradle.patcher.jpms.PatchModuleArgumentProvider
import fr.euphyllia.fidorial.gradle.patcher.task.ApplyPatchesTask
import fr.euphyllia.fidorial.gradle.patcher.task.ExtractPatchedFilesTask
import fr.euphyllia.fidorial.gradle.patcher.task.RebuildPatchesTask
import fr.euphyllia.fidorial.gradle.patcher.util.LibraryVersions
import org.gradle.api.NamedDomainObjectProvider
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.Configuration
import org.gradle.api.file.DuplicatesStrategy
import org.gradle.api.file.RegularFile
import org.gradle.api.provider.Provider
import org.gradle.api.tasks.SourceSet
import org.gradle.api.tasks.SourceSetContainer
import org.gradle.api.tasks.Sync
import org.gradle.api.tasks.TaskProvider
import org.gradle.api.tasks.bundling.Jar
import org.gradle.api.tasks.compile.JavaCompile
import org.gradle.kotlin.dsl.create
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.named
import org.gradle.kotlin.dsl.newInstance
import org.gradle.kotlin.dsl.register

private const val PATCHER_TASK_GROUP = "dependency patcher"
private const val INTERNAL_PATCHER_TASK_GROUP = "dependency patcher internal"

class DependencyPatcherPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        val extension = project.extensions.create<DependencyPatcherExtension>("dependencyPatcher")

        val diffPatch = project.configurations.dependencyScope("diffPatchConfig") {
            defaultDependencies {
                dependencies.add(
                    project.dependencies.create("io.codechicken:DiffPatch:${LibraryVersions.DIFF_PATCH}")
                )
            }
        }

        val diffPatchTool = project.configurations.resolvable("diffPatchResolvableConfig") {
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
        val capitalized = name.replaceFirstChar(Char::uppercase)

        val originalSources = project.configurations.resolvable("${name}Sources") {
            isTransitive = false
            dependencies.addLater(
                library.map {
                    project.dependencies.create(
                        it.copy().apply {
                            artifact {
                                classifier = this@configure.classifier.get()
                                type = "jar"
                            }
                        },
                    )
                },
            )
        }

        val originalBinary = project.configurations.resolvable("${name}Binary") {
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

        val applyPatches = project.tasks.register<ApplyPatchesTask>("apply${capitalized}Patches") {
            group = PATCHER_TASK_GROUP
            description = "Applies patches for '${this@configure.name}'."

            originalJar.set(originalSourcesJar)
            patchesDir.set(
                this@configure.patchesDir.filter { it.asFile.exists() }
            )
            outputZip.set(patchedZip)
            rejectsDir.set(rejects)
            mode.set(patchMode)
            minFuzz.set(this@configure.minFuzz)
            maxOffset.set(this@configure.maxOffset)

            toolClasspath.from(diffPatchTool)
        }

        val setupWorkspace = project.tasks.register<Sync>("setup${capitalized}PatchWorkspace") {
            group = PATCHER_TASK_GROUP
            description =
                "Builds a workspace for '${this@configure.name}' from the current patched source."

            from(project.zipTree(applyPatches.flatMap { it.outputZip }))
            into(this@configure.workspaceDir)
        }

        val sourceSets = project.extensions.getByType<SourceSetContainer>()

        sourceSets.register("${this@configure.name}Workspace") {
            java.srcDir(setupWorkspace.map { it.destinationDir })
            this@configure.dependenciesFrom.get().forEach {
                val sourceSetToJoin = sourceSets.named(it)
                compileClasspath += sourceSetToJoin.get().compileClasspath
            }
        }

        project.tasks.register<RebuildPatchesTask>("rebuild${capitalized}Patches") {
            group = PATCHER_TASK_GROUP
            description =
                "Diffs the '${this@configure.name}' patch workspace against the original sources and generates patches."
            originalJar.set(originalSourcesJar)
            workspaceDir.set(this@configure.workspaceDir)
            patchesDir.set(this@configure.patchesDir)
            context.set(this@configure.context)
            toolClasspath.from(diffPatchTool)
        }

        project.pluginManager.withPlugin("java") {
            configureJava(
                project = project,
                patchSet = this@configure,
                capitalized = capitalized,
                applyPatches = applyPatches,
                originalBinaryJar = originalBinaryJar,
            )
        }
    }

    private fun configureJava(
        project: Project,
        patchSet: PatchSet,
        capitalized: String,
        applyPatches: TaskProvider<ApplyPatchesTask>,
        originalBinaryJar: Provider<RegularFile>,
    ) {
        val sourceSets = project.extensions.getByType<SourceSetContainer>()

        val extractPatchedFiles =
            project.tasks.register<ExtractPatchedFilesTask>("extract${capitalized}PatchedFiles") {
                group = INTERNAL_PATCHER_TASK_GROUP
                description = "Extracts changed files for '${patchSet.name}'."
                patchedZip.set(applyPatches.flatMap { it.outputZip })
                patchesDir.set(patchSet.patchesDir)
                outputDir.set(project.layout.buildDirectory.dir("generated/sources/dependency-patcher/${patchSet.name}"))
            }

        val patchSourceSet = sourceSets.register("${patchSet.name}Patch") {
            java.srcDir(extractPatchedFiles.flatMap { it.outputDir })
            if (!patchSet.module.isPresent) {
                compileClasspath += project.files(originalBinaryJar)
            }
        }

        patchSourceSet.configure {
            project.tasks.named<JavaCompile>(compileJavaTaskName) {
                val provider = project.objects.newInstance<PatchModuleArgumentProvider>()

                provider.module.set(patchSet.module)
                provider.patchModule.set(originalBinaryJar)
                provider.joinedModule.set(patchSet.joinedModule)
                options.compilerArgumentProviders.add(provider)
            }
        }

        val patchedJar = project.tasks.register<Jar>("patched${capitalized}Jar") {
            group = INTERNAL_PATCHER_TASK_GROUP

            archiveBaseName.set(patchSet.name)
            archiveClassifier.set("patched")
            destinationDirectory.set(project.layout.buildDirectory.dir("dependency-patcher/${patchSet.name}"))
            duplicatesStrategy = DuplicatesStrategy.EXCLUDE
            from(patchSourceSet.map { it.output })
            from(project.zipTree(originalBinaryJar))
        }

        patchSet.targetConfigurations.get().forEach { config ->
            config.configure {
                dependencies.add(
                    project.dependencies.create(project.files(patchedJar)),
                )
            }
        }
    }
}
