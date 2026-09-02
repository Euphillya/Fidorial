extra.set("readUnnamedModules", setOf("fr.fidorial.test", "fr.fidorial"))

plugins {
    id("com.gradleup.shadow")
    id("fr.fidorial.plugin-libraries")
}

repositories {
    maven("https://jitpack.io/")
}

dependencies {
    compileOnly(libs.brigadier)
    compileOnly(projects.fidorialApi)

    fidorialLibrary("org.apache.commons:commons-text:1.12.0")
}

tasks.jar {
    enabled = false
}

tasks.shadowJar {
    archiveBaseName.set("TestPlugin")
    archiveClassifier.set("")
}

val deployToRun = tasks.register<Copy>("deployToRun") {
    from(tasks.shadowJar)
    rename { "TestPlugin.jar" }
    val destination = rootProject.layout.projectDirectory.dir("fidorial-server/run/plugins").asFile
    into(destination)
    doFirst {
        destination.resolve("TestPlugin.jar").delete()
    }
}
