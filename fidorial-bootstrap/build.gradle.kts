extra.set("readUnnamedModules", setOf<String>())

description = "Zero-dependency launcher: resolves Fidorial's runtime libraries, then hands over to the server."

tasks.jar {
    manifest {
        attributes(
            "Automatic-Module-Name" to "fr.fidorial.bootstrap",
        )
    }
}

tasks.withType<JavaCompile>().configureEach {
    options.release = 25
}
