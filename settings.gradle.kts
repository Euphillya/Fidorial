rootProject.name = "fidorial"

pluginManagement {
    includeBuild("fidorial-registry-generator")

    repositories {
        gradlePluginPortal()
        mavenCentral()
    }
}

include("fidorial-api")
include("fidorial-auth")
include("fidorial-server")
include("fidorial-test-plugin")