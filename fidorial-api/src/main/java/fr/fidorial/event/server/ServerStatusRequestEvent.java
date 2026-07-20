package fr.fidorial.event.server;

import fr.fidorial.event.Event;
import fr.fidorial.status.ServerStatus;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Contract;

public class ServerStatusRequestEvent implements Event {
    private boolean cancelled;
    private ServerStatus status;
    // todo: expose client connection that requested the status

    @ApiStatus.Internal
    public ServerStatusRequestEvent(ServerStatus status) {
        this.status = status;
    }

    @Contract(pure = true)
    public ServerStatus getStatus() {
        return status;
    }

    @Contract(mutates = "this")
    public void setStatus(ServerStatus status) {
        this.status = status;
    }
}
