plugins {
    id("fidorial-server-module")
}

fidorialModule {
    readUnnamedModules = setOf("fr.fidorial", "fr.fidorial.codecs")
}

dependencies {
    api(libs.adventure.nbt.dfu)
    implementation(libs.dfu)
}
