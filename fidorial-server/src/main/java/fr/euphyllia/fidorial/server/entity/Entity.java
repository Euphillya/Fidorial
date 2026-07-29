package fr.euphyllia.fidorial.server.entity;

import net.kyori.adventure.sound.Sound;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.event.HoverEventSource;

public interface Entity extends HoverEventSource<HoverEvent.ShowEntity>, Sound.Emitter, Sound.Source.Provider {
}
