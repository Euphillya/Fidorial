plugins {
    id("fidorial-build-conventions")
}

fidorialBuild {
    readUnnamedModules = setOf("fr.fidorial.auth")
}

dependencies {
    api(libs.gson)
    implementation(libs.slf4j.api)
    implementation(libs.jspecify)
}
