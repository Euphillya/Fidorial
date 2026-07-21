plugins {
    `maven-publish`
}

dependencies {
    api(libs.slf4j.api)
    api(platform(libs.adventure.bom))
    api(libs.bundles.adventure)
    api(libs.guava)
    api(libs.jspecify)
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
