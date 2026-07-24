package fr.fidorial.command;

import fr.fidorial.permission.PermissionHolder;
import net.kyori.adventure.audience.Audience;

/**
 * Represents an object that can be used to run {@link CommandTree}.
 * This is intentionally separated from {@link CommandSource}
 */
public interface CommandSender extends Audience, PermissionHolder {

    String name();
}
