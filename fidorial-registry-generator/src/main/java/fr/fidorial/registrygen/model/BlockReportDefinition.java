package fr.fidorial.registrygen.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * A parsed entry from Mojang's {@code reports/blocks.json}.
 *
 * @param identifier namespaced block identifier, e.g. {@code minecraft:zombie_head}
 * @param properties block properties, in report order
 * @param states     every state permutation for the block
 *
 * @since 0.1.0
 */
public record BlockReportDefinition(String identifier,
                                    List<BlockPropertyDefinition> properties,
                                    List<BlockStateEntry> states) {

    public BlockReportDefinition {
        properties = List.copyOf(properties);
        states = List.copyOf(states);
    }

    public int ordinalOf(final BlockStateEntry state) {

        int ordinal = 0;
        for (final BlockPropertyDefinition property : properties) {

            final String value = state.properties().get(property.name());
            final int index = property.values().indexOf(value);

            if (index < 0) {
                throw new IllegalStateException("State " + state.networkId() + " of block '" + identifier
                        + "' has an invalid or missing value for property '" + property.name() + "'.");
            }

            ordinal = ordinal * property.values().size() + index;
        }
        return ordinal;
    }

    public int[] stateIdsInOrder() {

        int expected = 1;
        for (final BlockPropertyDefinition property : properties) {
            expected *= property.values().size();
        }

        final int[] ids = new int[expected];
        final boolean[] filled = new boolean[expected];

        for (final BlockStateEntry state : states) {
            final int ordinal = ordinalOf(state);
            ids[ordinal] = state.networkId();
            filled[ordinal] = true;
        }

        for (int ordinal = 0; ordinal < filled.length; ordinal++) {
            if (!filled[ordinal]) {
                throw new IllegalStateException("Missing state permutation " + ordinal + " for block '" + identifier + "'.");
            }
        }

        return ids;
    }

    public List<Map<String, String>> statePropertiesInOrder() {

        int expected = 1;
        for (final BlockPropertyDefinition property : properties) {
            expected *= property.values().size();
        }

        final List<Map<String, String>> result = new ArrayList<>(Collections.nCopies(expected, null));
        final boolean[] filled = new boolean[expected];

        for (final BlockStateEntry state : states) {
            final int ordinal = ordinalOf(state);
            result.set(ordinal, state.properties());
            filled[ordinal] = true;
        }

        for (int ordinal = 0; ordinal < filled.length; ordinal++) {
            if (!filled[ordinal]) {
                throw new IllegalStateException("Missing state permutation " + ordinal + " for block '" + identifier + "'.");
            }
        }

        return result;
    }

    /**
     * Returns the ordinal of the state, marked as default.
     *
     * @return the default ordinal, or {@code 0} if none was marked
     */
    public int defaultOrdinal() {

        for (final BlockStateEntry state : states) {
            if (state.isDefault()) {
                return ordinalOf(state);
            }
        }
        return 0;
    }
}
