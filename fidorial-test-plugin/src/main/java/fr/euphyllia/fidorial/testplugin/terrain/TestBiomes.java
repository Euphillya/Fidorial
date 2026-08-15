package fr.euphyllia.fidorial.testplugin.terrain;

import fr.fidorial.world.biome.BiomeDefinition;
import fr.fidorial.world.biome.BiomeRegistry;
import fr.fidorial.world.biome.GrassColorModifier;
import fr.fidorial.world.biome.TemperatureModifier;
import fr.fidorial.world.environment.AdditionsSound;
import fr.fidorial.world.environment.AmbientParticle;
import fr.fidorial.world.environment.AmbientSounds;
import fr.fidorial.world.environment.BackgroundMusic;
import fr.fidorial.world.environment.Modifier;
import fr.fidorial.world.environment.MoodSound;
import fr.fidorial.world.environment.MusicTrack;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.logger.slf4j.ComponentLogger;

import java.util.List;

public final class TestBiomes {

    public static final BiomeDefinition VOLCANIC_PLAINS = BiomeDefinition
            .builder(Key.key("fidorial", "volcanic_plains"))
            .temperature(2.0F)
            .downfall(0.0F)
            .hasPrecipitation(false)
            .effects(effects -> effects
                    .waterColor(0x8C2E00)
                    .grassColor(0x6B4423)
                    .foliageColor(0x5A3A1E))
            .skyColor(0x992200)
            .fogColor(0x3F1A0A)
            .waterFogColor(0x3A0F00)
            .addAmbientParticle(AmbientParticle.of(Key.key("ash"), 0.02F))
            .ambientSounds(new AmbientSounds(
                    Key.key("ambient.nether_wastes.loop"),
                    MoodSound.CAVE,
                    AdditionsSound.of(Key.key("ambient.nether_wastes.additions"), 0.0111F)))
            .attributes(attributes -> attributes
                    .increasedFireBurnout(false)
                    .snowGolemMelts(true)
                    .waterEvaporates(true))
            .build();

    public static final BiomeDefinition FROZEN_PEAKS = BiomeDefinition
            .builder(Key.key("fidorial", "frozen_peaks"))
            .temperature(-0.7F)
            .temperatureModifier(TemperatureModifier.FROZEN)
            .downfall(0.9F)
            .hasPrecipitation(true)
            .effects(effects -> effects
                    .waterColor(0x3938C9)
                    .grassColor(0x80B497))
            .vanillaSkyColor()
            .fogColor(0xC0D8FF)
            .backgroundMusic(BackgroundMusic.of(
                    MusicTrack.of(Key.key("music.overworld.frozen_peaks"), 12000, 24000)))
            .build();

    public static final BiomeDefinition TOXIC_RIVER = BiomeDefinition
            .builder(Key.key("fidorial", "toxic_river"))
            .temperature(0.8F)
            .downfall(0.9F)
            .effects(effects -> effects
                    .waterColor(0x4C6314)
                    .grassColor(0x6A7039)
                    .foliageColor(0x6A7039)
                    .grassColorModifier(GrassColorModifier.SWAMP))
            .skyColor(0x78A7FF)
            .waterFogColor(0x232317)
            .attributes(attributes -> attributes
                    .waterFogEndDistance(0.35F, Modifier.MULTIPLY)
                    .increasedFireBurnout(true))
            .build();

    public static final BiomeDefinition CRYSTAL_SHORE = BiomeDefinition
            .builder(Key.key("fidorial", "crystal_shore"))
            .temperature(0.5F)
            .downfall(0.4F)
            .effects(effects -> effects.waterColor(0x5DB7EF))
            .skyColor(0x5DB7EF)
            .build();

    private static final List<BiomeDefinition> ALL =
            List.of(VOLCANIC_PLAINS, FROZEN_PEAKS, TOXIC_RIVER, CRYSTAL_SHORE);

    private TestBiomes() {
        throw new UnsupportedOperationException("TestBiomes cannot be instantiated.");
    }

    public static List<BiomeDefinition> all() {
        return ALL;
    }

    public static void registerAll(final BiomeRegistry biomes, final ComponentLogger logger) {
        for (final BiomeDefinition biome : ALL) {
            biomes.overwrite(biome);
            logger.info("[TestPlugin] biome {} registered (network id {})",
                    biome.key().asString(), biomes.networkId(biome.key()));
        }

        logger.info("[TestPlugin] {} biomes registered, {} of them defined by the server",
                biomes.totalRegistered(), biomes.definitions().size());
    }

    public static void unregisterAll(final BiomeRegistry biomes) {
        for (final BiomeDefinition biome : ALL) {
            biomes.unregister(biome.key());
        }
    }
}
