package fr.fidorial.registrygen.model;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

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