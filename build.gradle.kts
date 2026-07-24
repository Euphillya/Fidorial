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
        plugin("java")
        plugin("java-library")
    }

    group = "fr.fidorial"

    repositories {
        mavenCentral()
        maven("https://libraries.minecraft.net")
    }

    val modules = setOf(
        "fr.fidorial",
        "fr.fidorial.server",
        "fr.fidorial.auth",
        "fr.fidorial.test"
    )

    tasks.withType<JavaCompile>().configureEach {
        modules.forEach { options.compilerArgs.addAll(listOf("--add-reads", "$it=ALL-UNNAMED")) }
    }

    tasks.withType<Test>().configureEach {
        modules.forEach { jvmArgs("--add-reads", "$it=ALL-UNNAMED") }
    }

    tasks.withType<JavaExec>().configureEach {
        modules.forEach { jvmArgs("--add-reads", "$it=ALL-UNNAMED") }
    }

    tasks.javadoc {
        val options = options as StandardJavadocDocletOptions
        modules.forEach { options.addStringOption("-add-reads", "$it=ALL-UNNAMED") }
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
