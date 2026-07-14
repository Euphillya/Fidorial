package fr.euphyllia.fidorial.api.event.server;

import fr.euphyllia.fidorial.api.Server;
import fr.euphyllia.fidorial.api.event.Event;

public record ServerStartedEvent(Server server) implements Event {
}
