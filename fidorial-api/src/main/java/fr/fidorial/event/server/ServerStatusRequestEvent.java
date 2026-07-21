package fr.fidorial.event.server;

import fr.fidorial.event.Event;
import fr.fidorial.status.ServerStatus;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Contract;


@ApiStatus.NonExtendable
public interface ServerStatusRequestEvent extends Event {
    // todo: expose client connection that requested the status

    @Contract(pure = true)
    ServerStatus status();

    @Contract(mutates = "this")
    void status(final ServerStatus status);
}
