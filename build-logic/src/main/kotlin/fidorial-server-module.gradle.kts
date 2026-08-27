plugins {
    id("fidorial-java-module")
}

dependencies {
    "api"(project(":fidorial-api"))
}

val gplLicense = rootProject.file("LICENSE-GPL-3.0.txt")

tasks.named<Jar>("jar") {
    from(gplLicense) {
        into("META-INF")
    }
}
