package fr.fidorial.world.dimension;

import fr.fidorial.registry.TypedKey;
import fr.fidorial.registry.data.DimensionType;
import net.kyori.adventure.key.Key;
import org.jetbrains.annotations.Contract;

import java.util.Collection;
import java.util.Optional;

/**
 * Server-wide registry of dimension types, holding both the vanilla ones and those added by
 * plugins.
 *
 * @since 0.1.0
 */
public interface DimensionTypeRegistry {

    /**
     * Registers a new dimension type.
     *
     * @param definition the dimension type to add
     * @return the registered definition
     * @throws IllegalStateException if a dimension type is already registered under the same key;
     *                               use {@link #overwrite(DimensionTypeDefinition)} to replace it
     *                               on purpose
     * @since 0.1.0
     */
    @Contract("_ -> param1")
    DimensionTypeDefinition register(DimensionTypeDefinition definition);

    /**
     * Builds and registers a new dimension type.
     *
     * @param builder the builder describing the dimension type
     * @return the registered definition
     * @throws IllegalStateException if a dimension type is already registered under the same key
     * @since 0.1.0
     */
    default DimensionTypeDefinition register(final DimensionTypeBuilder builder) {
        return register(builder.build());
    }

    /**
     * Reads a dimension type from its data pack JSON representation and registers it.
     *
     * @param key  the key to register the dimension type under
     * @param json the JSON object describing the dimension type
     * @return the registered definition
     * @throws IllegalArgumentException if the JSON is malformed or misses a mandatory field
     * @throws IllegalStateException    if a dimension type is already registered under the same key
     * @since 0.1.0
     */
    DimensionTypeDefinition registerFromJson(Key key, String json);

    /**
     * Registers a dimension type, replacing any existing one sharing its key.
     *
     * @param definition the dimension type to add or replace
     * @return the definition previously registered under that key, if any
     * @since 0.1.0
     */
    Optional<DimensionTypeDefinition> overwrite(DimensionTypeDefinition definition);

    /**
     * Removes a dimension type from the registry.
     *
     * @param key the key of the dimension type to remove
     * @return {@code true} if a dimension type was removed, {@code false} if none was registered
     * @since 0.1.0
     */
    boolean unregister(Key key);

    /**
     * Removes a dimension type from the registry.
     *
     * @param key the typed key of the dimension type to remove
     * @return {@code true} if a dimension type was removed, {@code false} if none was registered
     * @since 0.1.0
     */
    default boolean unregister(final TypedKey<DimensionType> key) {
        return unregister(key.key());
    }

    /**
     * {@return the definition registered under {@code key}, if that dimension type carries one}
     *
     * @param key the dimension type key
     * @since 0.1.0
     */
    Optional<DimensionTypeDefinition> definition(Key key);

    /**
     * {@return whether a dimension type is registered under {@code key}}
     *
     * @param key the dimension type key
     * @since 0.1.0
     */
    boolean contains(Key key);

    /**
     * {@return whether {@code key} maps to a dimension type defined by this server rather than a
     * stock vanilla one}
     *
     * @param key the dimension type key
     * @since 0.1.0
     */
    boolean isCustom(Key key);

    /**
     * {@return every registered dimension type key, in network order}
     *
     * @since 0.1.0
     */
    @Contract(pure = true)
    Collection<Key> keys();

    /**
     * {@return every dimension type defined by this server, vanilla overrides included}
     *
     * @since 0.1.0
     */
    @Contract(pure = true)
    Collection<DimensionTypeDefinition> definitions();

    /**
     * {@return the network identifier of {@code key}, or {@code -1} if it is not registered}
     *
     * @param key the dimension type key
     * @since 0.1.0
     */
    int networkId(Key key);

    /**
     * {@return the number of registered dimension types}
     *
     * @since 0.1.0
     */
    int totalRegistered();
}
