import fr.euphyllia.fidorial.gradle.FidorialModuleExtension

plugins {
    id("java-library")
    id("fidorial-spotless")
}

group = "fr.fidorial"

val fidorialModule = extensions.create<FidorialModuleExtension>("fidorialModule")

fidorialModule.license.convention("GPL-3.0-only")

repositories {
    mavenCentral()
    maven("https://libraries.minecraft.net")
    maven("https://repo.papermc.io/repository/maven-public/")
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(25)
    }
}

val addReadsArgs: Provider<List<String>> =
    fidorialModule.readUnnamedModules.map { modules ->
        modules.sorted().flatMap { listOf("--add-reads", "$it=ALL-UNNAMED") }
    }

tasks.withType<JavaCompile>().configureEach {
    options.encoding = Charsets.UTF_8.name()
    options.release = 25
    options.compilerArgs.addAll(addReadsArgs.get())
}

tasks.withType<Test>().configureEach {
    jvmArgs(addReadsArgs.get())
}

tasks.withType<JavaExec>().configureEach {
    jvmArgs(addReadsArgs.get())
}

tasks.withType<Javadoc>().configureEach {
    val opt = options as StandardJavadocDocletOptions
    fidorialModule.readUnnamedModules.get().sorted().forEach {
        opt.addStringOption("-add-reads", "$it=ALL-UNNAMED")
    }
}

tasks.withType<Jar>().configureEach {
    isReproducibleFileOrder = true
    isPreserveFileTimestamps = false
}