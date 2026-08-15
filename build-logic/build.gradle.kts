plugins {
    `kotlin-dsl`
}

repositories {
    gradlePluginPortal()
    mavenCentral()
}

kotlin {
    jvmToolchain(17)
}

dependencies {
    implementation(gradleApi())
    implementation(libs.spotless)
    implementation(libs.spotless.lib)
    implementation(libs.spotless.lib.extra)
    implementation(libs.diffpatch)
}

gradlePlugin {
    plugins {
        register("dependencyPatcher") {
            id = "fr.fidorial.dependency-patcher"
            implementationClass = "fr.euphyllia.fidorial.gradle.patcher.DependencyPatcherPlugin"
        }
    }
}
