plugins {
    application
    id("com.gradleup.shadow")
}

dependencies {
    implementation(projects.fidorialApi)
    implementation(projects.fidorialAuth)
    implementation(libs.netty.all)
    implementation(libs.gson)
    implementation(libs.slf4j.api)
    implementation(libs.faststats.config)
    implementation(libs.faststats.core)
    implementation(libs.adventure.text.serializer.ansi)
    implementation(libs.logback.classic)
    implementation(libs.adventure.text.serializer.plain)
    implementation(libs.jline.ffm)
    implementation(libs.jline.reader)
    runtimeOnly(libs.netty.epoll)
    runtimeOnly(libs.netty.iouring)
    runtimeOnly(libs.netty.kqueue)
    testImplementation(libs.junit.jupiter)
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
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

tasks.test {
    useJUnitPlatform()
}

tasks.shadowJar {
    archiveBaseName.set("Fidorial")
    archiveClassifier.set("")
    mergeServiceFiles()
    filesMatching("META-INF/services/**") {
        duplicatesStrategy = DuplicatesStrategy.INCLUDE
    }
}
