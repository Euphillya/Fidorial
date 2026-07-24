extra.set("readUnnamedModules", setOf("fr.fidorial"))

plugins {
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
