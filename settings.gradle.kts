pluginManagement {
    includeBuild("build-logic")
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}

rootProject.name = "fidorial"

include("fidorial-api")
include("fidorial-auth")
include("fidorial-server")
include("fidorial-test-plugin")
include("fidorial-brigadier")
