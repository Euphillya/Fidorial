package fr.euphyllia.fidorial.server.world.entity;

import fr.fidorial.world.ChunkPos;

public interface EntityLookup {

    Iterable<? extends LevelEntity> all();

    Iterable<? extends LevelEntity> inChunk(ChunkPos pos);

    Iterable<? extends LevelPlayer> players();

    void add(LevelEntity entity);

    void remove(LevelEntity entity);

    void moved(LevelEntity entity, ChunkPos from, ChunkPos to);
}
