package fr.fidorial.registry.keys;

import fr.fidorial.registry.RegistryKey;
import fr.fidorial.registry.TypedKey;
import fr.fidorial.registry.data.ChatType;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.key.KeyPattern;

/**
 * Typed keys for entries in the {@code minecraft:chat_type} registry.
 */
public final class ChatTypeKeys {
    /**
     * Key for {@code minecraft:chat}.
     */
    public static final TypedKey<ChatType> CHAT = create("chat");

    /**
     * Key for {@code minecraft:emote_command}.
     */
    public static final TypedKey<ChatType> EMOTE_COMMAND = create("emote_command");

    /**
     * Key for {@code minecraft:msg_command_incoming}.
     */
    public static final TypedKey<ChatType> MSG_COMMAND_INCOMING = create("msg_command_incoming");

    /**
     * Key for {@code minecraft:msg_command_outgoing}.
     */
    public static final TypedKey<ChatType> MSG_COMMAND_OUTGOING = create("msg_command_outgoing");

    /**
     * Key for {@code minecraft:say_command}.
     */
    public static final TypedKey<ChatType> SAY_COMMAND = create("say_command");

    /**
     * Key for {@code minecraft:team_msg_command_incoming}.
     */
    public static final TypedKey<ChatType> TEAM_MSG_COMMAND_INCOMING = create("team_msg_command_incoming");

    /**
     * Key for {@code minecraft:team_msg_command_outgoing}.
     */
    public static final TypedKey<ChatType> TEAM_MSG_COMMAND_OUTGOING = create("team_msg_command_outgoing");

    /**
     * Entries in ascending {@code protocol_id} order - list index == network ID.
     */
    private static final List<TypedKey<ChatType>> VALUES = List.of(
        CHAT,
        EMOTE_COMMAND,
        MSG_COMMAND_INCOMING,
        MSG_COMMAND_OUTGOING,
        SAY_COMMAND,
        TEAM_MSG_COMMAND_INCOMING,
        TEAM_MSG_COMMAND_OUTGOING
    );

    private ChatTypeKeys() {
        throw new UnsupportedOperationException("ChatTypeKeys cannot be instantiated.");
    }

    private static TypedKey<ChatType> create(@KeyPattern final String value) {
        return TypedKey.create(RegistryKey.CHAT_TYPE, value);
    }

    /**
     * Returns a stream containing all keys declared by this class.
     *
     * @return a stream of registry keys
     */
    public static Stream<TypedKey<ChatType>> values() {
        return VALUES.stream();
    }

    /**
     * Returns this registry's tags (namespaced tag identifier to member entries).
     *
     * @return an immutable map of tags, or an empty map if this registry defines none
     */
    public static Map<Key, List<Key>> tags() {
        return Map.of();
    }
}
