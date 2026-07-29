package fr.fidorial.registrygen.model;

import java.util.Objects;

/**
 * Describes a registry whose entries must be exposed as raw network
 * (protocol) identifiers instead of, or in addition to, typed keys.
 *
 * <p>Some registries are never manipulated as {@code TypedKey} instances by the
 * server: they only ever appear on the wire as a {@code VarInt}. The
 * {@code minecraft:command_argument_type} registry is one such case, and
 * {@code minecraft:block_entity_type} is another &mdash; the
 * {@code level_chunk_with_light} packet writes the block entity type as a bare
 * protocol ID.</p>
 *
 * @param registryIdentifier namespaced registry identifier, e.g. {@code minecraft:block_entity_type}
 * @param packageName        package of the generated constant holder
 * @param className          simple name of the generated constant holder
 * @param constantSuffix     suffix appended to every generated constant name
 *
 * @since 0.1.0
 */
public record ProtocolIdTarget(String registryIdentifier,
                               String packageName,
                               String className,
                               String constantSuffix) {

    public ProtocolIdTarget {

        Objects.requireNonNull(registryIdentifier, "registryIdentifier");
        Objects.requireNonNull(packageName, "packageName");
        Objects.requireNonNull(className, "className");
        Objects.requireNonNull(constantSuffix, "constantSuffix");

        if (registryIdentifier.isBlank()) {
            throw new IllegalArgumentException("Protocol ID target registry identifier cannot be blank.");
        }

        if (packageName.isBlank()) {
            throw new IllegalArgumentException("Protocol ID target package cannot be blank.");
        }

        if (className.isBlank()) {
            throw new IllegalArgumentException("Protocol ID target class name cannot be blank.");
        }
    }
}
