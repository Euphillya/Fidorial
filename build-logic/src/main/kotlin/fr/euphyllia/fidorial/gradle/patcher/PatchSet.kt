package fr.euphyllia.fidorial.gradle.patcher

import io.codechicken.diffpatch.util.PatchMode
import org.gradle.api.Named
import org.gradle.api.NamedDomainObjectProvider
import org.gradle.api.Project
import org.gradle.api.artifacts.Configuration
import org.gradle.api.artifacts.MinimalExternalModuleDependency
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.file.ProjectLayout
import org.gradle.api.model.ObjectFactory
import org.gradle.api.plugins.JavaPlugin
import org.gradle.api.provider.ListProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.SourceSet.MAIN_SOURCE_SET_NAME
import org.gradle.kotlin.dsl.property
import javax.inject.Inject
import org.gradle.kotlin.dsl.*

abstract class PatchSet @Inject constructor(
    private val patchSetName: String,
    objects: ObjectFactory,
    projectLayout: ProjectLayout,
    project: Project
) : Named {
    override fun getName(): String = patchSetName

    abstract val library: Property<MinimalExternalModuleDependency>

    abstract val module: Property<String>

    val classifier: Property<String> = objects.property<String>().convention("sources")

    val patchesDir: DirectoryProperty = objects.directoryProperty().convention(projectLayout.projectDirectory.dir("patches/$patchSetName"))

    val workspaceDir: DirectoryProperty = objects.directoryProperty().convention(projectLayout.buildDirectory.dir("workspace/$patchSetName"))

    val patchMode: Property<PatchMode> = objects.property<PatchMode>().convention(PatchMode.OFFSET)

    val minFuzz: Property<Float> = objects.property<Float>().convention(0.5f)

    val maxOffset: Property<Int> = objects.property<Int>().convention(100)

    val context: Property<Int> = objects.property<Int>().convention(3)

    val targetConfigurations = objects.setProperty<NamedDomainObjectProvider<out Configuration>>().convention(
        setOf(
            project.configurations.named(JavaPlugin.IMPLEMENTATION_CONFIGURATION_NAME),
            project.configurations.named(JavaPlugin.TEST_IMPLEMENTATION_CONFIGURATION_NAME)
        )
    )
}
