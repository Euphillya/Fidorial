package fr.euphyllia.fidorial.api.entity;

import fr.euphyllia.fidorial.api.world.ChunkPos;
import fr.euphyllia.fidorial.api.world.Location;
import fr.euphyllia.fidorial.api.world.World;

import java.util.UUID;

public interface Entity {

    int entityId();

    UUID uuid();

    EntityType type();

    World world();

    Location location();

    default ChunkPos chunk() {
        return location().chunk();
    }

    boolean isRemoved();

    void remove();
}
