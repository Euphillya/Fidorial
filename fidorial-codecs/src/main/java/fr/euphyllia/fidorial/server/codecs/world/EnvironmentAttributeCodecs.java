package fr.euphyllia.fidorial.server.codecs.world;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import fr.euphyllia.fidorial.server.codecs.CommonCodecs;
import fr.euphyllia.fidorial.server.codecs.adventure.ComponentCodecs;
import fr.fidorial.world.environment.AdditionsSound;
import fr.fidorial.world.environment.AmbientParticle;
import fr.fidorial.world.environment.AmbientSounds;
import fr.fidorial.world.environment.Attribute;
import fr.fidorial.world.environment.BackgroundMusic;
import fr.fidorial.world.environment.BedRule;
import fr.fidorial.world.environment.EnvironmentAttributes;
import fr.fidorial.world.environment.Modifier;
import fr.fidorial.world.environment.MoodSound;
import fr.fidorial.world.environment.MusicTrack;
import net.kyori.adventure.key.Key;
import org.jspecify.annotations.Nullable;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class EnvironmentAttributeCodecs {

    public static final Codec<Integer> RGB_COLOR = Codec.either(Codec.STRING, Codec.INT)
            .comapFlatMap(
                    either -> either.map(EnvironmentAttributeCodecs::parseHexColor, DataResult::success),
                    color -> Either.left(formatHexColor(color)));

    public static final Codec<Integer> ARGB_COLOR = Codec.either(Codec.STRING, Codec.INT)
            .comapFlatMap(
                    either -> either.map(EnvironmentAttributeCodecs::parseHexColorARGB, DataResult::success),
                    color -> Either.left(formatHexColorARGB(color)));

    public static final Codec<Modifier> MODIFIER = Codec.STRING.comapFlatMap(
            name -> Arrays.stream(Modifier.values())
                    .filter(modifier -> modifier.id().equals(name))
                    .findFirst()
                    .map(DataResult::success)
                    .orElseGet(() -> DataResult.error(() -> "Unknown attribute modifier: " + name)),
            Modifier::id);

    public static final Codec<BedRule.AccessCondition> SLEEP_ACCESS = Codec.STRING.comapFlatMap(
            id -> BedRule.AccessCondition.fromId(id)
                    .map(DataResult::success)
                    .orElseGet(() -> DataResult.error(() -> "Unknown bed rule value: " + id)),
            BedRule.AccessCondition::id);

    public static final Codec<BedRule> BED_RULE = RecordCodecBuilder.create(instance -> instance.group(
            SLEEP_ACCESS.fieldOf("can_sleep").forGetter(BedRule::sleepAllowed),
            SLEEP_ACCESS.fieldOf("can_set_spawn").forGetter(BedRule::spawnAllowed),
            Codec.BOOL.optionalFieldOf("explodes", false).forGetter(BedRule::explodes),
            ComponentCodecs.COMPONENT_CODEC.optionalFieldOf("error_message").forGetter(BedRule::failureMessage)
    ).apply(instance, BedRule::new));

    public static final Codec<AmbientParticle> AMBIENT_PARTICLE = RecordCodecBuilder.create(instance -> instance.group(
            BiomeCodecs.PARTICLE_OPTIONS.fieldOf("particle").forGetter(AmbientParticle::type),
            Codec.FLOAT.fieldOf("probability").forGetter(AmbientParticle::probability)
    ).apply(instance, AmbientParticle::new));

    public static final Codec<MoodSound> MOOD_SOUND = RecordCodecBuilder.create(instance -> instance.group(
            CommonCodecs.KEY_CODEC.fieldOf("sound").forGetter(MoodSound::sound),
            Codec.INT.fieldOf("tick_delay").forGetter(MoodSound::tickDelay),
            Codec.INT.fieldOf("block_search_extent").forGetter(MoodSound::blockSearchExtent),
            Codec.DOUBLE.fieldOf("offset").forGetter(MoodSound::offset)
    ).apply(instance, MoodSound::new));

    public static final Codec<AdditionsSound> ADDITIONS_SOUND = RecordCodecBuilder.create(instance -> instance.group(
            CommonCodecs.KEY_CODEC.fieldOf("sound").forGetter(AdditionsSound::sound),
            Codec.FLOAT.fieldOf("tick_chance").forGetter(AdditionsSound::tickChance)
    ).apply(instance, AdditionsSound::new));

    public static final Codec<AmbientSounds> AMBIENT_SOUNDS = RecordCodecBuilder.create(instance -> instance.group(
            CommonCodecs.KEY_CODEC.optionalFieldOf("loop").forGetter(sounds -> Optional.ofNullable(sounds.loop())),
            MOOD_SOUND.optionalFieldOf("mood").forGetter(sounds -> Optional.ofNullable(sounds.mood())),
            ADDITIONS_SOUND.optionalFieldOf("additions").forGetter(sounds -> Optional.ofNullable(sounds.additions()))
    ).apply(instance, (loop, mood, additions) ->
            new AmbientSounds(loop.orElse(null), mood.orElse(null), additions.orElse(null))));

    public static final Codec<MusicTrack> MUSIC_TRACK = RecordCodecBuilder.create(instance -> instance.group(
            CommonCodecs.KEY_CODEC.fieldOf("sound").forGetter(MusicTrack::sound),
            Codec.INT.fieldOf("min_delay").forGetter(MusicTrack::minDelay),
            Codec.INT.fieldOf("max_delay").forGetter(MusicTrack::maxDelay),
            Codec.BOOL.optionalFieldOf("replace_current_music", false).forGetter(MusicTrack::replaceCurrentMusic)
    ).apply(instance, MusicTrack::new));

    public static final Codec<BackgroundMusic> BACKGROUND_MUSIC = RecordCodecBuilder.create(instance -> instance.group(
            MUSIC_TRACK.optionalFieldOf("default").forGetter(music -> Optional.ofNullable(music.normal())),
            MUSIC_TRACK.optionalFieldOf("underwater").forGetter(music -> Optional.ofNullable(music.underwater())),
            MUSIC_TRACK.optionalFieldOf("creative").forGetter(music -> Optional.ofNullable(music.creative()))
    ).apply(instance, (normal, underwater, creative) ->
            new BackgroundMusic(normal.orElse(null), underwater.orElse(null), creative.orElse(null))));

    private record VisualPart(
            @Nullable Attribute<Integer> fogColor,
            @Nullable Attribute<Float> fogStartDistance,
            @Nullable Attribute<Float> fogEndDistance,
            @Nullable Attribute<Integer> skyColor,
            @Nullable Attribute<Integer> cloudColor,
            @Nullable Attribute<Float> cloudHeight,
            @Nullable Attribute<Integer> skyLightColor,
            @Nullable Attribute<Float> skyLightFactor,
            @Nullable Attribute<Integer> ambientLightColor,
            @Nullable Attribute<Key> defaultDripstoneParticle,
            @Nullable Attribute<Integer> waterFogColor,
            @Nullable Attribute<Float> waterFogEndDistance
    ) { }

    private record GameplayPart(
            @Nullable Attribute<Float> musicVolume,
            @Nullable Attribute<Boolean> canStartRaid,
            @Nullable Attribute<Boolean> canPillagerPatrolSpawn,
            @Nullable Attribute<Boolean> waterEvaporates,
            @Nullable Attribute<BedRule> bedRule,
            @Nullable Attribute<Boolean> respawnAnchorWorks,
            @Nullable Attribute<Boolean> netherPortalSpawnsPiglin,
            @Nullable Attribute<Boolean> fastLava,
            @Nullable Attribute<Boolean> increasedFireBurnout,
            @Nullable Attribute<Boolean> piglinsZombify,
            @Nullable Attribute<Boolean> snowGolemMelts,
            @Nullable Attribute<Float> skyLightLevel
    ) { }

    private static final MapCodec<VisualPart> VISUAL = RecordCodecBuilder.mapCodec(instance -> instance.group(
            modifiable(RGB_COLOR).optionalFieldOf("visual/fog_color")
                    .forGetter(v -> Optional.ofNullable(v.fogColor())),
            modifiable(Codec.FLOAT).optionalFieldOf("visual/fog_start_distance")
                    .forGetter(v -> Optional.ofNullable(v.fogStartDistance())),
            modifiable(Codec.FLOAT).optionalFieldOf("visual/fog_end_distance")
                    .forGetter(v -> Optional.ofNullable(v.fogEndDistance())),
            modifiable(RGB_COLOR).optionalFieldOf("visual/sky_color")
                    .forGetter(v -> Optional.ofNullable(v.skyColor())),
            modifiable(ARGB_COLOR).optionalFieldOf("visual/cloud_color")
                    .forGetter(v -> Optional.ofNullable(v.cloudColor())),
            modifiable(Codec.FLOAT).optionalFieldOf("visual/cloud_height")
                    .forGetter(v -> Optional.ofNullable(v.cloudHeight())),
            modifiable(RGB_COLOR).optionalFieldOf("visual/sky_light_color")
                    .forGetter(v -> Optional.ofNullable(v.skyLightColor())),
            modifiable(Codec.FLOAT).optionalFieldOf("visual/sky_light_factor")
                    .forGetter(v -> Optional.ofNullable(v.skyLightFactor())),
            modifiable(RGB_COLOR).optionalFieldOf("visual/ambient_light_color")
                    .forGetter(v -> Optional.ofNullable(v.ambientLightColor())),
            modifiable(BiomeCodecs.PARTICLE_OPTIONS).optionalFieldOf("visual/default_dripstone_particle")
                    .forGetter(v -> Optional.ofNullable(v.defaultDripstoneParticle())),
            modifiable(RGB_COLOR).optionalFieldOf("visual/water_fog_color")
                    .forGetter(v -> Optional.ofNullable(v.waterFogColor())),
            modifiable(Codec.FLOAT).optionalFieldOf("visual/water_fog_end_distance")
                    .forGetter(v -> Optional.ofNullable(v.waterFogEndDistance()))
    ).apply(instance, (fogColor, fogStartDistance, fogEndDistance, skyColor, cloudColor, cloudHeight, skyLightColor,
                       skyLightFactor, ambientLightColor, defaultDripstoneParticle, waterFogColor,
                       waterFogEndDistance) ->
            new VisualPart(
                    fogColor.orElse(null),
                    fogStartDistance.orElse(null),
                    fogEndDistance.orElse(null),
                    skyColor.orElse(null),
                    cloudColor.orElse(null),
                    cloudHeight.orElse(null),
                    skyLightColor.orElse(null),
                    skyLightFactor.orElse(null),
                    ambientLightColor.orElse(null),
                    defaultDripstoneParticle.orElse(null),
                    waterFogColor.orElse(null),
                    waterFogEndDistance.orElse(null))));

    private static final MapCodec<GameplayPart> GAMEPLAY = RecordCodecBuilder.mapCodec(instance -> instance.group(
            modifiable(Codec.FLOAT).optionalFieldOf("audio/music_volume")
                    .forGetter(g -> Optional.ofNullable(g.musicVolume())),
            modifiable(Codec.BOOL).optionalFieldOf("gameplay/can_start_raid")
                    .forGetter(g -> Optional.ofNullable(g.canStartRaid())),
            modifiable(Codec.BOOL).optionalFieldOf("gameplay/can_pillager_patrol_spawn")
                    .forGetter(g -> Optional.ofNullable(g.canPillagerPatrolSpawn())),
            modifiable(Codec.BOOL).optionalFieldOf("gameplay/water_evaporates")
                    .forGetter(g -> Optional.ofNullable(g.waterEvaporates())),
            modifiable(BED_RULE).optionalFieldOf("gameplay/bed_rule")
                    .forGetter(g -> Optional.ofNullable(g.bedRule())),
            modifiable(Codec.BOOL).optionalFieldOf("gameplay/respawn_anchor_works")
                    .forGetter(g -> Optional.ofNullable(g.respawnAnchorWorks())),
            modifiable(Codec.BOOL).optionalFieldOf("gameplay/nether_portal_spawns_piglin")
                    .forGetter(g -> Optional.ofNullable(g.netherPortalSpawnsPiglin())),
            modifiable(Codec.BOOL).optionalFieldOf("gameplay/fast_lava")
                    .forGetter(g -> Optional.ofNullable(g.fastLava())),
            modifiable(Codec.BOOL).optionalFieldOf("gameplay/increased_fire_burnout")
                    .forGetter(g -> Optional.ofNullable(g.increasedFireBurnout())),
            modifiable(Codec.BOOL).optionalFieldOf("gameplay/piglins_zombify")
                    .forGetter(g -> Optional.ofNullable(g.piglinsZombify())),
            modifiable(Codec.BOOL).optionalFieldOf("gameplay/snow_golem_melts")
                    .forGetter(g -> Optional.ofNullable(g.snowGolemMelts())),
            modifiable(Codec.FLOAT).optionalFieldOf("gameplay/sky_light_level")
                    .forGetter(g -> Optional.ofNullable(g.skyLightLevel()))
    ).apply(instance, (musicVolume, canStartRaid, canPillagerPatrolSpawn, waterEvaporates, bedRule,
                       respawnAnchorWorks, netherPortalSpawnsPiglin, fastLava, increasedFireBurnout,
                       piglinsZombify, snowGolemMelts, skyLightLevel) ->
            new GameplayPart(
                    musicVolume.orElse(null),
                    canStartRaid.orElse(null),
                    canPillagerPatrolSpawn.orElse(null),
                    waterEvaporates.orElse(null),
                    bedRule.orElse(null),
                    respawnAnchorWorks.orElse(null),
                    netherPortalSpawnsPiglin.orElse(null),
                    fastLava.orElse(null),
                    increasedFireBurnout.orElse(null),
                    piglinsZombify.orElse(null),
                    snowGolemMelts.orElse(null),
                    skyLightLevel.orElse(null))));

    public static final Codec<EnvironmentAttributes> ATTRIBUTES = RecordCodecBuilder.create(instance -> instance.group(
            VISUAL.forGetter(a -> new VisualPart(
                    a.fogColor(), a.fogStartDistance(), a.fogEndDistance(), a.skyColor(), a.cloudColor(),
                    a.cloudHeight(), a.skyLightColor(), a.skyLightFactor(), a.ambientLightColor(),
                    a.defaultDripstoneParticle(), a.waterFogColor(), a.waterFogEndDistance())),
            GAMEPLAY.forGetter(a -> new GameplayPart(
                    a.musicVolume(), a.canStartRaid(), a.canPillagerPatrolSpawn(), a.waterEvaporates(),
                    a.bedRule(), a.respawnAnchorWorks(), a.netherPortalSpawnsPiglin(), a.fastLava(),
                    a.increasedFireBurnout(), a.piglinsZombify(), a.snowGolemMelts(), a.skyLightLevel())),
            AMBIENT_PARTICLE.listOf().optionalFieldOf("visual/ambient_particles", List.of())
                    .forGetter(EnvironmentAttributes::ambientParticles),
            AMBIENT_SOUNDS.optionalFieldOf("audio/ambient_sounds")
                    .forGetter(a -> Optional.ofNullable(a.ambientSounds())),
            BACKGROUND_MUSIC.optionalFieldOf("audio/background_music")
                    .forGetter(a -> Optional.ofNullable(a.backgroundMusic()))
    ).apply(instance, (visual, gameplay, ambientParticles, ambientSounds, backgroundMusic) ->
            new EnvironmentAttributes(
                    visual.fogColor(),
                    visual.fogStartDistance(),
                    visual.fogEndDistance(),
                    visual.skyColor(),
                    visual.cloudColor(),
                    visual.cloudHeight(),
                    visual.skyLightColor(),
                    visual.skyLightFactor(),
                    visual.ambientLightColor(),
                    visual.defaultDripstoneParticle(),
                    visual.waterFogColor(),
                    visual.waterFogEndDistance(),
                    gameplay.musicVolume(),
                    gameplay.canStartRaid(),
                    gameplay.canPillagerPatrolSpawn(),
                    gameplay.waterEvaporates(),
                    gameplay.bedRule(),
                    gameplay.respawnAnchorWorks(),
                    gameplay.netherPortalSpawnsPiglin(),
                    gameplay.fastLava(),
                    gameplay.increasedFireBurnout(),
                    gameplay.piglinsZombify(),
                    gameplay.snowGolemMelts(),
                    gameplay.skyLightLevel(),
                    ambientParticles,
                    ambientSounds.orElse(null),
                    backgroundMusic.orElse(null))));

    private EnvironmentAttributeCodecs() {
        throw new UnsupportedOperationException("EnvironmentAttributeCodecs cannot be instantiated.");
    }

    public static <T> Codec<Attribute<T>> modifiable(final Codec<T> value) {
        final Codec<Attribute<T>> expanded = RecordCodecBuilder.create(instance -> instance.group(
                MODIFIER.fieldOf("modifier").forGetter(Attribute::modifier),
                value.fieldOf("argument").forGetter(Attribute::value)
        ).apply(instance, (modifier, argument) -> new Attribute<>(argument, modifier)));

        return Codec.either(value, expanded).xmap(
                either -> either.map(Attribute::of, attribute -> attribute),
                attribute -> attribute.modifier() == Modifier.OVERRIDE
                        ? Either.left(attribute.value())
                        : Either.right(attribute));
    }

    private static String formatHexColor(final int color) {
        return String.format("#%06x", color & 0xFFFFFF);
    }

    private static DataResult<Integer> parseHexColor(final String value) {
        final String digits = value.startsWith("#") ? value.substring(1) : value;
        if (digits.length() != 6) {
            return DataResult.error(() -> "Expected a #rrggbb color, got " + value);
        }
        try {
            return DataResult.success(Integer.parseInt(digits, 16));
        } catch (final NumberFormatException exception) {
            return DataResult.error(() -> "Malformed hexadecimal color: " + value);
        }
    }

    private static String formatHexColorARGB(final int color) {
        return String.format("#%08x", color);
    }

    private static DataResult<Integer> parseHexColorARGB(final String value) {
        final String digits = value.startsWith("#") ? value.substring(1) : value;
        if (digits.length() != 8) {
            return DataResult.error(() -> "Expected a #aarrggbb color, got " + value);
        }
        try {
            return DataResult.success((int) Long.parseLong(digits, 16));
        } catch (final NumberFormatException exception) {
            return DataResult.error(() -> "Malformed hexadecimal color: " + value);
        }
    }
}
