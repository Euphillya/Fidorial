import fr.fidorial.registrygen.task.GenerateBlockStatesTask

plugins {
    id("fidorial-server-module")
    id("fr.fidorial.registry-generator")
}

fidorialModule {
    readUnnamedModules = setOf("fr.fidorial", "fr.fidorial.world")
}

dependencies {
    implementation(projects.fidorialCore)
    implementation(projects.fidorialStorage)
    implementation(projects.fidorialRegistry)
    implementation(projects.fidorialProtocol)
}

java {
    sourceSets.main {
        java.srcDir("src/generated/java")
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

    generatedPackage.set("fr.euphyllia.fidorial.server.world")

    workingDirectory.set(rootProject.layout.buildDirectory.dir("registry-generator/working"))
    generatedSourcesDirectory.set(layout.projectDirectory.dir("src/generated/java"))

    dataGeneratorArguments.set(listOf("--reports"))

    generateRegistryKey = false
    generatePacketCatalogs = false
    generateBlockStates = true
}

tasks.withType<GenerateBlockStatesTask>().configureEach {
    blockPackage.set("fr.fidorial.world.block")
    blockTypeKeysPackage.set("fr.fidorial.registry.keys")
}
