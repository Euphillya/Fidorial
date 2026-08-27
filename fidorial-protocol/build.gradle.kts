plugins {
    id("fidorial-server-module")
    id("fr.fidorial.registry-generator")
}

fidorialModule {
    readUnnamedModules = setOf("fr.fidorial", "fr.fidorial.protocol")
}

dependencies {
    api(libs.netty.all)
    implementation(projects.fidorialCodecs)
    implementation(projects.fidorialRegistry)
    implementation(libs.adventure.nbt.dfu)
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

    generatedPackage.set("fr.euphyllia.fidorial.server.network.protocol")

    workingDirectory.set(rootProject.layout.buildDirectory.dir("registry-generator/working"))
    generatedSourcesDirectory.set(layout.projectDirectory.dir("src/generated/java"))

    dataGeneratorArguments.set(listOf("--reports"))

    generateRegistryKey = false
    generatePacketCatalogs = true
    generateBlockStates = false
}
