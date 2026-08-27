package fr.euphyllia.fidorial.server.world.entity;

import fr.fidorial.world.ChunkPos;

import java.util.UUID;

public interface LevelEntity {

    int entityId();

    UUID uuid();

    ChunkPos chunkPos();

    boolean persistable();

    boolean isPlayer();
}
