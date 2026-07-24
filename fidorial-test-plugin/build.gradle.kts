extra.set("moduleName", "fr.fidorial.test")

plugins {
    id("com.gradleup.shadow")
}

dependencies {
    compileOnly(libs.brigadier)
    compileOnly(projects.fidorialApi)
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
