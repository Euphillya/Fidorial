pluginManagement {
    includeBuild("build-logic")
    includeBuild("fidorial-registry-generator")
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}

rootProject.name = "fidorial"

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

// --- MIT LICENCE
include("fidorial-api")
include("fidorial-auth")
include("fidorial-test-plugin")

// --- GPL-3.0
include("fidorial-core")
include("fidorial-codecs")
include("fidorial-storage")
include("fidorial-registry")
include("fidorial-protocol")
include("fidorial-world")
include("fidorial-entity")
include("fidorial-command")
include("fidorial-server")
