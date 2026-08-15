package fr.euphyllia.fidorial.gradle.patcher.extension

import fr.euphyllia.fidorial.gradle.patcher.PatchSet
import org.gradle.api.NamedDomainObjectContainer
import org.gradle.api.model.ObjectFactory
import javax.inject.Inject

abstract class DependencyPatcherExtension
    @Inject
    constructor(
        objects: ObjectFactory,
    ) {
        val patchSets: NamedDomainObjectContainer<PatchSet> =
            objects.domainObjectContainer(PatchSet::class.java) { name ->
                objects.newInstance(PatchSet::class.java, name)
            }

        fun patchSet(
            name: String,
            configure: PatchSet.() -> Unit,
        ) {
            patchSets.maybeCreate(name)
            patchSets.named(name, configure)
        }
    }
