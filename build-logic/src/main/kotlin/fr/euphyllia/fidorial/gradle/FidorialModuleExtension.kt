package fr.euphyllia.fidorial.gradle

import org.gradle.api.provider.Property
import org.gradle.api.provider.SetProperty

interface FidorialModuleExtension {

    val readUnnamedModules: SetProperty<String>

    val license: Property<String>
}
