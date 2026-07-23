package fr.fidorial.world;

import fr.fidorial.entity.Entity;
import fr.fidorial.world.time.DayNightCycle;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.key.Keyed;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public interface World extends Keyed /* ForwardingAudience */ { // make it extend when we have enough features

    int minY();

    int height();

    DayNightCycle dayNightCycle();

    CompletableFuture<Chunk> getChunkAsync(int chunkX, int chunkZ);

    default CompletableFuture<Chunk> getChunkAsync(final ChunkPos pos) {
        return getChunkAsync(pos.x(), pos.z());
    }

    Optional<Chunk> getChunkIfLoaded(int chunkX, int chunkZ);

    default Chunk getChunkIfLoaded(final ChunkPos pos) {
        return getChunkIfLoaded(pos.x(), pos.z()).orElseThrow();
    }

    default boolean isChunkLoaded(final int chunkX, final int chunkZ) {
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

    default CompletableFuture<Boolean> unloadChunkAsync(final ChunkPos pos) {
        return unloadChunkAsync(pos.x(), pos.z());
    }
}
