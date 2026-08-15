package fr.fidorial.world.biome;

import fr.fidorial.world.environment.EnvironmentAttributes;
import net.kyori.adventure.key.Key;
import org.jetbrains.annotations.Contract;

import java.util.Objects;
import java.util.function.Consumer;

public class BiomeBuilder {

    private Key key;
    private boolean hasPrecipitation = true;
    private float temperature = 0.8F;
    private TemperatureModifier temperatureModifier = TemperatureModifier.NONE;
    private float downfall = 0.4F;
    private BiomeEffects effects = BiomeEffects.DEFAULT;

    BiomeBuilder(final Key key) {
        this.key = Objects.requireNonNull(key, "key");
    }

    BiomeBuilder(final BiomeDefinition definition) {
        this.key = definition.key();
        this.hasPrecipitation = definition.hasPrecipitation();
        this.temperature = definition.temperature();
        this.temperatureModifier = definition.temperatureModifier();
        this.downfall = definition.downfall();
        this.effects = definition.effects();
    }

    /**
     * @param key the namespaced identifier of the biome
     * @return this builder
     */
    @Contract("_ -> this")
    public BiomeBuilder key(final Key key) {
        this.key = Objects.requireNonNull(key, "key");
        return this;
    }

    /**
     * @param hasPrecipitation whether rain or snow may fall in the biome
     * @return this builder
     */
    @Contract("_ -> this")
    public BiomeBuilder hasPrecipitation(final boolean hasPrecipitation) {
        this.hasPrecipitation = hasPrecipitation;
        return this;
    }

    /**
     * Sets the biome temperature.
     *
     * <p>Below {@code 0.15} water freezes and precipitation falls as snow; above {@code 1.0} the
     * client stops rendering rain entirely.</p>
     *
     * @param temperature the biome temperature
     * @return this builder
     */
    @Contract("_ -> this")
    public BiomeBuilder temperature(final float temperature) {
        this.temperature = temperature;
        return this;
    }

    /**
     * @param modifier how the client reinterprets the temperature
     * @return this builder
     */
    @Contract("_ -> this")
    public BiomeBuilder temperatureModifier(final TemperatureModifier modifier) {
        this.temperatureModifier = Objects.requireNonNull(modifier, "modifier");
        return this;
    }

    /**
     * @param downfall humidity within {@code [0, 1]}
     * @return this builder
     */
    @Contract("_ -> this")
    public BiomeBuilder downfall(final float downfall) {
        this.downfall = downfall;
        return this;
    }

    /**
     * @param effects the client-side effects
     * @return this builder
     */
    @Contract("_ -> this")
    public BiomeBuilder effects(final BiomeEffects effects) {
        this.effects = Objects.requireNonNull(effects, "effects");
        return this;
    }

    /**
     * Configures the effects in place, starting from the ones currently set.
     *
     * @param configurer callback receiving a pre-filled effects builder
     * @return this builder
     */
    @Contract("_ -> this")
    public BiomeBuilder effects(final Consumer<BiomeEffects.Builder> configurer) {
        final BiomeEffects.Builder builder = BiomeEffects.builder(this.effects);
        configurer.accept(builder);
        this.effects = builder.build();
        return this;
    }

    /**
     * Sets the sky color to the value vanilla would derive from the current temperature.
     *
     * <p>Call this after {@link #temperature(float)}.</p>
     *
     * @return this builder
     */
    @Contract("-> this")
    public BiomeBuilder vanillaSkyColor() {
        return effects(effects -> effects.skyColor(EnvironmentAttributes.skyColorFor(this.temperature)));
    }

    /**
     * {@return the immutable definition described by this builder}
     */
    @Contract(value = "-> new", pure = true)
    public BiomeDefinition build() {
        return new BiomeDefinition(key, hasPrecipitation, temperature, temperatureModifier, downfall, effects);
    }
}