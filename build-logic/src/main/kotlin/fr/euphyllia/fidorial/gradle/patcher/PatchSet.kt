package fr.euphyllia.fidorial.gradle.patcher

import io.codechicken.diffpatch.util.PatchMode
import org.gradle.api.Named
import org.gradle.api.NamedDomainObjectProvider
import org.gradle.api.artifacts.Configuration
import org.gradle.api.artifacts.MinimalExternalModuleDependency
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.provider.ListProperty
import org.gradle.api.provider.Property
import org.gradle.api.provider.SetProperty
import org.gradle.api.tasks.SourceSet
import javax.inject.Inject

abstract class PatchSet
    @Inject
    constructor(
        private val patchSetName: String,
    ) : Named {
        override fun getName(): String = patchSetName

        abstract val library: Property<MinimalExternalModuleDependency>

        abstract val classifier: Property<String>

        abstract val patchesDir: DirectoryProperty

        abstract val workspaceDir: DirectoryProperty

        abstract val patchMode: Property<PatchMode>

        abstract val minFuzz: Property<Float>

        abstract val maxOffset: Property<Int>

        abstract val context: Property<Int>

        abstract val sourceSetsToAddTo: ListProperty<SourceSet>

        abstract val addPatchedLibraryTo: SetProperty<NamedDomainObjectProvider<out Configuration>>
    }
