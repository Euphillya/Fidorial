plugins {
    `kotlin-dsl`
}

repositories {
    gradlePluginPortal()
    mavenCentral()
}

dependencies {
    implementation(gradleApi())
    implementation(libs.spotless)
    implementation(libs.spotless.lib)
    implementation(libs.spotless.lib.extra)
}
