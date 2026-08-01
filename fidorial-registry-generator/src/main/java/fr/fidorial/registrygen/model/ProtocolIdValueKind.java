package fr.fidorial.registrygen.model;

public enum ProtocolIdValueKind {
    /** Field value is {@link RegistryEntryDefinition#protocolId()}, as an {@code int}. */
    PROTOCOL_ID,
    /** Field value is {@link RegistryEntryDefinition#identifier()}, as a {@code String}. */
    IDENTIFIER
}
