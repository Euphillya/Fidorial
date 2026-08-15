package fr.euphyllia.fidorial.server.codecs.world;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import fr.euphyllia.fidorial.server.codecs.CommonCodecs;
import fr.fidorial.world.environment.AdditionsSound;
import fr.fidorial.world.environment.AmbientParticle;
import fr.fidorial.world.environment.AmbientSounds;
import fr.fidorial.world.environment.BackgroundMusic;
import fr.fidorial.world.environment.Attribute;
import fr.fidorial.world.environment.EnvironmentAttributes;
import fr.fidorial.world.environment.Modifier;
import fr.fidorial.world.environment.MoodSound;
import fr.fidorial.world.environment.MusicTrack;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;


public class EnvironmentAttributeCodecs {

    public static final Codec<Integer> RGB_COLOR = Codec.either(Codec.STRING, Codec.INT)
            .comapFlatMap(
                    either -> either.map(EnvironmentAttributeCodecs::parseHexColor, DataResult::success),
                    color -> Either.left(formatHexColor(color)));

    public static final Codec<Modifier> MODIFIER = Codec.STRING.comapFlatMap(
            name -> Arrays.stream(Modifier.values())
                    .filter(modifier -> modifier.id().equals(name))
                    .findFirst()
                    .map(DataResult::success)
                    .orElseGet(() -> DataResult.error(() -> "Unknown attribute modifier: " + name)),
            Modifier::id);

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

    public static final Codec<EnvironmentAttributes> ATTRIBUTES = RecordCodecBuilder.create(instance -> instance.group(

            modifiable(RGB_COLOR).optionalFieldOf("minecraft:visual/fog_color")
                    .forGetter(a -> Optional.ofNullable(a.fogColor())),
            modifiable(RGB_COLOR).optionalFieldOf("minecraft:visual/sky_color")
                    .forGetter(a -> Optional.ofNullable(a.skyColor())),
            modifiable(RGB_COLOR).optionalFieldOf("minecraft:visual/water_fog_color")
                    .forGetter(a -> Optional.ofNullable(a.waterFogColor())),
            modifiable(Codec.FLOAT).optionalFieldOf("minecraft:visual/water_fog_end_distance")
                    .forGetter(a -> Optional.ofNullable(a.waterFogEndDistance())),
            modifiable(Codec.BOOL).optionalFieldOf("minecraft:visual/extra_fog")
                    .forGetter(a -> Optional.ofNullable(a.extraFog())),
            modifiable(Codec.FLOAT).optionalFieldOf("minecraft:audio/music_volume")
                    .forGetter(a -> Optional.ofNullable(a.musicVolume())),
            modifiable(Codec.BOOL).optionalFieldOf("minecraft:gameplay/can_start_raid")
                    .forGetter(a -> Optional.ofNullable(a.canStartRaid())),
            modifiable(Codec.BOOL).optionalFieldOf("minecraft:gameplay/can_pillager_patrol_spawn")
                    .forGetter(a -> Optional.ofNullable(a.canPillagerPatrolSpawn())),
            modifiable(Codec.BOOL).optionalFieldOf("minecraft:gameplay/water_evaporates")
                    .forGetter(a -> Optional.ofNullable(a.waterEvaporates())),
            modifiable(Codec.BOOL).optionalFieldOf("minecraft:gameplay/respawn_anchor_works")
                    .forGetter(a -> Optional.ofNullable(a.respawnAnchorWorks())),
            modifiable(Codec.BOOL).optionalFieldOf("minecraft:gameplay/increased_fire_burnout")
                    .forGetter(a -> Optional.ofNullable(a.increasedFireBurnout())),
            modifiable(Codec.BOOL).optionalFieldOf("minecraft:gameplay/piglins_zombify")
                    .forGetter(a -> Optional.ofNullable(a.piglinsZombify())),
            modifiable(Codec.BOOL).optionalFieldOf("minecraft:gameplay/snow_golem_melts")
                    .forGetter(a -> Optional.ofNullable(a.snowGolemMelts())),
            AMBIENT_PARTICLE.listOf().optionalFieldOf("minecraft:visual/ambient_particles", List.of())
                    .forGetter(EnvironmentAttributes::ambientParticles),
            AMBIENT_SOUNDS.optionalFieldOf("minecraft:audio/ambient_sounds")
                    .forGetter(a -> Optional.ofNullable(a.ambientSounds())),
            BACKGROUND_MUSIC.optionalFieldOf("minecraft:audio/background_music")
                    .forGetter(a -> Optional.ofNullable(a.backgroundMusic()))
    ).apply(instance, (fogColor, skyColor, waterFogColor, waterFogEndDistance, extraFog, musicVolume, canStartRaid, canPillagerPatrolSpawn, waterEvaporates, respawnAnchorWorks, increasedFireBurnout, piglinsZombify, snowGolemMelts, ambientParticles, ambientSounds, backgroundMusic) ->
            new EnvironmentAttributes(

                    fogColor.orElse(null),
                    skyColor.orElse(null),
                    waterFogColor.orElse(null),
                    waterFogEndDistance.orElse(null),
                    extraFog.orElse(null),
                    musicVolume.orElse(null),
                    canStartRaid.orElse(null),
                    canPillagerPatrolSpawn.orElse(null),
                    waterEvaporates.orElse(null),
                    respawnAnchorWorks.orElse(null),
                    increasedFireBurnout.orElse(null),
                    piglinsZombify.orElse(null),
                    snowGolemMelts.orElse(null),
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
}
