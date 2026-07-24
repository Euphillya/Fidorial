package fr.fidorial.command;

import fr.fidorial.permission.Permissible;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;

/**
 * Represents an object that can be used to run {@link CommandTree}.
 * This is intentionally separated from {@link CommandSource}
 */
public interface CommandSender extends Audience, Permissible {

    String name();
}
