plugins {
    id("java")
    id("fidorial-spotless")
    alias(libs.plugins.shadow) apply false
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
        maven("https://libraries.minecraft.net")
        maven {
            name = "faststatsReleases"
            url = uri("https://repo.faststats.dev/releases")
        }
    }

    fun ownProperty(name: String): String? {
        return if (extensions.extraProperties.has(name)) extensions.extraProperties.get(name).toString() else null
    }

    tasks.withType<JavaCompile>().configureEach {
        ownProperty("moduleName")?.let { moduleName ->
            options.compilerArgs.addAll(listOf("--add-reads", "$moduleName=ALL-UNNAMED"))
        }
    }

    tasks.withType<Test>().configureEach {
        ownProperty("moduleName")?.let { moduleName ->
            jvmArgs("--add-reads", "$moduleName=ALL-UNNAMED")
        }
    }

    tasks.withType<JavaExec>().configureEach {
        ownProperty("moduleName")?.let { moduleName ->
            jvmArgs("--add-reads", "$moduleName=ALL-UNNAMED")
        }
    }

    tasks.javadoc {
        val options = options as StandardJavadocDocletOptions
        ownProperty("moduleName")?.let { moduleName ->
            options.addStringOption("-add-reads", "$moduleName=ALL-UNNAMED")
        }
    }

    extensions.configure<JavaPluginExtension> {
        toolchain {
            languageVersion.set(JavaLanguageVersion.of(25))
        }
    }

    tasks.withType<JavaCompile>().configureEach {
        options.encoding = Charsets.UTF_8.name()
        options.release = 25
    }
}
