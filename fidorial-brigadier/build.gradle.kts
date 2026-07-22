plugins {
    `java-library`
    `maven-publish`
}

val embedded = configurations.register("embedded")

dependencies {
    embedded(libs.brigadier)
}

tasks.jar {
    from(embedded.map { files ->
        files.map(::zipTree)
    })

    manifest {
        attributes(
            "Automatic-Module-Name" to "com.mojang.brigadier"
        )
    }
}
