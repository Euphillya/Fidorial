package fr.euphyllia.fidorial.api.world;

import fr.euphyllia.fidorial.api.entity.Entity;

import net.kyori.adventure.audience.ForwardingAudience;
import net.kyori.adventure.key.Keyed;

import java.util.Collection;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public interface World extends Keyed /* ForwardingAudience */ { // make it extend when we have enough features

    int minY();

    int height();

    CompletableFuture<Chunk> getChunkAsync(int chunkX, int chunkZ);

    default CompletableFuture<Chunk> getChunkAsync(ChunkPos pos) {
        return getChunkAsync(pos.x(), pos.z());
    }

    Chunk getChunkIfLoaded(int chunkX, int chunkZ);

    default Chunk getChunkIfLoaded(ChunkPos pos) {
        return getChunkIfLoaded(pos.x(), pos.z());
    }

    default boolean isChunkLoaded(int chunkX, int chunkZ) {
        return getChunkIfLoaded(chunkX, chunkZ) != null;
    }

    int getBlockStateId(BlockPos pos);

    boolean setBlockStateId(BlockPos pos, int stateId);

    Collection<? extends Entity> entities();

    Entity entity(UUID uuid);

    Entity entity(int entityId);
}
