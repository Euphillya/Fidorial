plugins {
    application
    id("com.gradleup.shadow") version "9.5.1"
}

dependencies {
    implementation(project(":fidorial-api"))
    implementation(project(":fidorial-auth"))
    implementation(libs.netty.all)
    implementation(libs.gson)
    implementation(libs.slf4j.api)
    implementation(libs.faststats.config)
    implementation(libs.faststats.core)
    runtimeOnly(libs.logback.classic)
}

application {
    mainClass.set("fr.euphyllia.fidorial.server.Main")
}

tasks.named<JavaExec>("run") {
    description = "Spin up a test server without assembling a jar"
    standardInput = System.`in`
    classpath(sourceSets.main.map { it.runtimeClasspath })
    workingDir = project.file("run")
    workingDir.mkdirs()
}

tasks.named("run") {
    dependsOn(":fidorial-test-plugin:build")
}

tasks.shadowJar {
    archiveBaseName.set("Fidorial")
    archiveClassifier.set("")
    mergeServiceFiles()
}