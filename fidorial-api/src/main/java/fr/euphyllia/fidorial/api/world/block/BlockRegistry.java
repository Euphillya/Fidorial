package fr.euphyllia.fidorial.api.world.block;

import fr.euphyllia.fidorial.api.registry.Key;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

public interface BlockRegistry {

    Optional<BlockType> type(Key key);

    default Optional<BlockType> type(String key) {
        return type(Key.parse(key));
    }

    BlockData fromNetworkId(int networkId);

    void register(BlockType type);

    Collection<BlockType> types();

    default BlockData parse(String input) {
        String name = input;
        Map<String, String> values = Map.of();
        int bracket = input.indexOf('[');
        if (bracket >= 0) {
            if (!input.endsWith("]")) {
                throw new IllegalArgumentException("Missing closing ']' in '" + input + "'");
            }
            name = input.substring(0, bracket);
            values = new LinkedHashMap<>();
            String body = input.substring(bracket + 1, input.length() - 1);
            if (!body.isEmpty()) {
                for (String pair : body.split(",")) {
                    int eq = pair.indexOf('=');
                    if (eq < 0) {
                        throw new IllegalArgumentException("Invalid property '" + pair + "' in '" + input + "'");
                    }
                    values.put(pair.substring(0, eq).trim(), pair.substring(eq + 1).trim());
                }
            }
        }
        BlockType type = type(name).orElse(null);
        return type == null ? null : type.data(values);
    }
}
