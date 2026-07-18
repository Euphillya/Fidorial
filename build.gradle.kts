plugins {
    java
}

subprojects {
    apply {
        plugin("java-library")
        plugin("checkstyle")
    }

    group = "fr.euphyllia.fidorial"
    version = "0.1.0-SNAPSHOT"

    repositories {
        mavenCentral()
        maven {
            name = "faststatsReleases"
            url = uri("https://repo.faststats.dev/releases")
        }
    }

    extensions.configure<JavaPluginExtension> {
        toolchain {
            languageVersion.set(JavaLanguageVersion.of(25))
        }
    }

    tasks.withType<JavaCompile>().configureEach {
        options.encoding = "UTF-8"
        options.release.set(25)
    }

    extensions.configure<CheckstyleExtension>("checkstyle") {
        toolVersion = "13.8.0"
        configDirectory.set(rootProject.layout.projectDirectory.dir("config"))
        maxWarnings = 0
    }

    tasks.withType<Checkstyle>().configureEach {
        reports {
            xml.required.set(false)
            html.required.set(true)
        }
    }
}
