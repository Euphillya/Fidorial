package fr.euphyllia.fidorial.server.chat;

import net.kyori.adventure.text.minimessage.MiniMessage;

public record MiniText() {

    private static final MiniMessage MINI_MESSAGE = MiniMessage.miniMessage();

    public static MiniMessage miniMessage() {
        return MINI_MESSAGE;
    }
}
