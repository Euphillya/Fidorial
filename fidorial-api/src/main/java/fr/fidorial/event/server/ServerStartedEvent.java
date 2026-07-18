package fr.fidorial.event.server;

import fr.fidorial.Server;
import fr.fidorial.event.Event;

public record ServerStartedEvent(Server server) implements Event {
}
