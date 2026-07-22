package fr.fidorial.world;

import fr.fidorial.entity.Entity;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.key.Keyed;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public interface World extends Keyed /* ForwardingAudience */ { // make it extend when we have enough features

    int minY();

    int height();

    CompletableFuture<Chunk> getChunkAsync(int chunkX, int chunkZ);

    default CompletableFuture<Chunk> getChunkAsync(ChunkPos pos) {
        return getChunkAsync(pos.x(), pos.z());
    }

    Optional<Chunk> getChunkIfLoaded(int chunkX, int chunkZ);

    default Chunk getChunkIfLoaded(ChunkPos pos) {
        return getChunkIfLoaded(pos.x(), pos.z()).orElseThrow();
    }

    default boolean isChunkLoaded(int chunkX, int chunkZ) {
        return getChunkIfLoaded(chunkX, chunkZ).isPresent();
    }

    int getBlockStateId(BlockPos pos);

    boolean setBlockStateId(BlockPos pos, int stateId);

    Collection<? extends Entity> entities();

    Entity entity(UUID uuid);

    Entity entity(int entityId);

    // to remove once we extend forwarding audience
    Iterable<? extends Audience> audiences();

    CompletableFuture<Boolean> unloadChunkAsync(int chunkX, int chunkZ);

    default CompletableFuture<Boolean> unloadChunkAsync(ChunkPos pos) {
        return unloadChunkAsync(pos.x(), pos.z());
    }
}
