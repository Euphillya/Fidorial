package fr.fidorial.entity;

import fr.fidorial.command.CommandSource;
import fr.fidorial.world.ChunkPos;
import fr.fidorial.world.Location;
import fr.fidorial.world.World;

import java.util.UUID;

public interface Entity extends CommandSource {

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
