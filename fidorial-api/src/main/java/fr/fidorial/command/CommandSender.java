package fr.fidorial.command;

import fr.fidorial.permission.Permissible;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.jetbrains.annotations.NotNull;

/**
 * Represents an object that can be used to run {@link CommandTree}.
 */
public interface CommandSender extends Audience, Permissible {

    /**
     * Sends a message with the MiniMessage format to this source.
     *
     * @param message MiniMessage content
     **/
    default void sendRichMessage(final @NotNull String message) {
        this.sendMessage(MiniMessage.miniMessage().deserialize(message, this));
    }

    /**
     * Sends a message with the MiniMessage format to this source.
     *
     * @param message MiniMessage content
     * @param resolvers resolvers to use
     */
    default void sendRichMessage(final @NotNull String message, final @NotNull TagResolver @NotNull ... resolvers) {
        this.sendMessage(MiniMessage.miniMessage().deserialize(message, this, resolvers));
    }

    String name();
}
