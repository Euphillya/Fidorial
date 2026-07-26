package fr.fidorial.registrygen.model;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Represents a holder for a collection of {@link RegistryDefinition} objects.
 * Provides functionality to manage and retrieve registries based on their identifiers.
 *
 * @since 0.1.0
 */
public record RegistriesHolder(List<RegistryDefinition> registries) {

    public RegistriesHolder {

        Objects.requireNonNull(registries, "registries");
        registries = List.copyOf(registries);
    }

    public Optional<RegistryDefinition> registry(final String identifier) {

        return registries.stream()
                .filter(registry ->registry.identifier().equals(identifier))
                .findFirst();
    }
}