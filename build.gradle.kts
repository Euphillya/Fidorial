plugins {
    id("java")
    id("fidorial-spotless")
    alias(libs.plugins.shadow) apply false
}

repositories {
    mavenCentral()
}

tasks.register("checkLayers") {
    group = "verification"
    description = "Verify that no module depends on a higher-level layer."
    dependsOn(
        subprojects
            .filter { it.name.startsWith("fidorial-") }
            .map { "${it.path}:compileJava" },
    )
}
