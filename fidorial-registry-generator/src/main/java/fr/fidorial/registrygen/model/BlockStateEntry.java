package fr.fidorial.registrygen.model;

import java.util.Map;

/**
 * A single reported block state: its network ID and property assignment.
 *
 * @param networkId  network ID for this exact state
 * @param properties property values for this state
 * @param isDefault  whether this is the block's default state
 *
 * @since 0.1.0
 */
public record BlockStateEntry(int networkId, Map<String, String> properties, boolean isDefault) {

    public BlockStateEntry {
        properties = Map.copyOf(properties);
    }
}
