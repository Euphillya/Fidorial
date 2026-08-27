package fr.euphyllia.fidorial.server.world;

import fr.euphyllia.fidorial.server.context.ServerContext;
import fr.euphyllia.fidorial.server.registry.RegistryContext;
import fr.euphyllia.fidorial.server.schedulers.AiWorker;
import fr.euphyllia.fidorial.server.schedulers.ThreadedRegionRegionizer;
import fr.euphyllia.fidorial.server.world.entity.LevelEntity;
import fr.euphyllia.fidorial.server.world.entity.LevelPlayer;
import fr.euphyllia.fidorial.server.world.weather.WeatherEngine;

public interface WorldContext extends RegistryContext {

    WorldManager worldManager();

    ThreadedRegionRegionizer regionizer();

    AiWorker aiWorker();

    WeatherEngine weatherEngine();

    BlockStateRegistry blockStateRegistry();

    BossBarRegistry bossBarRegistry();

    ChunkNetworkSerializer chunkSerializer();

    Iterable<? extends LevelPlayer> connectedPlayers();

    void despawn(LevelEntity entity);

    static WorldContext get() {
        return ServerContext.get(WorldContext.class);
    }
}
