package fr.euphyllia.fidorial.server.registry.item;

import fr.fidorial.item.ItemDefinition;
import net.kyori.adventure.key.Key;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Acts as a centralized registry for managing {@link ItemDefinition} instances within the system.
 * This class ensures item definitions can be registered and retrieved by a unique identifier.
 * It enforces constraints to prevent duplicate registrations and handles retrieval of unknown items gracefully.
 *
 * @since 0.1.0
 */
public final class ItemRegistry {

    private final Map<Key, ItemDefinition> items = new ConcurrentHashMap<>();

    /**
     * Registers an {@link ItemDefinition} instance in the registry. The item definition is
     * added to an internal map using its unique identifier as the key. If an item with the
     * same identifier is already registered, an exception is thrown to prevent duplicate entries.
     *
     * @param definition The {@link ItemDefinition} to register. Cannot be null.
     *                   Must have a unique identifier that is not already registered.
     * @throws NullPointerException     If the provided {@code definition} is null.
     * @throws IllegalArgumentException If an item with the same identifier is already registered.
     */
    public void register(final ItemDefinition definition) throws NullPointerException, IllegalArgumentException {
        Objects.requireNonNull(definition, "definition");

        final ItemDefinition previous = items.putIfAbsent(definition.id(), definition);
        if (previous != null) {
            throw new IllegalArgumentException("Item already registered: " + definition.id());
        }
    }

    /**
     * Retrieves an {@link ItemDefinition} associated with the specified identifier.
     * If no item definition exists for the given identifier, an exception is thrown.
     *
     * @param id The unique identifier of the item to retrieve. Cannot be null.
     * @return The {@link ItemDefinition} corresponding to the provided identifier.
     * @throws IllegalArgumentException If no item is found for the specified identifier.
     */
    public ItemDefinition get(final Key id) throws IllegalArgumentException {

        final ItemDefinition definition = items.get(id);
        if (definition == null) {
            throw new IllegalArgumentException("Unknown item: " + id.asString());
        }

        return definition;
    }

    /**
     * Checks whether the registry contains an {@link ItemDefinition} associated with the specified identifier.
     *
     * @param id The unique identifier of the item to check for existence. Cannot be null.
     * @return {@code true} if an {@link ItemDefinition} with the specified identifier exists in the registry;
     *         {@code false} otherwise.
     * @throws NullPointerException If the provided {@code id} is null.
     */
    public boolean contains(final Key id) {
        return items.containsKey(id);
    }
}