package fr.fidorial.event.player;

import fr.fidorial.entity.Player;

public record PlayerJoinEvent(Player player) implements PlayerEvent {
}
