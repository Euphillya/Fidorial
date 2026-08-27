plugins {
    id("fidorial-server-module")
    id("fr.fidorial.dependency-patcher")
}

fidorialModule {
    readUnnamedModules = setOf("fr.fidorial", "fr.fidorial.command")
}

dependencies {
    implementation(projects.fidorialRegistry)
    implementation(projects.fidorialProtocol)
    implementation(projects.fidorialWorld)
    implementation(projects.fidorialEntity)
}

dependencyPatcher {
    patchSet("brigadier") {
        library.set(libs.brigadier)
        autoRebuild = true
    }
}
