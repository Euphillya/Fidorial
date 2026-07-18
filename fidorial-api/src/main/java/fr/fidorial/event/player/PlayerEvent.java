package fr.fidorial.event.player;

import fr.fidorial.entity.Player;
import fr.fidorial.event.Event;

public interface PlayerEvent extends Event {

    Player player();
}
