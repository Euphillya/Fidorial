package fr.euphyllia.fidorial.server.entity;

import fr.fidorial.entity.Entity;
import fr.fidorial.entity.EntityType;
import fr.fidorial.world.Location;
import fr.fidorial.world.World;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;


public abstract class AbstractEntity implements Entity {

    private final int entityId;
    private UUID uuid;
    private final EntityType type;
    private final AtomicBoolean removed = new AtomicBoolean(false);

    private volatile World world;
    private volatile Location location;

    protected AbstractEntity(int entityId, UUID uuid, EntityType type, World world, Location location) {
        this.entityId = entityId;
        this.uuid = uuid;
        this.type = type;
        this.world = world;
        this.location = location;
    }

    @Override
    public final int entityId() {
        return entityId;
    }

    @Override
    public UUID uuid() {
        return uuid;
    }

    public final void restoreUuid(final UUID uuid) {
        this.uuid = uuid;
    }

    @Override
    public final EntityType type() {
        return type;
    }

    @Override
    public final World world() {
        return world;
    }

    @Override
    public final Location location() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public void setWorld(World world) {
        this.world = world;
    }

    @Override
    public final boolean isRemoved() {
        return removed.get();
    }

    @Override
    public final void remove() {
        if (removed.compareAndSet(false, true)) {
            onRemoved();
        }
    }

    /**
     * Appele une seule fois, au retrait effectif.
     */
    protected void onRemoved() {
    }

    /**
     * Appele une fois par tick par la region proprietaire de {@link #chunk()}.
     * Implementation par defaut vide : un item au sol n'a rien a faire.
     */
    public void tick(long currentTick) {
    }

    @Override
    public final boolean equals(Object o) {
        return o instanceof AbstractEntity other && other.entityId == entityId;
    }

    @Override
    public final int hashCode() {
        return Integer.hashCode(entityId);
    }

    @Override
    public String toString() {
        return type.key() + "#" + entityId;
    }
}
