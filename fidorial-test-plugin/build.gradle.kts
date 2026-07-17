dependencies {
    compileOnly(project(":fidorial-api"))
    compileOnly(libs.slf4j.api)
}

tasks.jar {
    archiveBaseName.set("TestPlugin")
}

val deployToRun by tasks.registering(Copy::class) {
    from(tasks.jar)
    into(rootProject.layout.projectDirectory.dir("fidorial-server/run/plugins"))
}

tasks.build {
    finalizedBy(deployToRun)
}
