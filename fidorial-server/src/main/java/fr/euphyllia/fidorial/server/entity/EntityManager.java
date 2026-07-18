package fr.euphyllia.fidorial.server.entity;

import fr.euphyllia.fidorial.api.world.ChunkPos;
import fr.euphyllia.fidorial.server.schedulers.ThreadedRegionRegionizer;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;


public final class EntityManager {

    private final Map<Integer, AbstractEntity> byId = new ConcurrentHashMap<>();
    private final Map<UUID, AbstractEntity> byUuid = new ConcurrentHashMap<>();
    private final Map<Long, Set<AbstractEntity>> byChunk = new ConcurrentHashMap<>();
    private final Map<Long, Set<AbstractEntity>> bySection = new ConcurrentHashMap<>();

    private static long key(ChunkPos pos) {
        return ((long) pos.z() << 32) | (pos.x() & 0xFFFFFFFFL);
    }

    private static long sectionKey(ChunkPos pos) {
        return sectionKey(pos.x() >> ThreadedRegionRegionizer.SECTION_SHIFT, pos.z() >> ThreadedRegionRegionizer.SECTION_SHIFT);
    }

    private static long sectionKey(int sectionX, int sectionZ) {
        return ((long) sectionZ << 32) | (sectionX & 0xFFFFFFFFL);
    }

    public void add(AbstractEntity entity) {
        byId.put(entity.entityId(), entity);
        byUuid.put(entity.uuid(), entity);
        ChunkPos chunk = entity.chunk();
        byChunk.computeIfAbsent(key(chunk), k -> ConcurrentHashMap.newKeySet()).add(entity);
        bySection.computeIfAbsent(sectionKey(chunk), k -> ConcurrentHashMap.newKeySet()).add(entity);
    }

    public void remove(AbstractEntity entity) {
        byId.remove(entity.entityId());
        byUuid.remove(entity.uuid());
        ChunkPos chunk = entity.chunk();
        byChunk.computeIfPresent(key(chunk), (k, set) -> {
            set.remove(entity);
            return set.isEmpty() ? null : set;
        });
        bySection.computeIfPresent(sectionKey(chunk), (k, set) -> {
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

        long fromSection = sectionKey(from);
        long toSection = sectionKey(to);
        if (fromSection != toSection) {
            bySection.computeIfPresent(fromSection, (k, set) -> {
                set.remove(entity);
                return set.isEmpty() ? null : set;
            });
            bySection.computeIfAbsent(toSection, k -> ConcurrentHashMap.newKeySet()).add(entity);
        }
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

    public Set<AbstractEntity> inSection(int sectionX, int sectionZ) {
        return bySection.getOrDefault(sectionKey(sectionX, sectionZ), Set.of());
    }

    public Collection<AbstractEntity> all() {
        return Collections.unmodifiableCollection(byId.values());
    }

    public int count() {
        return byId.size();
    }
}
