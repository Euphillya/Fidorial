package fr.fidorial.registrygen.model;

import java.util.Objects;

public record RegistryEntryDefinition(String identifier, int protocolId) {

    public RegistryEntryDefinition {

        Objects.requireNonNull(identifier, "identifier");

        if (identifier.isBlank()) {
            throw new IllegalArgumentException("Registry entry identifier cannot be blank.");
        }

        if (protocolId < 0) {
            throw new IllegalArgumentException("Registry entry protocol ID cannot be negative.");
        }
    }
}