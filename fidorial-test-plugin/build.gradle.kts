plugins {
    id("fidorial-java-module")
    id("com.gradleup.shadow")
}

fidorialModule {
    readUnnamedModules = setOf("fr.fidorial.test", "fr.fidorial")
    license = "MIT"
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

val deployToRun =
    tasks.register<Copy>("deployToRun") {
        from(tasks.shadowJar)
        rename { "TestPlugin.jar" }
        val destination =
            rootProject.layout.projectDirectory
                .dir("fidorial-server/run/plugins")
                .asFile
        into(destination)
        doFirst {
            destination.resolve("TestPlugin.jar").delete()
        }
    }
