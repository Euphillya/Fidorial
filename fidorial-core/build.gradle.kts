plugins {
    id("fidorial-server-module")
}

fidorialModule {
    readUnnamedModules = setOf("fr.fidorial", "fr.fidorial.core")
}
