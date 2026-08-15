package fr.fidorial.world.biome;

import fr.fidorial.registry.TypedKey;
import fr.fidorial.registry.data.Biome;
import net.kyori.adventure.key.Key;
import org.jetbrains.annotations.Contract;

import java.util.Collection;
import java.util.Optional;

/**
 * Server-wide registry of biomes, holding both the vanilla ones and those added by plugins.
 *
 * @since 0.1.0
 */
public interface BiomeRegistry {

    /**
     * Registers a new biome.
     *
     * @param definition the biome to add
     * @return the registered definition
     * @throws IllegalStateException if a biome is already registered under the same key; use
     *                               {@link #overwrite(BiomeDefinition)} to replace it on purpose
     * @since 0.1.0
     */
    @Contract("_ -> param1")
    BiomeDefinition register(BiomeDefinition definition);

    /**
     * Builds and registers a new biome.
     *
     * @param builder the builder describing the biome
     * @return the registered definition
     * @throws IllegalStateException if a biome is already registered under the same key
     * @since 0.1.0
     */
    default BiomeDefinition register(final BiomeBuilder builder) {
        return register(builder.build());
    }

    /**
     * Reads a biome from its data pack JSON representation and registers it.
     *
     * @param key  the key to register the biome under
     * @param json the JSON object describing the biome
     * @return the registered definition
     * @throws IllegalArgumentException if the JSON is malformed or misses a mandatory field
     * @throws IllegalStateException    if a biome is already registered under the same key
     * @since 0.1.0
     */
    BiomeDefinition registerFromJson(Key key, String json);

    /**
     * Registers a biome, replacing any existing one sharing its key.
     *
     * @param definition the biome to add or replace
     * @return the definition previously registered under that key, if any
     * @since 0.1.0
     */
    Optional<BiomeDefinition> overwrite(BiomeDefinition definition);

    /**
     * Removes a biome from the registry.
     *
     * @param key the key of the biome to remove
     * @return {@code true} if a biome was removed, {@code false} if none was registered
     * @throws IllegalArgumentException if {@code key} is the server's fallback biome, which must
     *                                  always stay registered
     * @since 0.1.0
     */
    boolean unregister(Key key);

    /**
     * Removes a biome from the registry.
     *
     * @param key the typed key of the biome to remove
     * @return {@code true} if a biome was removed, {@code false} if none was registered
     * @throws IllegalArgumentException if {@code key} is the server's fallback biome
     * @since 0.1.0
     */
    default boolean unregister(final TypedKey<Biome> key) {
        return unregister(key.key());
    }

    /**
     * {@return the definition registered under {@code key}, if that biome carries one}
     *
     * @param key the biome key
     * @since 0.1.0
     */
    Optional<BiomeDefinition> definition(Key key);

    /**
     * {@return whether a biome is registered under {@code key}}
     *
     * @param key the biome key
     * @since 0.1.0
     */
    boolean contains(Key key);

    /**
     * {@return whether {@code key} maps to a biome defined by this server rather than a stock
     * vanilla one}
     *
     * @param key the biome key
     * @since 0.1.0
     */
    boolean isCustom(Key key);

    /**
     * {@return every registered biome key, in network order}
     *
     * @since 0.1.0
     */
    @Contract(pure = true)
    Collection<Key> keys();

    /**
     * {@return every biome defined by this server, vanilla overrides included}
     *
     * @since 0.1.0
     */
    @Contract(pure = true)
    Collection<BiomeDefinition> definitions();

    /**
     * {@return the network identifier of {@code key}, or {@code -1} if it is not registered}
     *
     * @param key the biome key
     * @since 0.1.0
     */
    int networkId(Key key);

    /**
     * {@return the key of the biome used whenever a chunk references an unknown biome}
     *
     * @since 0.1.0
     */
    @Contract(pure = true)
    Key fallback();

    /**
     * {@return the number of registered biomes}
     *
     * @since 0.1.0
     */
    int totalRegistered();
}
