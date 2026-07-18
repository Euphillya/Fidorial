package fr.euphyllia.fidorial.server.entity;

import fr.euphyllia.fidorial.api.scheduler.RegionTickHandler;
import fr.euphyllia.fidorial.server.world.ServerWorld;
import fr.euphyllia.fidorial.server.world.WorldManager;
import net.kyori.adventure.text.logger.slf4j.ComponentLogger;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static fr.euphyllia.fidorial.server.adventure.AdventureHelper.getLogger;

public final class EntityTickHandler implements RegionTickHandler {

    private static final ComponentLogger LOGGER = getLogger(EntityTickHandler.class);

    private final WorldManager worldManager;
    private final Map<String, ServerWorld> worldsById = new ConcurrentHashMap<>();

    public EntityTickHandler(WorldManager worldManager) {
        this.worldManager = worldManager;
    }

    @Override
    public void tick(String worldId, int sectionX, int sectionZ, long currentTick) {
        ServerWorld world = worldById(worldId);
        if (world == null) {
            return;
        }
        for (AbstractEntity entity : world.entityManager().inSection(sectionX, sectionZ)) {
            if (entity.isRemoved()) {
                continue;
            }
            try {
                entity.tick(currentTick);
            } catch (Throwable t) {
                LOGGER.error("Erreur pendant le tick de {}", entity, t);
            }
        }
    }

    private ServerWorld worldById(String id) {
        ServerWorld cached = worldsById.get(id);
        if (cached != null) {
            return cached;
        }
        for (ServerWorld world : worldManager.worlds()) {
            if (world.dimension().id().equals(id)) {
                worldsById.put(id, world);
                return world;
            }
        }
        return null;
    }
}
