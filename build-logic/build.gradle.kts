plugins {
    `kotlin-dsl`
    alias(libs.plugins.blossom)
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

sourceSets.all {
    blossom.kotlinSources {
        properties.put("diffpatch_version", libs.versions.diffpatch)
    }
}

gradlePlugin {
    plugins {
        register("dependencyPatcher") {
            id = "fr.fidorial.dependency-patcher"
            implementationClass = "fr.euphyllia.fidorial.gradle.patcher.DependencyPatcherPlugin"
        }
    }
}
