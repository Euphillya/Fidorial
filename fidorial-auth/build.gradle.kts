plugins {
    id("fidorial-java-module")
}

fidorialModule {
    readUnnamedModules = setOf("fr.fidorial.auth")
    license = "MIT"
}

dependencies {
    api(libs.gson)
    implementation(libs.slf4j.api)
    implementation(libs.jspecify)
}
