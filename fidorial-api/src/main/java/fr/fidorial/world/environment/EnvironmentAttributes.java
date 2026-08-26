package fr.fidorial.world.environment;

import net.kyori.adventure.key.Key;
import org.jetbrains.annotations.Contract;
import org.jspecify.annotations.Nullable;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @param fogColor                    distance fog color, packed RGB — {@code minecraft:visual/fog_color}
 * @param fogStartDistance            distance in blocks at which fog begins — {@code minecraft:visual/fog_start_distance}
 * @param fogEndDistance               distance in blocks at which fog reaches full density — {@code minecraft:visual/fog_end_distance}
 * @param skyColor                    sky color, packed RGB — {@code minecraft:visual/sky_color}
 * @param cloudColor                  cloud color, packed ARGB — {@code minecraft:visual/cloud_color}
 * @param cloudHeight                 height, in blocks, at which clouds render — {@code minecraft:visual/cloud_height}
 * @param skyLightColor               sky light color, packed RGB — {@code minecraft:visual/sky_light_color}
 * @param skyLightFactor              multiplier applied to sky light, within {@code [0, 1]} — {@code minecraft:visual/sky_light_factor}
 * @param ambientLightColor           ambient light color, packed RGB — {@code minecraft:visual/ambient_light_color}
 * @param defaultDripstoneParticle    particle used by dripping dripstone — {@code minecraft:visual/default_dripstone_particle}
 * @param waterFogColor               underwater fog color, packed RGB — {@code minecraft:visual/water_fog_color}
 * @param waterFogEndDistance         distance in blocks at which underwater fog reaches full density — {@code minecraft:visual/water_fog_end_distance}
 * @param musicVolume                 volume music fades to, within {@code [0, 1]} — {@code minecraft:audio/music_volume}
 * @param canStartRaid                whether a raid can begin here — {@code minecraft:gameplay/can_start_raid}
 * @param canPillagerPatrolSpawn      whether pillager patrols spawn here — {@code minecraft:gameplay/can_pillager_patrol_spawn}
 * @param waterEvaporates             whether placed water evaporates — {@code minecraft:gameplay/water_evaporates}
 * @param bedRule                     when beds are usable to sleep/set spawn — {@code minecraft:gameplay/bed_rule}
 * @param respawnAnchorWorks          whether respawn anchors set spawn instead of exploding — {@code minecraft:gameplay/respawn_anchor_works}
 * @param netherPortalSpawnsPiglin    whether entering a nether portal can spawn a piglin — {@code minecraft:gameplay/nether_portal_spawns_piglin}
 * @param fastLava                    whether lava flows and spreads at Nether speed — {@code minecraft:gameplay/fast_lava}
 * @param increasedFireBurnout        whether fire burns out faster — {@code minecraft:gameplay/increased_fire_burnout}
 * @param piglinsZombify              whether piglins and hoglins zombify — {@code minecraft:gameplay/piglins_zombify}
 * @param snowGolemMelts              whether snow golems take damage — {@code minecraft:gameplay/snow_golem_melts}
 * @param skyLightLevel               sky light level, within {@code [0, 15]} — {@code minecraft:gameplay/sky_light_level}
 * @param ambientParticles            particles randomly spawned around the camera, possibly empty — {@code minecraft:visual/ambient_particles}
 * @param ambientSounds                looping, mood and additions sounds — {@code minecraft:audio/ambient_sounds}
 * @param backgroundMusic              music tracks — {@code minecraft:audio/background_music}
 * @since 0.1.0
 */
public record EnvironmentAttributes(
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
        @Nullable Attribute<Float> waterFogEndDistance,
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
        @Nullable Attribute<Float> skyLightLevel,
        List<AmbientParticle> ambientParticles,
        @Nullable AmbientSounds ambientSounds,
        @Nullable BackgroundMusic backgroundMusic
) {

    /**
     * Sets nothing, letting the dimension and the vanilla defaults decide everything.
     */
    public static final EnvironmentAttributes EMPTY = builder().build();

    public EnvironmentAttributes {
        ambientParticles = List.copyOf(ambientParticles);
    }

    /**
     * {@return a new, empty builder}
     */
    @Contract(value = "-> new", pure = true)
    public static Builder builder() {
        return new Builder();
    }

    /**
     * {@return a new builder pre-filled with the values of {@code attributes}}
     *
     * @param attributes the attributes to copy
     */
    @Contract(value = "_ -> new", pure = true)
    public static Builder builder(final EnvironmentAttributes attributes) {
        return new Builder(attributes);
    }

    /**
     * Computes the sky color the vanilla generator derives from a given temperature.
     *
     * @param temperature the biome temperature
     * @return the packed RGB sky color
     */
    public static int skyColorFor(final float temperature) {
        final float scaled = Math.clamp(temperature / 3F, -1F, 1F);
        return Color.HSBtoRGB(0.62222224F - scaled * 0.05F, 0.5F + scaled * 0.1F, 1.0F) & 0xFFFFFF;
    }

    /**
     * {@return whether this sets no attribute at all}
     */
    public boolean isEmpty() {
        return EMPTY.equals(this);
    }

    /**
     * Mutable builder for {@link EnvironmentAttributes}.
     *
     * @since 0.1.0
     */
    public static final class Builder {
        private final List<AmbientParticle> ambientParticles = new ArrayList<>();
        private @Nullable Attribute<Integer> fogColor;
        private @Nullable Attribute<Float> fogStartDistance;
        private @Nullable Attribute<Float> fogEndDistance;
        private @Nullable Attribute<Integer> skyColor;
        private @Nullable Attribute<Integer> cloudColor;
        private @Nullable Attribute<Float> cloudHeight;
        private @Nullable Attribute<Integer> skyLightColor;
        private @Nullable Attribute<Float> skyLightFactor;
        private @Nullable Attribute<Integer> ambientLightColor;
        private @Nullable Attribute<Key> defaultDripstoneParticle;
        private @Nullable Attribute<Integer> waterFogColor;
        private @Nullable Attribute<Float> waterFogEndDistance;
        private @Nullable Attribute<Float> musicVolume;
        private @Nullable Attribute<Boolean> canStartRaid;
        private @Nullable Attribute<Boolean> canPillagerPatrolSpawn;
        private @Nullable Attribute<Boolean> waterEvaporates;
        private @Nullable Attribute<BedRule> bedRule;
        private @Nullable Attribute<Boolean> respawnAnchorWorks;
        private @Nullable Attribute<Boolean> netherPortalSpawnsPiglin;
        private @Nullable Attribute<Boolean> fastLava;
        private @Nullable Attribute<Boolean> increasedFireBurnout;
        private @Nullable Attribute<Boolean> piglinsZombify;
        private @Nullable Attribute<Boolean> snowGolemMelts;
        private @Nullable Attribute<Float> skyLightLevel;
        private @Nullable AmbientSounds ambientSounds;
        private @Nullable BackgroundMusic backgroundMusic;

        private Builder() {
        }

        private Builder(final EnvironmentAttributes attributes) {
            this.fogColor = attributes.fogColor;
            this.fogStartDistance = attributes.fogStartDistance;
            this.fogEndDistance = attributes.fogEndDistance;
            this.skyColor = attributes.skyColor;
            this.cloudColor = attributes.cloudColor;
            this.cloudHeight = attributes.cloudHeight;
            this.skyLightColor = attributes.skyLightColor;
            this.skyLightFactor = attributes.skyLightFactor;
            this.ambientLightColor = attributes.ambientLightColor;
            this.defaultDripstoneParticle = attributes.defaultDripstoneParticle;
            this.waterFogColor = attributes.waterFogColor;
            this.waterFogEndDistance = attributes.waterFogEndDistance;
            this.musicVolume = attributes.musicVolume;
            this.canStartRaid = attributes.canStartRaid;
            this.canPillagerPatrolSpawn = attributes.canPillagerPatrolSpawn;
            this.waterEvaporates = attributes.waterEvaporates;
            this.bedRule = attributes.bedRule;
            this.respawnAnchorWorks = attributes.respawnAnchorWorks;
            this.netherPortalSpawnsPiglin = attributes.netherPortalSpawnsPiglin;
            this.fastLava = attributes.fastLava;
            this.increasedFireBurnout = attributes.increasedFireBurnout;
            this.piglinsZombify = attributes.piglinsZombify;
            this.snowGolemMelts = attributes.snowGolemMelts;
            this.skyLightLevel = attributes.skyLightLevel;
            this.ambientParticles.addAll(attributes.ambientParticles);
            this.ambientSounds = attributes.ambientSounds;
            this.backgroundMusic = attributes.backgroundMusic;
        }

        /**
         * Sets {@code minecraft:visual/fog_color} as a plain override.
         *
         * @param value distance fog color, packed RGB, or {@code null} to leave unset
         * @return this builder
         */
        @Contract("_ -> this")
        public Builder fogColor(final @Nullable Integer value) {
            this.fogColor = value == null ? null : Attribute.of(value);
            return this;
        }

        /**
         * Sets {@code minecraft:visual/fog_color}, deriving from the dimension's value.
         *
         * @param value    the modifier argument
         * @param modifier how to combine it
         * @return this builder
         */
        @Contract("_, _ -> this")
        public Builder fogColor(final Integer value, final Modifier modifier) {
            this.fogColor = Attribute.of(value, modifier);
            return this;
        }

        /**
         * Sets {@code minecraft:visual/fog_start_distance} as a plain override.
         *
         * @param value distance in blocks at which fog begins, or {@code null} to leave unset
         * @return this builder
         */
        @Contract("_ -> this")
        public Builder fogStartDistance(final @Nullable Float value) {
            this.fogStartDistance = value == null ? null : Attribute.of(value);
            return this;
        }

        /**
         * Sets {@code minecraft:visual/fog_start_distance}, deriving from the dimension's value.
         *
         * @param value    the modifier argument
         * @param modifier how to combine it
         * @return this builder
         */
        @Contract("_, _ -> this")
        public Builder fogStartDistance(final Float value, final Modifier modifier) {
            this.fogStartDistance = Attribute.of(value, modifier);
            return this;
        }

        /**
         * Sets {@code minecraft:visual/fog_end_distance} as a plain override.
         *
         * @param value distance in blocks at which fog reaches full density, or {@code null} to leave unset
         * @return this builder
         */
        @Contract("_ -> this")
        public Builder fogEndDistance(final @Nullable Float value) {
            this.fogEndDistance = value == null ? null : Attribute.of(value);
            return this;
        }

        /**
         * Sets {@code minecraft:visual/fog_end_distance}, deriving from the dimension's value.
         *
         * @param value    the modifier argument
         * @param modifier how to combine it
         * @return this builder
         */
        @Contract("_, _ -> this")
        public Builder fogEndDistance(final Float value, final Modifier modifier) {
            this.fogEndDistance = Attribute.of(value, modifier);
            return this;
        }

        /**
         * Sets {@code minecraft:visual/sky_color} as a plain override.
         *
         * @param value sky color, packed RGB, or {@code null} to leave unset
         * @return this builder
         */
        @Contract("_ -> this")
        public Builder skyColor(final @Nullable Integer value) {
            this.skyColor = value == null ? null : Attribute.of(value);
            return this;
        }

        /**
         * Sets {@code minecraft:visual/sky_color}, deriving from the dimension's value.
         *
         * @param value    the modifier argument
         * @param modifier how to combine it
         * @return this builder
         */
        @Contract("_, _ -> this")
        public Builder skyColor(final Integer value, final Modifier modifier) {
            this.skyColor = Attribute.of(value, modifier);
            return this;
        }

        /**
         * Sets {@code minecraft:visual/cloud_color} as a plain override.
         *
         * @param value cloud color, packed ARGB, or {@code null} to leave unset
         * @return this builder
         */
        @Contract("_ -> this")
        public Builder cloudColor(final @Nullable Integer value) {
            this.cloudColor = value == null ? null : Attribute.of(value);
            return this;
        }

        /**
         * Sets {@code minecraft:visual/cloud_color}, deriving from the dimension's value.
         *
         * @param value    the modifier argument
         * @param modifier how to combine it
         * @return this builder
         */
        @Contract("_, _ -> this")
        public Builder cloudColor(final Integer value, final Modifier modifier) {
            this.cloudColor = Attribute.of(value, modifier);
            return this;
        }

        /**
         * Sets {@code minecraft:visual/cloud_height} as a plain override.
         *
         * @param value height, in blocks, at which clouds render, or {@code null} to leave unset
         * @return this builder
         */
        @Contract("_ -> this")
        public Builder cloudHeight(final @Nullable Float value) {
            this.cloudHeight = value == null ? null : Attribute.of(value);
            return this;
        }

        /**
         * Sets {@code minecraft:visual/cloud_height}, deriving from the dimension's value.
         *
         * @param value    the modifier argument
         * @param modifier how to combine it
         * @return this builder
         */
        @Contract("_, _ -> this")
        public Builder cloudHeight(final Float value, final Modifier modifier) {
            this.cloudHeight = Attribute.of(value, modifier);
            return this;
        }

        /**
         * Sets {@code minecraft:visual/sky_light_color} as a plain override.
         *
         * @param value sky light color, packed RGB, or {@code null} to leave unset
         * @return this builder
         */
        @Contract("_ -> this")
        public Builder skyLightColor(final @Nullable Integer value) {
            this.skyLightColor = value == null ? null : Attribute.of(value);
            return this;
        }

        /**
         * Sets {@code minecraft:visual/sky_light_color}, deriving from the dimension's value.
         *
         * @param value    the modifier argument
         * @param modifier how to combine it
         * @return this builder
         */
        @Contract("_, _ -> this")
        public Builder skyLightColor(final Integer value, final Modifier modifier) {
            this.skyLightColor = Attribute.of(value, modifier);
            return this;
        }

        /**
         * Sets {@code minecraft:visual/sky_light_factor} as a plain override.
         *
         * @param value multiplier applied to sky light, within {@code [0, 1]}, or {@code null} to leave unset
         * @return this builder
         */
        @Contract("_ -> this")
        public Builder skyLightFactor(final @Nullable Float value) {
            this.skyLightFactor = value == null ? null : Attribute.of(value);
            return this;
        }

        /**
         * Sets {@code minecraft:visual/sky_light_factor}, deriving from the dimension's value.
         *
         * @param value    the modifier argument
         * @param modifier how to combine it
         * @return this builder
         */
        @Contract("_, _ -> this")
        public Builder skyLightFactor(final Float value, final Modifier modifier) {
            this.skyLightFactor = Attribute.of(value, modifier);
            return this;
        }

        /**
         * Sets {@code minecraft:visual/ambient_light_color} as a plain override.
         *
         * @param value ambient light color, packed RGB, or {@code null} to leave unset
         * @return this builder
         */
        @Contract("_ -> this")
        public Builder ambientLightColor(final @Nullable Integer value) {
            this.ambientLightColor = value == null ? null : Attribute.of(value);
            return this;
        }

        /**
         * Sets {@code minecraft:visual/ambient_light_color}, deriving from the dimension's value.
         *
         * @param value    the modifier argument
         * @param modifier how to combine it
         * @return this builder
         */
        @Contract("_, _ -> this")
        public Builder ambientLightColor(final Integer value, final Modifier modifier) {
            this.ambientLightColor = Attribute.of(value, modifier);
            return this;
        }

        /**
         * Sets {@code minecraft:visual/default_dripstone_particle} as a plain override.
         *
         * @param value particle used by dripping dripstone, or {@code null} to leave unset
         * @return this builder
         */
        @Contract("_ -> this")
        public Builder defaultDripstoneParticle(final @Nullable Key value) {
            this.defaultDripstoneParticle = value == null ? null : Attribute.of(value);
            return this;
        }

        /**
         * Sets {@code minecraft:visual/default_dripstone_particle}, deriving from the dimension's value.
         *
         * @param value    the modifier argument
         * @param modifier how to combine it
         * @return this builder
         */
        @Contract("_, _ -> this")
        public Builder defaultDripstoneParticle(final Key value, final Modifier modifier) {
            this.defaultDripstoneParticle = Attribute.of(value, modifier);
            return this;
        }

        /**
         * Sets {@code minecraft:visual/water_fog_color} as a plain override.
         *
         * @param value underwater fog color, packed RGB, or {@code null} to leave unset
         * @return this builder
         */
        @Contract("_ -> this")
        public Builder waterFogColor(final @Nullable Integer value) {
            this.waterFogColor = value == null ? null : Attribute.of(value);
            return this;
        }

        /**
         * Sets {@code minecraft:visual/water_fog_color}, deriving from the dimension's value.
         *
         * @param value    the modifier argument
         * @param modifier how to combine it
         * @return this builder
         */
        @Contract("_, _ -> this")
        public Builder waterFogColor(final Integer value, final Modifier modifier) {
            this.waterFogColor = Attribute.of(value, modifier);
            return this;
        }

        /**
         * Sets {@code minecraft:visual/water_fog_end_distance} as a plain override.
         *
         * @param value distance in blocks at which underwater fog reaches full density, or {@code null} to leave unset
         * @return this builder
         */
        @Contract("_ -> this")
        public Builder waterFogEndDistance(final @Nullable Float value) {
            this.waterFogEndDistance = value == null ? null : Attribute.of(value);
            return this;
        }

        /**
         * Sets {@code minecraft:visual/water_fog_end_distance}, deriving from the dimension's value.
         *
         * @param value    the modifier argument
         * @param modifier how to combine it
         * @return this builder
         */
        @Contract("_, _ -> this")
        public Builder waterFogEndDistance(final Float value, final Modifier modifier) {
            this.waterFogEndDistance = Attribute.of(value, modifier);
            return this;
        }

        /**
         * Sets {@code minecraft:audio/music_volume} as a plain override.
         *
         * @param value volume music fades to, within {@code [0, 1]}, or {@code null} to leave unset
         * @return this builder
         */
        @Contract("_ -> this")
        public Builder musicVolume(final @Nullable Float value) {
            this.musicVolume = value == null ? null : Attribute.of(value);
            return this;
        }

        /**
         * Sets {@code minecraft:audio/music_volume}, deriving from the dimension's value.
         *
         * @param value    the modifier argument
         * @param modifier how to combine it
         * @return this builder
         */
        @Contract("_, _ -> this")
        public Builder musicVolume(final Float value, final Modifier modifier) {
            this.musicVolume = Attribute.of(value, modifier);
            return this;
        }

        /**
         * Sets {@code minecraft:gameplay/can_start_raid} as a plain override.
         *
         * @param value whether a raid can begin here, or {@code null} to leave unset
         * @return this builder
         */
        @Contract("_ -> this")
        public Builder canStartRaid(final @Nullable Boolean value) {
            this.canStartRaid = value == null ? null : Attribute.of(value);
            return this;
        }

        /**
         * Sets {@code minecraft:gameplay/can_start_raid}, deriving from the dimension's value.
         *
         * @param value    the modifier argument
         * @param modifier how to combine it
         * @return this builder
         */
        @Contract("_, _ -> this")
        public Builder canStartRaid(final Boolean value, final Modifier modifier) {
            this.canStartRaid = Attribute.of(value, modifier);
            return this;
        }

        /**
         * Sets {@code minecraft:gameplay/can_pillager_patrol_spawn} as a plain override.
         *
         * @param value whether pillager patrols spawn here, or {@code null} to leave unset
         * @return this builder
         */
        @Contract("_ -> this")
        public Builder canPillagerPatrolSpawn(final @Nullable Boolean value) {
            this.canPillagerPatrolSpawn = value == null ? null : Attribute.of(value);
            return this;
        }

        /**
         * Sets {@code minecraft:gameplay/can_pillager_patrol_spawn}, deriving from the dimension's value.
         *
         * @param value    the modifier argument
         * @param modifier how to combine it
         * @return this builder
         */
        @Contract("_, _ -> this")
        public Builder canPillagerPatrolSpawn(final Boolean value, final Modifier modifier) {
            this.canPillagerPatrolSpawn = Attribute.of(value, modifier);
            return this;
        }

        /**
         * Sets {@code minecraft:gameplay/water_evaporates} as a plain override.
         *
         * @param value whether placed water evaporates, or {@code null} to leave unset
         * @return this builder
         */
        @Contract("_ -> this")
        public Builder waterEvaporates(final @Nullable Boolean value) {
            this.waterEvaporates = value == null ? null : Attribute.of(value);
            return this;
        }

        /**
         * Sets {@code minecraft:gameplay/water_evaporates}, deriving from the dimension's value.
         *
         * @param value    the modifier argument
         * @param modifier how to combine it
         * @return this builder
         */
        @Contract("_, _ -> this")
        public Builder waterEvaporates(final Boolean value, final Modifier modifier) {
            this.waterEvaporates = Attribute.of(value, modifier);
            return this;
        }

        /**
         * Sets {@code minecraft:gameplay/bed_rule} as a plain override.
         *
         * @param value when beds are usable to sleep/set spawn, or {@code null} to leave unset
         * @return this builder
         */
        @Contract("_ -> this")
        public Builder bedRule(final @Nullable BedRule value) {
            this.bedRule = value == null ? null : Attribute.of(value);
            return this;
        }

        /**
         * Sets {@code minecraft:gameplay/bed_rule}, deriving from the dimension's value.
         *
         * @param value    the modifier argument
         * @param modifier how to combine it
         * @return this builder
         */
        @Contract("_, _ -> this")
        public Builder bedRule(final BedRule value, final Modifier modifier) {
            this.bedRule = Attribute.of(value, modifier);
            return this;
        }

        /**
         * Sets {@code minecraft:gameplay/respawn_anchor_works} as a plain override.
         *
         * @param value whether respawn anchors set spawn instead of exploding, or {@code null} to leave unset
         * @return this builder
         */
        @Contract("_ -> this")
        public Builder respawnAnchorWorks(final @Nullable Boolean value) {
            this.respawnAnchorWorks = value == null ? null : Attribute.of(value);
            return this;
        }

        /**
         * Sets {@code minecraft:gameplay/respawn_anchor_works}, deriving from the dimension's value.
         *
         * @param value    the modifier argument
         * @param modifier how to combine it
         * @return this builder
         */
        @Contract("_, _ -> this")
        public Builder respawnAnchorWorks(final Boolean value, final Modifier modifier) {
            this.respawnAnchorWorks = Attribute.of(value, modifier);
            return this;
        }

        /**
         * Sets {@code minecraft:gameplay/nether_portal_spawns_piglin} as a plain override.
         *
         * @param value whether entering a nether portal can spawn a piglin, or {@code null} to leave unset
         * @return this builder
         */
        @Contract("_ -> this")
        public Builder netherPortalSpawnsPiglin(final @Nullable Boolean value) {
            this.netherPortalSpawnsPiglin = value == null ? null : Attribute.of(value);
            return this;
        }

        /**
         * Sets {@code minecraft:gameplay/nether_portal_spawns_piglin}, deriving from the dimension's value.
         *
         * @param value    the modifier argument
         * @param modifier how to combine it
         * @return this builder
         */
        @Contract("_, _ -> this")
        public Builder netherPortalSpawnsPiglin(final Boolean value, final Modifier modifier) {
            this.netherPortalSpawnsPiglin = Attribute.of(value, modifier);
            return this;
        }

        /**
         * Sets {@code minecraft:gameplay/fast_lava} as a plain override.
         *
         * @param value whether lava flows and spreads at Nether speed, or {@code null} to leave unset
         * @return this builder
         */
        @Contract("_ -> this")
        public Builder fastLava(final @Nullable Boolean value) {
            this.fastLava = value == null ? null : Attribute.of(value);
            return this;
        }

        /**
         * Sets {@code minecraft:gameplay/fast_lava}, deriving from the dimension's value.
         *
         * @param value    the modifier argument
         * @param modifier how to combine it
         * @return this builder
         */
        @Contract("_, _ -> this")
        public Builder fastLava(final Boolean value, final Modifier modifier) {
            this.fastLava = Attribute.of(value, modifier);
            return this;
        }

        /**
         * Sets {@code minecraft:gameplay/increased_fire_burnout} as a plain override.
         *
         * @param value whether fire burns out faster, or {@code null} to leave unset
         * @return this builder
         */
        @Contract("_ -> this")
        public Builder increasedFireBurnout(final @Nullable Boolean value) {
            this.increasedFireBurnout = value == null ? null : Attribute.of(value);
            return this;
        }

        /**
         * Sets {@code minecraft:gameplay/increased_fire_burnout}, deriving from the dimension's value.
         *
         * @param value    the modifier argument
         * @param modifier how to combine it
         * @return this builder
         */
        @Contract("_, _ -> this")
        public Builder increasedFireBurnout(final Boolean value, final Modifier modifier) {
            this.increasedFireBurnout = Attribute.of(value, modifier);
            return this;
        }

        /**
         * Sets {@code minecraft:gameplay/piglins_zombify} as a plain override.
         *
         * @param value whether piglins and hoglins zombify, or {@code null} to leave unset
         * @return this builder
         */
        @Contract("_ -> this")
        public Builder piglinsZombify(final @Nullable Boolean value) {
            this.piglinsZombify = value == null ? null : Attribute.of(value);
            return this;
        }

        /**
         * Sets {@code minecraft:gameplay/piglins_zombify}, deriving from the dimension's value.
         *
         * @param value    the modifier argument
         * @param modifier how to combine it
         * @return this builder
         */
        @Contract("_, _ -> this")
        public Builder piglinsZombify(final Boolean value, final Modifier modifier) {
            this.piglinsZombify = Attribute.of(value, modifier);
            return this;
        }

        /**
         * Sets {@code minecraft:gameplay/snow_golem_melts} as a plain override.
         *
         * @param value whether snow golems take damage, or {@code null} to leave unset
         * @return this builder
         */
        @Contract("_ -> this")
        public Builder snowGolemMelts(final @Nullable Boolean value) {
            this.snowGolemMelts = value == null ? null : Attribute.of(value);
            return this;
        }

        /**
         * Sets {@code minecraft:gameplay/snow_golem_melts}, deriving from the dimension's value.
         *
         * @param value    the modifier argument
         * @param modifier how to combine it
         * @return this builder
         */
        @Contract("_, _ -> this")
        public Builder snowGolemMelts(final Boolean value, final Modifier modifier) {
            this.snowGolemMelts = Attribute.of(value, modifier);
            return this;
        }

        /**
         * Sets {@code minecraft:gameplay/sky_light_level} as a plain override.
         *
         * @param value sky light level, within {@code [0, 15]}, or {@code null} to leave unset
         * @return this builder
         */
        @Contract("_ -> this")
        public Builder skyLightLevel(final @Nullable Float value) {
            this.skyLightLevel = value == null ? null : Attribute.of(value);
            return this;
        }

        /**
         * Sets {@code minecraft:gameplay/sky_light_level}, deriving from the dimension's value.
         *
         * @param value    the modifier argument
         * @param modifier how to combine it
         * @return this builder
         */
        @Contract("_, _ -> this")
        public Builder skyLightLevel(final Float value, final Modifier modifier) {
            this.skyLightLevel = Attribute.of(value, modifier);
            return this;
        }

        /**
         * Adds one ambient particle to {@code minecraft:visual/ambient_particles}.
         *
         * @param particle the particle
         * @return this builder
         */
        @Contract("_ -> this")
        public Builder addAmbientParticle(final AmbientParticle particle) {
            this.ambientParticles.add(Objects.requireNonNull(particle, "particle"));
            return this;
        }

        /**
         * Replaces the whole ambient particle list.
         *
         * @param particles the particles
         * @return this builder
         */
        @Contract("_ -> this")
        public Builder ambientParticles(final List<AmbientParticle> particles) {
            this.ambientParticles.clear();
            this.ambientParticles.addAll(particles);
            return this;
        }

        /**
         * Sets {@code minecraft:audio/ambient_sounds}.
         *
         * @param value looping, mood and additions sounds, or {@code null} to leave unset
         * @return this builder
         */
        @Contract("_ -> this")
        public Builder ambientSounds(final @Nullable AmbientSounds value) {
            this.ambientSounds = value;
            return this;
        }

        /**
         * Sets {@code minecraft:audio/background_music}.
         *
         * @param value music tracks, or {@code null} to leave unset
         * @return this builder
         */
        @Contract("_ -> this")
        public Builder backgroundMusic(final @Nullable BackgroundMusic value) {
            this.backgroundMusic = value;
            return this;
        }

        /**
         * {@return the immutable attributes described by this builder}
         */
        @Contract(value = "-> new", pure = true)
        public EnvironmentAttributes build() {
            return new EnvironmentAttributes(
                    fogColor,
                    fogStartDistance,
                    fogEndDistance,
                    skyColor,
                    cloudColor,
                    cloudHeight,
                    skyLightColor,
                    skyLightFactor,
                    ambientLightColor,
                    defaultDripstoneParticle,
                    waterFogColor,
                    waterFogEndDistance,
                    musicVolume,
                    canStartRaid,
                    canPillagerPatrolSpawn,
                    waterEvaporates,
                    bedRule,
                    respawnAnchorWorks,
                    netherPortalSpawnsPiglin,
                    fastLava,
                    increasedFireBurnout,
                    piglinsZombify,
                    snowGolemMelts,
                    skyLightLevel,
                    List.copyOf(ambientParticles),
                    ambientSounds,
                    backgroundMusic);
        }
    }
}
