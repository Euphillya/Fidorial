package fr.fidorial.registrygen.model;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public record RegistryDefinition(String identifier,
                                 int protocolId,
                                 String defaultEntry,
                                 List<RegistryEntryDefinition> entries) {

    public RegistryDefinition {

        Objects.requireNonNull(identifier, "identifier");
        Objects.requireNonNull(entries, "entries");

        if (identifier.isBlank()) {
            throw new IllegalArgumentException("Registry identifier cannot be blank.");
        }

        if (protocolId < 0) {
            throw new IllegalArgumentException("Registry protocol ID cannot be negative.");
        }
        entries = List.copyOf(entries);
    }

    public Optional<String> defaultEntryOptional() {
        return Optional.ofNullable(defaultEntry);
    }
}