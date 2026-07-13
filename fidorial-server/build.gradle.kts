plugins {
    application
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
    standardInput = System.`in`
}
