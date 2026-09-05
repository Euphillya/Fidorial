plugins {
    id("java")
    id("fidorial-spotless")
    alias(libs.plugins.shadow) apply false
}

repositories {
    mavenCentral()
}
