package fr.euphyllia.fidorial.server.events;

import fr.fidorial.event.server.ServerStatusRequestEvent;
import fr.fidorial.status.ServerStatus;

public final class ServerStatusRequestEventImpl implements ServerStatusRequestEvent {
    private ServerStatus status;

    public ServerStatusRequestEventImpl(final ServerStatus status) {
        this.status = status;
    }

    @Override
    public ServerStatus status() {
        return status;
    }

    @Override
    public void status(ServerStatus status) {
        this.status = status;
    }
}
