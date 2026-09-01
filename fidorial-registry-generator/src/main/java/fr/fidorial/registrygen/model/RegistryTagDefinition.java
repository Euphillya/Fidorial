package fr.fidorial.registrygen.model;

import java.util.List;
import java.util.Objects;

/**
 * Represents a single, fully-resolved registry tag: a namespaced tag identifier
 * and the flat list of registry entry identifiers it contains.
 *
 * @since 0.1.0
 */
public record RegistryTagDefinition(String identifier, List<String> entries) {

    public RegistryTagDefinition {

        Objects.requireNonNull(identifier, "identifier");
        Objects.requireNonNull(entries, "entries");

        if (identifier.isBlank()) {
            throw new IllegalArgumentException("Tag identifier cannot be blank.");
        }

        entries = List.copyOf(entries);
    }
}
