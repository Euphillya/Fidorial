extra.set("readUnnamedModules", setOf("fr.fidorial"))

plugins {
    `maven-publish`
    id("fr.fidorial.registry-generator")
}

dependencies {
    api(libs.adventure.text.serializer.ansi)
    api(libs.adventure.text.serializer.plain)
    api(libs.brigadier)
    api(libs.bundles.adventure)
    api(libs.gson)
    api(libs.guava)
    api(libs.jspecify)
    api(libs.slf4j.api)
    api(platform(libs.adventure.bom))
    api(libs.fastutil)
    compileOnly(libs.jetbrains.annotations)
}

java {
    withJavadocJar()
    withSourcesJar()
    sourceSets.main {
        java.srcDir("src/generated/java")
        resources.srcDir("src/generated/resources")
    }
}

tasks.javadoc {
    val opt = options as StandardJavadocDocletOptions

    opt.encoding = "UTF-8"
    opt.docEncoding = "UTF-8"
    opt.charSet = "UTF-8"
    opt.docTitle = "Fidorial API ${project.version}"
    opt.windowTitle = "fidorial-api ${project.version}"
    opt.addBooleanOption("html5", true)
    opt.noTimestamp(true)
    opt.addStringOption("Xdoclint:all,-missing", "-quiet")
    opt.tags("apiNote:a:API Note:", "sinceMinecraft:a:Since Minecraft:")

    opt.links(
        "https://docs.oracle.com/en/java/javase/25/docs/api/",
        "https://jd.papermc.io/adventure/5.2.0/",
        "https://www.slf4j.org/apidocs/",
        "https://jspecify.dev/docs/api/",
    )

    opt.bottom(
        "MIT © 2026 Euphyllia Bierque — " +
                "<a href=\"https://github.com/Euphillya/Fidorial\">GitHub</a>"
    )
}

publishing {
    publications {
        register<MavenPublication>("maven") {
            from(components["java"])
            pom {
                name = "fidorial-api"
                description = "Plugin API for the Fidorial Minecraft server"
                url = "https://repo.euphyllia.moe"
            }
        }
    }
    repositories {
        maven {
            name = "Euphyllia"
            val releases = uri("https://repo.euphyllia.moe/repository/maven-releases/")
            val snapshots = uri("https://repo.euphyllia.moe/repository/maven-snapshots/")
            url = if (version.toString().endsWith("SNAPSHOT")) snapshots else releases
            credentials {
                username = providers.environmentVariable("NEXUS_USERNAME").orNull ?: ""
                password = providers.environmentVariable("NEXUS_PASSWORD").orNull ?: ""
            }
        }
    }
}

fidorialRegistryGenerator {
    minecraftVersion.set("26.2")

    generatedSourcesDirectory.set(
        layout.projectDirectory.dir(
            "src/generated/java"
        )
    )

    dataGeneratorArguments.set(
        listOf("--reports", "--server")
    )

    registries.set(
        mapOf(
            "minecraft:attribute" to "Attribute",
            "minecraft:banner_pattern" to "BannerPattern",
            "minecraft:worldgen/biome" to "Biome",
            "minecraft:block" to "BlockType",
            "minecraft:cat_sound_variant" to "CatSoundVariant",
            "minecraft:cat_variant" to "CatVariant",
            "minecraft:chat_type" to "ChatType",
            "minecraft:chicken_sound_variant" to "ChickenSoundVariant",
            "minecraft:chicken_variant" to "ChickenVariant",
            "minecraft:cow_sound_variant" to "CowSoundVariant",
            "minecraft:cow_variant" to "CowVariant",
            "minecraft:damage_type" to "DamageType",
            "minecraft:data_component_type" to "DataComponentType",
            "minecraft:dialog" to "Dialog",
            "minecraft:dimension_type" to "DimensionType",
            "minecraft:enchantment" to "Enchantment",
            "minecraft:frog_variant" to "FrogVariant",
            "minecraft:game_event" to "GameEvent",
            "minecraft:game_rule" to "GameRule",
            "minecraft:instrument" to "Instrument",
            "minecraft:item" to "Item",
            "minecraft:jukebox_song" to "JukeboxSong",
            "minecraft:map_decoration_type" to "MapDecorationType",
            "minecraft:menu" to "MenuType",
            "minecraft:mob_effect" to "MobEffect",
            "minecraft:painting_variant" to "PaintingVariant",
            "minecraft:pig_sound_variant" to "PigSoundVariant",
            "minecraft:pig_variant" to "PigVariant",
            "minecraft:sound_event" to "SoundEvent",
            "minecraft:timeline" to "Timeline",
            "minecraft:trim_material" to "TrimMaterial",
            "minecraft:trim_pattern" to "TrimPattern",
            "minecraft:villager_profession" to "VillagerProfession",
            "minecraft:villager_type" to "VillagerType",
            "minecraft:wolf_sound_variant" to "WolfSoundVariant",
            "minecraft:wolf_variant" to "WolfVariant",
            "minecraft:world_clock" to "WorldClock",
            "minecraft:zombie_nautilus_variant" to "ZombieNautilusVariant"
        )
    )
}
