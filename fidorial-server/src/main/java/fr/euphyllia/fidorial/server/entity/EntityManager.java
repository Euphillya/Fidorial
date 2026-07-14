package fr.euphyllia.fidorial.server.entity;

import fr.euphyllia.fidorial.api.world.ChunkPos;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;


public final class EntityManager {

    private final Map<Integer, AbstractEntity> byId = new ConcurrentHashMap<>();
    private final Map<UUID, AbstractEntity> byUuid = new ConcurrentHashMap<>();
    private final Map<Long, Set<AbstractEntity>> byChunk = new ConcurrentHashMap<>();

    private static long key(ChunkPos pos) {
        return ((long) pos.z() << 32) | (pos.x() & 0xFFFFFFFFL);
    }

    public void add(AbstractEntity entity) {
        byId.put(entity.entityId(), entity);
        byUuid.put(entity.uuid(), entity);
        byChunk.computeIfAbsent(key(entity.chunk()), k -> ConcurrentHashMap.newKeySet()).add(entity);
    }

    public void remove(AbstractEntity entity) {
        byId.remove(entity.entityId());
        byUuid.remove(entity.uuid());
        byChunk.computeIfPresent(key(entity.chunk()), (k, set) -> {
            set.remove(entity);
            return set.isEmpty() ? null : set;
        });
    }

    public void moved(AbstractEntity entity, ChunkPos from, ChunkPos to) {
        if (from.equals(to)) {
            return;
        }
        byChunk.computeIfPresent(key(from), (k, set) -> {
            set.remove(entity);
            return set.isEmpty() ? null : set;
        });
        byChunk.computeIfAbsent(key(to), k -> ConcurrentHashMap.newKeySet()).add(entity);
    }

    public AbstractEntity byId(int entityId) {
        return byId.get(entityId);
    }

    public AbstractEntity byUuid(UUID uuid) {
        return byUuid.get(uuid);
    }

    public Set<AbstractEntity> inChunk(ChunkPos pos) {
        return byChunk.getOrDefault(key(pos), Set.of());
    }

    public Collection<AbstractEntity> all() {
        return Collections.unmodifiableCollection(byId.values());
    }

    public int count() {
        return byId.size();
    }
}
