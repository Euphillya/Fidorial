package fr.fidorial.event.player;

import fr.fidorial.entity.Player;

public record PlayerQuitEvent(Player player) implements PlayerEvent {
}
