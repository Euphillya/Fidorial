plugins {
    id("com.gradleup.shadow") version "9.5.1"
}

dependencies {
    compileOnly(project(":fidorial-api"))
    implementation(platform(libs.adventure.bom))
    implementation(libs.adventure.text.serializer.plain)
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
    rootProject.layout.projectDirectory.file("fidorial-server/run/plugins/TestPlugin.jar").asFile.delete()
    into(rootProject.layout.projectDirectory.dir("fidorial-server/run/plugins"))
}

tasks.build {
    finalizedBy(deployToRun)
}
