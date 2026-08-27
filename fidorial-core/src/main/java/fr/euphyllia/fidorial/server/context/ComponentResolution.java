package fr.euphyllia.fidorial.server.context;

import net.kyori.adventure.text.Component;

public interface ComponentResolution {

    Component resolve(Component input, Object source);
}
