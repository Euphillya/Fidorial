package fr.fidorial.world.biome;

import fr.fidorial.world.environment.AmbientParticle;
import fr.fidorial.world.environment.AmbientSounds;
import fr.fidorial.world.environment.BackgroundMusic;
import fr.fidorial.world.environment.EnvironmentAttributes;
import org.jetbrains.annotations.Contract;
import org.jspecify.annotations.Nullable;

import java.util.Objects;
import java.util.function.Consumer;

public record BiomeEffects(
        int waterColor,
        @Nullable Integer foliageColor,
        @Nullable Integer grassColor,
        @Nullable Integer dryFoliageColor,
        GrassColorModifier grassColorModifier,
        EnvironmentAttributes attributes
) {

    /**
     * Overworld temperate defaults, matching {@code minecraft:plains}.
     */
    public static final BiomeEffects DEFAULT = builder().build();

    public BiomeEffects {
        Objects.requireNonNull(grassColorModifier, "grassColorModifier");
        Objects.requireNonNull(attributes, "attributes");
    }

    /**
     * {@return a new builder pre-filled with vanilla overworld values}
     */
    @Contract(value = "-> new", pure = true)
    public static Builder builder() {
        return new Builder();
    }

    /**
     * {@return a new builder pre-filled with the values of {@code effects}}
     *
     * @param effects the effects to copy
     */
    @Contract(value = "_ -> new", pure = true)
    public static Builder builder(final BiomeEffects effects) {
        return new Builder(effects);
    }

    /**
     * Computes the sky color the vanilla generator derives from a given temperature.
     *
     * @param temperature the biome temperature
     * @return the packed RGB sky color
     * @deprecated moved alongside the attribute it feeds; use
     * {@link EnvironmentAttributes#skyColorFor(float)}
     */
    @Deprecated(since = "0.1.0", forRemoval = true)
    public static int skyColorFor(final float temperature) {
        return EnvironmentAttributes.skyColorFor(temperature);
    }

    /**
     * Mutable builder for {@link BiomeEffects}.
     *
     * <p>Beyond the tints it owns, the builder forwards the most frequently used environment
     * attributes to an inner {@link EnvironmentAttributes.Builder}, reachable in full through
     * {@link #attributes(Consumer)}.</p>
     *
     * @since 0.1.0
     */
    public static final class Builder {

        private int waterColor = 0x3F76E4;
        private @Nullable Integer foliageColor;
        private @Nullable Integer grassColor;
        private @Nullable Integer dryFoliageColor;
        private GrassColorModifier grassColorModifier = GrassColorModifier.NONE;
        private EnvironmentAttributes.Builder attributes = EnvironmentAttributes.builder();

        private Builder() {
        }

        private Builder(final BiomeEffects effects) {
            this.waterColor = effects.waterColor;
            this.foliageColor = effects.foliageColor;
            this.grassColor = effects.grassColor;
            this.dryFoliageColor = effects.dryFoliageColor;
            this.grassColorModifier = effects.grassColorModifier;
            this.attributes = EnvironmentAttributes.builder(effects.attributes);
        }

        /**
         * @param color packed RGB tint applied to water
         * @return this builder
         */
        @Contract("_ -> this")
        public Builder waterColor(final int color) {
            this.waterColor = color;
            return this;
        }

        /**
         * @param color packed RGB leaf tint, or {@code null} to let the client compute it
         * @return this builder
         */
        @Contract("_ -> this")
        public Builder foliageColor(final @Nullable Integer color) {
            this.foliageColor = color;
            return this;
        }

        /**
         * @param color packed RGB grass tint, or {@code null} to let the client compute it
         * @return this builder
         */
        @Contract("_ -> this")
        public Builder grassColor(final @Nullable Integer color) {
            this.grassColor = color;
            return this;
        }

        /**
         * @param color packed RGB leaf litter tint, or {@code null} for the default
         * @return this builder
         */
        @Contract("_ -> this")
        public Builder dryFoliageColor(final @Nullable Integer color) {
            this.dryFoliageColor = color;
            return this;
        }

        /**
         * @param modifier post-processing applied on top of the grass color
         * @return this builder
         */
        @Contract("_ -> this")
        public Builder grassColorModifier(final GrassColorModifier modifier) {
            this.grassColorModifier = Objects.requireNonNull(modifier, "modifier");
            return this;
        }

        /**
         * Replaces the whole attribute map.
         *
         * @param attributes the attributes this biome sets
         * @return this builder
         */
        @Contract("_ -> this")
        public Builder attributes(final EnvironmentAttributes attributes) {
            this.attributes = EnvironmentAttributes.builder(attributes);
            return this;
        }

        /**
         * Configures the attribute map in place, starting from what is already set.
         *
         * <p>This is the way to reach attributes the shorthands below do not cover.</p>
         *
         * @param configurer callback receiving the attribute builder
         * @return this builder
         */
        @Contract("_ -> this")
        public Builder attributes(final Consumer<EnvironmentAttributes.Builder> configurer) {
            configurer.accept(this.attributes);
            return this;
        }

        /**
         * Shorthand for {@code minecraft:visual/sky_color}.
         *
         * @param color packed RGB color of the sky
         * @return this builder
         */
        @Contract("_ -> this")
        public Builder skyColor(final @Nullable Integer color) {
            this.attributes.skyColor(color);
            return this;
        }

        /**
         * Shorthand for {@code minecraft:visual/fog_color}.
         *
         * @param color packed RGB color of the distance fog
         * @return this builder
         */
        @Contract("_ -> this")
        public Builder fogColor(final @Nullable Integer color) {
            this.attributes.fogColor(color);
            return this;
        }

        /**
         * Shorthand for {@code minecraft:visual/water_fog_color}.
         *
         * @param color packed RGB color of the underwater fog
         * @return this builder
         */
        @Contract("_ -> this")
        public Builder waterFogColor(final @Nullable Integer color) {
            this.attributes.waterFogColor(color);
            return this;
        }

        /**
         * Shorthand for {@code minecraft:visual/ambient_particles}.
         *
         * @param particle the particle to add
         * @return this builder
         */
        @Contract("_ -> this")
        public Builder addAmbientParticle(final AmbientParticle particle) {
            this.attributes.addAmbientParticle(particle);
            return this;
        }

        /**
         * Shorthand for {@code minecraft:audio/ambient_sounds}.
         *
         * @param sounds the ambient sounds, or {@code null} to leave unset
         * @return this builder
         */
        @Contract("_ -> this")
        public Builder ambientSounds(final @Nullable AmbientSounds sounds) {
            this.attributes.ambientSounds(sounds);
            return this;
        }

        /**
         * Shorthand for {@code minecraft:audio/background_music}.
         *
         * @param music the background music, or {@code null} to leave unset
         * @return this builder
         */
        @Contract("_ -> this")
        public Builder backgroundMusic(final @Nullable BackgroundMusic music) {
            this.attributes.backgroundMusic(music);
            return this;
        }

        /**
         * {@return the immutable effects described by this builder}
         */
        @Contract(value = "-> new", pure = true)
        public BiomeEffects build() {
            return new BiomeEffects(
                    waterColor,
                    foliageColor,
                    grassColor,
                    dryFoliageColor,
                    grassColorModifier,
                    attributes.build());
        }
    }
}
