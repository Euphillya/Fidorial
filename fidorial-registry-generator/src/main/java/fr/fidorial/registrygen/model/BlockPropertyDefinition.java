package fr.fidorial.registrygen.model;

import java.util.List;

/**
 * A single property of a block, and every value it may take.
 *
 * @param name   property name, e.g. {@code rotation}
 * @param values values for the property, in report order
 *
 * @since 0.1.0
 */
public record BlockPropertyDefinition(String name, List<String> values) {

    public BlockPropertyDefinition {
        values = List.copyOf(values);
    }
}
