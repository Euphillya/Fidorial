package fr.fidorial.registrygen.model;

import java.util.Objects;

/**
 * Represents the definition for a specific type of registry.
 * A registry type is described using an identifier, a type name,
 * and the name of the class responsible for handling keys associated with the registry.
 *
 * @since 0.1.0
 */
public record RegistryTypeDefinition(String identifier, String typeName, String keysClassName) {

  public RegistryTypeDefinition {
    Objects.requireNonNull(identifier, "identifier");
    Objects.requireNonNull(typeName, "typeName");
    Objects.requireNonNull(keysClassName, "keysClassName");
  }

  public String path() {
    final int separator = identifier.indexOf(':');

    return separator >= 0? identifier.substring(separator + 1) : identifier;
  }
}