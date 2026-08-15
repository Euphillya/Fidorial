package fr.fidorial.world.biome;

import fr.fidorial.registry.RegistryKey;
import fr.fidorial.registry.TypedKey;
import fr.fidorial.registry.data.Biome;
import net.kyori.adventure.key.Key;
import org.jetbrains.annotations.Contract;

import java.util.Objects;

public record BiomeDefinition(
        Key key,
        boolean hasPrecipitation,
        float temperature,
        TemperatureModifier temperatureModifier,
        float downfall,
        BiomeEffects effects
) implements Biome {

    public BiomeDefinition {
        Objects.requireNonNull(key, "key");
        Objects.requireNonNull(temperatureModifier, "temperatureModifier");
        Objects.requireNonNull(effects, "effects");
        if (downfall < 0F || downfall > 1F) {
            throw new IllegalArgumentException("downfall must be within [0, 1], got " + downfall);
        }
    }

    /**
     * {@return a new builder for the biome identified by {@code key}}
     *
     * @param key the namespaced identifier of the biome
     */
    @Contract(value = "_ -> new", pure = true)
    public static BiomeBuilder builder(final Key key) {
        return new BiomeBuilder(key);
    }

    /**
     * {@return a new builder pre-filled with the values of {@code definition}}
     *
     * @param definition the definition to copy
     */
    @Contract(value = "_ -> new", pure = true)
    public static BiomeBuilder builder(final BiomeDefinition definition) {
        return new BiomeBuilder(definition);
    }

    /**
     * {@return this biome's key, typed against the {@code minecraft:worldgen/biome} registry}
     */
    @Contract(value = "-> new", pure = true)
    public TypedKey<Biome> typedKey() {
        return TypedKey.create(RegistryKey.BIOME, key);
    }
}
