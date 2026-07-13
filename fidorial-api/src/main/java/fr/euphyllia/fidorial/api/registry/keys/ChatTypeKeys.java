package fr.euphyllia.fidorial.api.registry.keys;

import fr.euphyllia.fidorial.api.registry.Key;
import fr.euphyllia.fidorial.api.registry.RegistryKey;
import fr.euphyllia.fidorial.api.registry.TypedKey;
import fr.euphyllia.fidorial.api.registry.data.ChatType;

/**
 * Generated {@link TypedKey} constants for the {@code minecraft:chat_type} registry.
 */
public final class ChatTypeKeys {

    public static final TypedKey<ChatType> CHAT = create("chat");
    public static final TypedKey<ChatType> EMOTE_COMMAND = create("emote_command");
    public static final TypedKey<ChatType> MSG_COMMAND_INCOMING = create("msg_command_incoming");
    public static final TypedKey<ChatType> MSG_COMMAND_OUTGOING = create("msg_command_outgoing");
    public static final TypedKey<ChatType> SAY_COMMAND = create("say_command");
    public static final TypedKey<ChatType> TEAM_MSG_COMMAND_INCOMING = create("team_msg_command_incoming");
    public static final TypedKey<ChatType> TEAM_MSG_COMMAND_OUTGOING = create("team_msg_command_outgoing");

    private ChatTypeKeys() {
    }

    private static TypedKey<ChatType> create(String value) {
        return TypedKey.create(RegistryKey.CHAT_TYPE, Key.minecraft(value));
    }
}
