plugins {
    `maven-publish`
}

dependencies {
    api(libs.slf4j.api)
    api(platform(libs.adventure.bom))
    api(libs.bundles.adventure)
    api(libs.guava)
    api(libs.brigadier)
    api(projects.fidorialBrigadier) // for module support
    api(libs.jspecify)
    compileOnly("org.jetbrains:annotations:26.0.2")
}

java {
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
                username = providers.systemProperty("NEXUS_USERNAME").orNull ?: ""
                password = providers.systemProperty("NEXUS_PASSWORD").orNull ?: ""
            }
        }
    }
}
