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
}
