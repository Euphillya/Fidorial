package fr.fidorial.command;

import fr.fidorial.permission.PermissionHolder;
import net.kyori.adventure.audience.Audience;

/**
 * Represents an object that can be used to run {@link Commands}.
 * This is intentionally separated from {@link CommandSource} due to the possibility of it not matching the {@link CommandSender}
 * when the executor was changed using the {@literal /execute} command for example.
 */
public interface CommandSender extends Audience, PermissionHolder {

    String name();
}
