package fr.euphyllia.fidorial.api.event.player;

import fr.euphyllia.fidorial.api.entity.Player;

public record PlayerQuitEvent(Player player) implements PlayerEvent {
}
