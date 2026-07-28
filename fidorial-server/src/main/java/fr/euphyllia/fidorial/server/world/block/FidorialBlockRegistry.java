package fr.euphyllia.fidorial.server.world.block;

import fr.fidorial.world.block.BlockBehaviour;
import fr.fidorial.world.block.BlockData;
import fr.fidorial.world.block.BlockRegistry;
import fr.fidorial.world.block.BlockType;
import net.kyori.adventure.key.Key;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;


public final class FidorialBlockRegistry implements BlockRegistry {

    private final Map<Key, BlockType> types = new ConcurrentHashMap<>();
    private final Map<Key, BlockBehaviour> behaviours = new ConcurrentHashMap<>();
    private final Map<Integer, BlockData> byNetworkId = new ConcurrentHashMap<>();
    private final @Nullable BlockRegistry fallback;

    public FidorialBlockRegistry() {
        this(null);
    }

    public FidorialBlockRegistry(final @Nullable BlockRegistry fallback) {
        this.fallback = fallback;
    }

    @Override
    public void register(final BlockType type) {
        final BlockType previous = types.put(type.key(), type);
        if (previous != null) {
            throw new IllegalStateException("Block '" + type.key().asString() + "' is already registered");
        }
        for (int ordinal = 0; ordinal < type.stateCount(); ordinal++) {
            final BlockData data = type.stateAt(ordinal);
            byNetworkId.put(data.networkId(), data);
        }
    }

    @Override
    public void register(final BlockBehaviour behaviour) {
        register(behaviour.type());
        behaviours.put(behaviour.key(), behaviour);
    }

    @Override
    public Optional<BlockType> type(final Key key) {
        final BlockType type = types.get(key);
        if (type != null) {
            return Optional.of(type);
        }
        return fallback == null ? Optional.empty() : fallback.type(key);
    }

    @Override
    public @Nullable BlockData fromNetworkId(final int networkId) {
        final BlockData data = byNetworkId.get(networkId);
        if (data != null) {
            return data;
        }
        return fallback == null ? null : fallback.fromNetworkId(networkId);
    }

    @Override
    public Optional<BlockBehaviour> behaviour(final Key key) {
        return Optional.ofNullable(behaviours.get(key));
    }

    @Override
    public Collection<BlockType> types() {
        if (fallback == null) {
            return Collections.unmodifiableCollection(types.values());
        }
        final List<BlockType> all = new ArrayList<>(types.values());
        for (final BlockType type : fallback.types()) {
            if (!types.containsKey(type.key())) {
                all.add(type);
            }
        }
        return Collections.unmodifiableList(all);
    }

    public int definedCount() {
        return types.size();
    }
}
