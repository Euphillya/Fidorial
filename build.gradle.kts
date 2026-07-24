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

    fun readUnnamedModules(): Iterable<String> {
        val property = extensions.extraProperties.get("readUnnamedModules")
        return (property as Iterable<*>).map { it.toString() }
    }

    tasks.withType<JavaCompile>().configureEach {
        readUnnamedModules().forEach { options.compilerArgs.addAll(listOf("--add-reads", "$it=ALL-UNNAMED")) }
    }

    tasks.withType<Test>().configureEach {
        readUnnamedModules().forEach { jvmArgs("--add-reads", "$it=ALL-UNNAMED") }
    }

    tasks.withType<JavaExec>().configureEach {
        readUnnamedModules().forEach { jvmArgs("--add-reads", "$it=ALL-UNNAMED") }
    }

    tasks.javadoc {
        val options = options as StandardJavadocDocletOptions
        readUnnamedModules().forEach { options.addStringOption("-add-reads", "$it=ALL-UNNAMED") }
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
