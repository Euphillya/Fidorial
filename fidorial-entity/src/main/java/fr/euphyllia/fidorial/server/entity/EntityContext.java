package fr.euphyllia.fidorial.server.entity;

import fr.euphyllia.fidorial.server.context.ServerContext;
import fr.euphyllia.fidorial.server.entity.mob.FidorialMobRegistry;
import fr.euphyllia.fidorial.server.entity.player.ServerPlayer;
import fr.euphyllia.fidorial.server.entity.player.profile.FidorialOfflinePlayers;
import fr.euphyllia.fidorial.server.world.WorldContext;
import fr.fidorial.entity.Player;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface EntityContext extends WorldContext {

    List<ServerPlayer> players();

    Collection<? extends Player> onlinePlayers();

    Optional<? extends Player> player(UUID uuid);

    Optional<? extends Player> player(String name);

    FidorialOfflinePlayers offlinePlayers();

    FidorialMobRegistry mobs();

    EntityIdAllocator entityIds();

    void spawnEntity(AbstractEntity entity);

    void despawnEntity(AbstractEntity entity);

    static EntityContext get() {
        return ServerContext.get(EntityContext.class);
    }
}
