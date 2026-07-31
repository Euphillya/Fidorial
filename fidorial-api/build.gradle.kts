extra.set("readUnnamedModules", setOf("fr.fidorial"))

plugins {
    `maven-publish`
    id("fr.fidorial.registry-generator")
    id("maven-publish")
}

dependencies {
    api(libs.adventure.text.serializer.ansi)
    api(libs.adventure.text.serializer.plain)
    api(libs.brigadier)
    api(libs.bundles.adventure)
    api(libs.gson)
    api(libs.guava)
    api(libs.jspecify)
    api(libs.slf4j.api)
    api(platform(libs.adventure.bom))
    compileOnly(libs.jetbrains.annotations)
}

java {
    withJavadocJar()
    withSourcesJar()
}

publishing {
    publications {
        register<MavenPublication>("maven") {
            from(components["java"])
            pom {
                name = "fidorial-api"
                description = "Plugin API for the Fidorial Minecraft server"
                url = "https://repo.euphyllia.moe"
            }
        }
    }
    repositories {
        maven {
            name = "Euphyllia"
            val releases = uri("https://repo.euphyllia.moe/repository/maven-releases/")
            val snapshots = uri("https://repo.euphyllia.moe/repository/maven-snapshots/")
            url = if (version.toString().endsWith("SNAPSHOT")) snapshots else releases
            credentials {
                username = providers.environmentVariable("NEXUS_USERNAME").orNull ?: ""
                password = providers.environmentVariable("NEXUS_PASSWORD").orNull ?: ""
            }
        }
    }
}

fidorialRegistryGenerator {
    minecraftVersion.set("26.2")

    generatedPackage.set(
        "fr.fidorial.registry"
    )

    generatedSourcesDirectory.set(
        layout.buildDirectory.dir(
            "generated/sources/registries/java/main"
        )
    )

    dataGeneratorArguments.set(
        listOf("--reports")
    )
}