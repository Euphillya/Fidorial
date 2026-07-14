package fr.euphyllia.fidorial.api.event.server;

import fr.euphyllia.fidorial.api.Server;
import fr.euphyllia.fidorial.api.event.Event;

public record ServerStoppingEvent(Server server) implements Event {
}
