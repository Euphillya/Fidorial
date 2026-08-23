import fr.euphyllia.fidorial.gradle.patcher.task.RebuildPatchesTask

extra.set("readUnnamedModules", setOf("fr.fidorial", "fr.fidorial.server"))

plugins {
    application
    id("fr.fidorial.dependency-patcher")
    id("fr.fidorial.registry-generator")
    id("com.gradleup.shadow")
}

repositories {
    maven("https://repo.faststats.dev/releases")
    maven("https://repo.lucko.me/")
}

dependencies {
    implementation(libs.faststats.config)
    implementation(libs.faststats.core)
    implementation(libs.jline.ffm)
    implementation(libs.jline.reader)
    implementation(libs.logback.classic)
    implementation(libs.netty.all)
    implementation(libs.classgraph)
    implementation(projects.fidorialApi)
    implementation(projects.fidorialAuth)
    implementation(libs.dfu)
    implementation(libs.adventure.nbt.dfu)
    implementation(libs.spark.common) {
        exclude(group = "net.kyori", module = "adventure-api")
        exclude(group = "net.kyori", module = "adventure-key")
        exclude(group = "net.kyori", module = "adventure-text-serializer-gson")
        exclude(group = "net.kyori", module = "adventure-text-serializer-legacy")
        exclude(group = "com.google.code.gson", module = "gson")
        exclude(group = "com.google.guava", module = "guava")
        exclude(group = "org.jspecify", module = "jspecify")
    }
    implementation(libs.spark.api)
    implementation(libs.leafpile)

    runtimeOnly(libs.netty.epoll)
    runtimeOnly(libs.netty.iouring)
    runtimeOnly(libs.netty.kqueue)
}

application {
    mainClass.set("fr.euphyllia.fidorial.server.Main")
}

java {
    sourceSets.main {
        java.srcDir("src/generated/java")
        resources.srcDir("src/generated/resources")
    }
}

tasks.run {
    description = "Spin up a test server without assembling a jar"
    standardInput = System.`in`
    classpath(sourceSets.main.map { it.runtimeClasspath })
    workingDir = project.file("run")
    jvmArgs = listOf("--enable-native-access=ALL-UNNAMED")
    dependsOn(":fidorial-test-plugin:deployToRun")
    doFirst {
        workingDir.mkdirs()
    }
}

tasks.register<JavaExec>("testScenarios") {
    description = "Run scenario tests against a real server"
    group = "verification"

    val pluginsDir = layout.projectDirectory.dir("run/plugins").asFile

    standardInput = System.`in`
    classpath(sourceSets.main.map { it.runtimeClasspath })
    workingDir = layout.projectDirectory.file("build/tmp/scenario-tests").asFile
    jvmArgs = listOf("--enable-native-access=ALL-UNNAMED")
    mainClass = "fr.euphyllia.fidorial.server.testing.ScenarioTestMain"
    args = listOf("fr.euphyllia.fidorial.server.tests", "fr.euphyllia.fidorial.testplugin.tests")
    dependsOn(":fidorial-test-plugin:deployToRun")
    doFirst {
        workingDir.deleteRecursively()
        workingDir.mkdirs()
        pluginsDir.resolve("TestPlugin.jar").copyTo(workingDir.resolve("plugins/TestPlugin.jar"))
    }
}

tasks.test {
    dependsOn("testScenarios")
}

tasks.shadowJar {
    archiveBaseName.set("Fidorial")
    archiveClassifier.set("")
    mergeServiceFiles()
    filesMatching("META-INF/services/**") {
        duplicatesStrategy = DuplicatesStrategy.INCLUDE
    }

    relocate("net.kyori.adventure.text.feature.pagination", "me.lucko.spark.lib.adventure.pagination")
    relocate("net.bytebuddy", "me.lucko.spark.lib.bytebuddy")
    relocate("com.google.protobuf", "me.lucko.spark.lib.protobuf")
    relocate("org.objectweb.asm", "me.lucko.spark.lib.asm")
    relocate("one.profiler", "me.lucko.spark.lib.asyncprofiler")
    relocate("me.lucko.bytesocks.client", "me.lucko.spark.lib.bytesocks")
    relocate("org.java_websocket", "me.lucko.spark.lib.bytesocks.ws")

    exclude("linux-arm64/**")
    exclude("linux-x64/**")
    exclude("macos/**")
    exclude("**/*.proto")
    exclude("**/*.proto.bin")
    exclude("META-INF/proguard/**")
}

fidorialRegistryGenerator {
    minecraftVersion.set("26.2")
    prismarineMinecraftData.set("26.1")

    generatedPackage.set(
        "fr.euphyllia.fidorial.server.registry"
    )

    generatedSourcesDirectory.set(
        layout.projectDirectory.dir(
            "src/generated/java"
        )
    )

    dataGeneratorArguments.set(
        listOf("--reports")
    )

    registries.set(
        mapOf(
            "minecraft:command_argument_type" to "ArgumentType",
            "minecraft:block_entity_type" to "BlockEntityType"
        )
    )

    generateRegistryKey = false
    generatePacketCatalogs = true
    generateBlockStates = true
}

dependencyPatcher {
    patchSet("brigadier") {
        library.set(libs.brigadier)
        autoRebuild = true
    }
}
