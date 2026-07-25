plugins {
    `maven-publish`
    id("fr.fidorial.registry-generator")
}

dependencies {
    api(libs.slf4j.api)
    api(platform(libs.adventure.bom))
    api(libs.bundles.adventure)
    api(libs.guava)
}

java {
    withSourcesJar()
}


publishing {
    publications {
        create<MavenPublication>("maven") {
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
                username = System.getenv("NEXUS_USERNAME") ?: ""
                password = System.getenv("NEXUS_PASSWORD") ?: ""
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

    registries.set(
        mapOf(
            "minecraft:item" to "Item",
            "minecraft:worldgen/biome" to "Biome"
        )
    )
}