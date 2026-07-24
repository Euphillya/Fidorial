extra.set("readUnnamedModules", setOf("fr.fidorial", "fr.fidorial.server"))

plugins {
    application
    id("com.gradleup.shadow")
}

repositories {
    maven("https://repo.faststats.dev/releases")
}

dependencies {
    implementation(libs.faststats.config)
    implementation(libs.faststats.core)
    implementation(libs.jline.ffm)
    implementation(libs.jline.reader)
    implementation(libs.logback.classic)
    implementation(libs.netty.all)
    implementation(projects.fidorialApi)
    implementation(projects.fidorialAuth)
    runtimeOnly(libs.netty.epoll)
    runtimeOnly(libs.netty.iouring)
    runtimeOnly(libs.netty.kqueue)
}

application {
    mainClass.set("fr.euphyllia.fidorial.server.Main")
}

tasks.run {
    description = "Spin up a test server without assembling a jar"
    standardInput = System.`in`
    classpath(sourceSets.main.map { it.runtimeClasspath })
    workingDir = project.file("run")
    workingDir.mkdirs()
    jvmArgs = listOf("--enable-native-access=ALL-UNNAMED")
    dependsOn(":fidorial-test-plugin:build")
}

tasks.shadowJar {
    archiveBaseName.set("Fidorial")
    archiveClassifier.set("")
    mergeServiceFiles()
    filesMatching("META-INF/services/**") {
        duplicatesStrategy = DuplicatesStrategy.INCLUDE
    }
}
