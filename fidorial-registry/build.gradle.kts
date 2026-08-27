plugins {
    id("fidorial-server-module")
    id("fr.fidorial.registry-generator")
}

fidorialModule {
    readUnnamedModules = setOf("fr.fidorial", "fr.fidorial.registry")
}

dependencies {
    implementation(projects.fidorialCodecs)
}

java {
    sourceSets.main {
        java.srcDir("src/generated/java")
        resources.srcDir("src/generated/resources")
    }
}

spotless {
    java {
        targetExclude("src/generated/**")
    }
}

fidorialRegistryGenerator {
    minecraftVersion.set("26.2")
    prismarineMinecraftData.set("26.1")

    generatedPackage.set("fr.euphyllia.fidorial.server.registry")
    registryDataPackage.set("fr.euphyllia.fidorial.server.registry.data")
    registryKeysPackage.set("fr.euphyllia.fidorial.server.registry.keys")

    workingDirectory.set(rootProject.layout.buildDirectory.dir("registry-generator/working"))
    generatedSourcesDirectory.set(layout.projectDirectory.dir("src/generated/java"))

    dataGeneratorArguments.set(listOf("--reports"))

    registries.set(
        mapOf(
            "minecraft:command_argument_type" to "ArgumentType",
            "minecraft:block_entity_type" to "BlockEntityType",
            "minecraft:entity_type" to "EntityTypes",
        ),
    )

    generateRegistryKey = false
    generatePacketCatalogs = false
    generateBlockStates = false
}
