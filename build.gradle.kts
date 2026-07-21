import fr.euphyllia.fidorial.gradle.WrapDeclarationParameters

plugins {
    id("java")
    id("com.diffplug.spotless") version "8.8.0"
}

repositories {
    mavenCentral()
}

subprojects {
    apply {
        plugin("java-library")
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
}

spotless {
    ratchetFrom("origin/master")

    format("misc") {
        target(
            "*.gradle.kts",
            "*.md",
            "*.properties",
            "*.json",
            "*.toml",
            "*.xml",
            "*.yml",
            "*.yaml",
        )
        targetExclude(
            ".gradle/**",
            ".idea/**",
            "**/build/**",
            "**/run/**",
        )
        trimTrailingWhitespace()
        endWithNewline()
    }

    java {
        target("**/*.java")

        //palantirJavaFormat("2.96.0")
        forbidWildcardImports()
        formatAnnotations()
        removeUnusedImports()
        //custom("Wrap declaration parameters", WrapDeclarationParameters())
        replaceRegex("Expand empty code blocks", """(?m)^([ \t]*)(.+) \{\}$""", "$1$2 {\n$1}")
        replaceRegex(
            "Separate module requires static",
            """(?m)^(    requires (?!static )[^\n]+;\n)(    requires static )""",
            "$1\n$2",
        )

        importOrder("", "javax|java", "\\#")
    }
}
