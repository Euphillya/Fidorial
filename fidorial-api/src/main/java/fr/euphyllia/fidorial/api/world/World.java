package fr.euphyllia.fidorial.api.world;

import fr.euphyllia.fidorial.api.entity.Entity;
import fr.euphyllia.fidorial.api.registry.Key;

import java.util.Collection;
import java.util.UUID;

public interface World {

    Key key();

    int minY();

    int height();

    int getBlockStateId(BlockPos pos);

    boolean setBlockStateId(BlockPos pos, int stateId);

    Collection<? extends Entity> entities();

    Entity entity(UUID uuid);

    Entity entity(int entityId);
}
