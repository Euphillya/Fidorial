package fr.fidorial.command;

import fr.fidorial.Server;
import fr.fidorial.entity.Entity;
import fr.fidorial.world.Location;
import org.jspecify.annotations.Nullable;

public interface CommandSource {
    /**
     * Gets the location that this command is being executed at.
     *
     * @return a cloned location instance.
     */
    Location location();

    /**
     * Gets the command sender that executed this command.
     * The sender of a command source stack is the one that initiated/triggered the execution of a command.
     * It differs to {@link #executor()} as the executor can be changed by a command, e.g. {@literal /execute}.
     *
     * @return the command sender instance
     */
    CommandSender sender();

    /**
     * Gets the entity that executes this command.
     * May not always be {@link #sender()} as the executor of a command can be changed to a different entity
     * than the one that triggered the command.
     *
     * @return entity that executes this command
     */
    @Nullable Entity executor();

    /**
     * Gets the server associated with this source.
     *
     * @return the server
     */
    Server server();
}
