plugins {
    id("java")
    id("com.diffplug.spotless") version "8.8.0"
}

spotless {
    ratchetFrom("origin/master")

    format("misc") {
        target(
            "**/*.gradle.kts",
            "**/*.md",
            "**/*.properties",
            "**/*.json",
            "**/*.toml",
            "**/*.xml",
            "**/*.yml",
            "**/*.yaml",
            ".github/workflows/*.yml",
            ".github/workflows/*.yaml",
        )
        targetExclude(
            ".gradle/**",
            ".idea/**",
            "build/**",
            "**/build/**",
            "**/run/**",
        )
        trimTrailingWhitespace()
        endWithNewline()
    }
}

subprojects {
    apply {
        plugin("java-library")
        plugin("com.diffplug.spotless")
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

    extensions.configure<com.diffplug.gradle.spotless.SpotlessExtension> {
        java {
            ratchetFrom("origin/master")
            target(
                "**/*.java",
            )
            targetExclude(
                ".gradle/**",
                ".idea/**",
                "build/**",
                "run/**",
            )
            palantirJavaFormat("2.96.0")
            formatAnnotations()
        }
    }
}
