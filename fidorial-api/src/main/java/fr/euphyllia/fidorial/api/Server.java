package fr.euphyllia.fidorial.api;

import fr.euphyllia.fidorial.api.entity.Player;
import fr.euphyllia.fidorial.api.event.EventBus;
import fr.euphyllia.fidorial.api.plugin.PluginManager;
import fr.euphyllia.fidorial.api.registry.Key;
import fr.euphyllia.fidorial.api.scheduler.RegionizedScheduler;
import fr.euphyllia.fidorial.api.service.ServiceRegistry;
import fr.euphyllia.fidorial.api.world.World;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

public interface Server {

    String minecraftVersion();

    int protocolVersion();

    RegionizedScheduler scheduler();

    EventBus events();

    ServiceRegistry services();

    PluginManager plugins();

    Collection<? extends World> worlds();

    Optional<? extends World> world(Key key);

    Collection<? extends Player> onlinePlayers();

    Optional<? extends Player> player(UUID uuid);

    Optional<? extends Player> player(String name);

    boolean isRunning();

    void shutdown();
}
