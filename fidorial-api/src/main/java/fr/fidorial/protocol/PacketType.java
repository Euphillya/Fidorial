package fr.fidorial.protocol;

import net.kyori.adventure.key.Key;

import java.util.Objects;

/**
 * Stably identifies a packet type, independently of its numeric id (which varies
 * between versions). A {@code PacketType} is the combination of a
 * {@link ConnectionPhase phase}, a {@link PacketDirection direction} and a resource
 * key (e.g. {@code minecraft:keep_alive}).
 *
 * @param phase     connection phase this packet belongs to
 * @param direction travel direction of the packet
 * @param key       canonical resource key of the packet (namespace included)
 */
public record PacketType(ConnectionPhase phase, PacketDirection direction, Key key) {

    public PacketType {
        Objects.requireNonNull(phase, "phase");
        Objects.requireNonNull(direction, "direction");
        Objects.requireNonNull(key, "key");
    }

    /**
     * Creates a {@code PacketType} from a key given as a string.
     *
     * @param phase     connection phase
     * @param direction travel direction
     * @param key       resource key (e.g. {@code "minecraft:keep_alive"})
     * @return the matching packet type
     */
    public static PacketType of(ConnectionPhase phase, PacketDirection direction, String key) {
        return new PacketType(phase, direction, Key.key(key));
    }

    /**
     * @return the resource key as {@code namespace:path}.
     */
    public String asString() {
        return key.asString();
    }

    @Override
    public String toString() {
        return phase + "/" + direction + "/" + key.asString();
    }
}
