plugins {
    id("fidorial-server-module")
}

fidorialModule {
    readUnnamedModules = setOf("fr.fidorial", "fr.fidorial.entity")
}

dependencies {
    implementation(projects.fidorialCodecs)
    implementation(projects.fidorialStorage)
    implementation(projects.fidorialRegistry)
    implementation(projects.fidorialProtocol)
    implementation(projects.fidorialWorld)
    implementation(libs.adventure.nbt.dfu)
}
