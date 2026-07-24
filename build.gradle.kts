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

    repositories {
        mavenCentral()
        maven("https://libraries.minecraft.net")
        maven {
            name = "faststatsReleases"
            url = uri("https://repo.faststats.dev/releases")
        }
    }

    fun property(name: String): String {
        val property = extensions.extraProperties.get(name)
        if (property != null) return property.toString()
        throw Error("Property '$name' not found in project ${project.path}")
    }

    tasks.withType<JavaCompile>().configureEach {
        property("moduleName").let { moduleName ->
            options.compilerArgs.addAll(listOf("--add-reads", "$moduleName=ALL-UNNAMED"))
        }
    }

    tasks.withType<Test>().configureEach {
        property("moduleName").let { moduleName ->
            jvmArgs("--add-reads", "$moduleName=ALL-UNNAMED")
        }
    }

    tasks.withType<JavaExec>().configureEach {
        property("moduleName").let { moduleName ->
            jvmArgs("--add-reads", "$moduleName=ALL-UNNAMED")
        }
    }

    tasks.javadoc {
        val options = options as StandardJavadocDocletOptions
        property("moduleName").let { moduleName ->
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
