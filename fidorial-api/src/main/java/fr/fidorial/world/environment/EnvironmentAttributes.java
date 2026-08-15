package fr.fidorial.world.environment;

import org.jetbrains.annotations.Contract;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * The environment attributes a biome sets, written as the {@code attributes} field of its effects.
 *
 * @param fogColor                 distance fog color, packed RGB — {@code minecraft:visual/fog_color}
 * @param skyColor                 sky color, packed RGB — {@code minecraft:visual/sky_color}
 * @param waterFogColor            underwater fog color, packed RGB — {@code minecraft:visual/water_fog_color}
 * @param waterFogRadius           distance in blocks at which underwater fog peaks — {@code minecraft:visual/water_fog_radius}
 * @param extraFog                 whether to use dense, Nether-like fog — {@code minecraft:visual/extra_fog}
 * @param ambientParticles         particles randomly spawned around the camera — {@code minecraft:visual/ambient_particles}
 * @param ambientSounds            looping, mood and additions sounds — {@code minecraft:audio/ambient_sounds}
 * @param backgroundMusic          music tracks — {@code minecraft:audio/background_music}
 * @param musicVolume              volume music fades to, within {@code [0, 1]} — {@code minecraft:audio/music_volume}
 * @param canStartRaid             whether a raid can begin here — {@code minecraft:gameplay/can_start_raid}
 * @param waterEvaporates          whether placed water evaporates — {@code minecraft:gameplay/water_evaporates}
 * @param respawnAnchorWorks       whether respawn anchors set spawn instead of exploding — {@code minecraft:gameplay/respawn_anchor_works}
 * @param netherPortalSpawnsPiglin whether portals spawn piglins — {@code minecraft:gameplay/nether_portal_spawns_piglin}
 * @param increasedFireBurnout     whether fire burns out faster — {@code minecraft:gameplay/increased_fire_burnout}
 * @param piglinsZombify           whether piglins and hoglins zombify — {@code minecraft:gameplay/piglins_zombify}
 * @param snowGolemMelts           whether snow golems take damage — {@code minecraft:gameplay/snow_golem_melts}
 * @since 0.1.0
 */
public record EnvironmentAttributes(
        @Nullable Integer fogColor,
        @Nullable Integer skyColor,
        @Nullable Integer waterFogColor,
        @Nullable Float waterFogRadius,
        @Nullable Boolean extraFog,
        List<AmbientParticle> ambientParticles,
        @Nullable AmbientSounds ambientSounds,
        @Nullable BackgroundMusic backgroundMusic,
        @Nullable Float musicVolume,
        @Nullable Boolean canStartRaid,
        @Nullable Boolean waterEvaporates,
        @Nullable Boolean respawnAnchorWorks,
        @Nullable Boolean netherPortalSpawnsPiglin,
        @Nullable Boolean increasedFireBurnout,
        @Nullable Boolean piglinsZombify,
        @Nullable Boolean snowGolemMelts
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
        return java.awt.Color.HSBtoRGB(0.62222224F - scaled * 0.05F, 0.5F + scaled * 0.1F, 1.0F) & 0xFFFFFF;
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
        private @Nullable Integer fogColor;
        private @Nullable Integer skyColor;
        private @Nullable Integer waterFogColor;
        private @Nullable Float waterFogRadius;
        private @Nullable Boolean extraFog;
        private @Nullable AmbientSounds ambientSounds;
        private @Nullable BackgroundMusic backgroundMusic;
        private @Nullable Float musicVolume;
        private @Nullable Boolean canStartRaid;
        private @Nullable Boolean waterEvaporates;
        private @Nullable Boolean respawnAnchorWorks;
        private @Nullable Boolean netherPortalSpawnsPiglin;
        private @Nullable Boolean increasedFireBurnout;
        private @Nullable Boolean piglinsZombify;
        private @Nullable Boolean snowGolemMelts;

        private Builder() {
        }

        private Builder(final EnvironmentAttributes attributes) {
            this.fogColor = attributes.fogColor;
            this.skyColor = attributes.skyColor;
            this.waterFogColor = attributes.waterFogColor;
            this.waterFogRadius = attributes.waterFogRadius;
            this.extraFog = attributes.extraFog;
            this.ambientParticles.addAll(attributes.ambientParticles);
            this.ambientSounds = attributes.ambientSounds;
            this.backgroundMusic = attributes.backgroundMusic;
            this.musicVolume = attributes.musicVolume;
            this.canStartRaid = attributes.canStartRaid;
            this.waterEvaporates = attributes.waterEvaporates;
            this.respawnAnchorWorks = attributes.respawnAnchorWorks;
            this.netherPortalSpawnsPiglin = attributes.netherPortalSpawnsPiglin;
            this.increasedFireBurnout = attributes.increasedFireBurnout;
            this.piglinsZombify = attributes.piglinsZombify;
            this.snowGolemMelts = attributes.snowGolemMelts;
        }

        /**
         * @param color packed RGB color of the distance fog
         * @return this builder
         */
        @Contract("_ -> this")
        public Builder fogColor(final @Nullable Integer color) {
            this.fogColor = color;
            return this;
        }

        /**
         * @param color packed RGB color of the sky
         * @return this builder
         */
        @Contract("_ -> this")
        public Builder skyColor(final @Nullable Integer color) {
            this.skyColor = color;
            return this;
        }

        /**
         * @param color packed RGB color of the underwater fog
         * @return this builder
         */
        @Contract("_ -> this")
        public Builder waterFogColor(final @Nullable Integer color) {
            this.waterFogColor = color;
            return this;
        }

        /**
         * @param radius distance in blocks at which underwater fog reaches full density
         * @return this builder
         */
        @Contract("_ -> this")
        public Builder waterFogRadius(final @Nullable Float radius) {
            this.waterFogRadius = radius;
            return this;
        }

        /**
         * @param extraFog whether to use dense, Nether-like fog
         * @return this builder
         */
        @Contract("_ -> this")
        public Builder extraFog(final @Nullable Boolean extraFog) {
            this.extraFog = extraFog;
            return this;
        }

        /**
         * Adds one ambient particle.
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
         * @param sounds looping, mood and additions sounds, or {@code null} to leave unset
         * @return this builder
         */
        @Contract("_ -> this")
        public Builder ambientSounds(final @Nullable AmbientSounds sounds) {
            this.ambientSounds = sounds;
            return this;
        }

        /**
         * @param music background music tracks, or {@code null} to leave unset
         * @return this builder
         */
        @Contract("_ -> this")
        public Builder backgroundMusic(final @Nullable BackgroundMusic music) {
            this.backgroundMusic = music;
            return this;
        }

        /**
         * @param volume music volume within {@code [0, 1]}
         * @return this builder
         */
        @Contract("_ -> this")
        public Builder musicVolume(final @Nullable Float volume) {
            this.musicVolume = volume;
            return this;
        }

        /**
         * @param canStartRaid whether a raid can begin in this biome
         * @return this builder
         */
        @Contract("_ -> this")
        public Builder canStartRaid(final @Nullable Boolean canStartRaid) {
            this.canStartRaid = canStartRaid;
            return this;
        }

        /**
         * @param waterEvaporates whether placed water evaporates, as in the Nether
         * @return this builder
         */
        @Contract("_ -> this")
        public Builder waterEvaporates(final @Nullable Boolean waterEvaporates) {
            this.waterEvaporates = waterEvaporates;
            return this;
        }

        /**
         * @param respawnAnchorWorks whether respawn anchors set spawn instead of exploding
         * @return this builder
         */
        @Contract("_ -> this")
        public Builder respawnAnchorWorks(final @Nullable Boolean respawnAnchorWorks) {
            this.respawnAnchorWorks = respawnAnchorWorks;
            return this;
        }

        /**
         * @param netherPortalSpawnsPiglin whether nether portals spawn piglins
         * @return this builder
         */
        @Contract("_ -> this")
        public Builder netherPortalSpawnsPiglin(final @Nullable Boolean netherPortalSpawnsPiglin) {
            this.netherPortalSpawnsPiglin = netherPortalSpawnsPiglin;
            return this;
        }

        /**
         * @param increasedFireBurnout whether fire burns out faster than normal
         * @return this builder
         */
        @Contract("_ -> this")
        public Builder increasedFireBurnout(final @Nullable Boolean increasedFireBurnout) {
            this.increasedFireBurnout = increasedFireBurnout;
            return this;
        }

        /**
         * @param piglinsZombify whether piglins and hoglins zombify here
         * @return this builder
         */
        @Contract("_ -> this")
        public Builder piglinsZombify(final @Nullable Boolean piglinsZombify) {
            this.piglinsZombify = piglinsZombify;
            return this;
        }

        /**
         * @param snowGolemMelts whether snow golems take damage here
         * @return this builder
         */
        @Contract("_ -> this")
        public Builder snowGolemMelts(final @Nullable Boolean snowGolemMelts) {
            this.snowGolemMelts = snowGolemMelts;
            return this;
        }

        /**
         * {@return the immutable attributes described by this builder}
         */
        @Contract(value = "-> new", pure = true)
        public EnvironmentAttributes build() {
            return new EnvironmentAttributes(
                    fogColor,
                    skyColor,
                    waterFogColor,
                    waterFogRadius,
                    extraFog,
                    List.copyOf(ambientParticles),
                    ambientSounds,
                    backgroundMusic,
                    musicVolume,
                    canStartRaid,
                    waterEvaporates,
                    respawnAnchorWorks,
                    netherPortalSpawnsPiglin,
                    increasedFireBurnout,
                    piglinsZombify,
                    snowGolemMelts);
        }
    }
}
