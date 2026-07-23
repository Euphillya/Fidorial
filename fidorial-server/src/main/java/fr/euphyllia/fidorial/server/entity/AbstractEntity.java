package fr.euphyllia.fidorial.server.entity;

import fr.euphyllia.fidorial.server.FidorialServer;
import fr.euphyllia.fidorial.server.network.ClientConnection;
import fr.euphyllia.fidorial.server.protocol.packet.ClientboundPacket;
import fr.euphyllia.fidorial.server.protocol.packet.clientbound.play.ClientboundAddEntityPacket;
import fr.fidorial.command.CommandSender;
import fr.fidorial.entity.Entity;
import fr.fidorial.entity.EntityType;
import fr.fidorial.world.Location;
import fr.fidorial.world.World;
import org.jspecify.annotations.Nullable;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;

public abstract class AbstractEntity implements Entity {

    private final int entityId;
    private UUID uuid;
    private final EntityType type;
    private final AtomicBoolean removed = new AtomicBoolean(false);

    private volatile World world;
    private volatile Location location;

    protected AbstractEntity(final int entityId, final UUID uuid, final EntityType type, final World world, final Location location) {
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

    public void setLocation(final Location location) {
        this.location = location;
    }

    public void setWorld(final World world) {
        this.world = world;
    }

    @Override
    public final boolean isRemoved() {
        return removed.get();
    }

    @Override
    public FidorialServer server() {
        return FidorialServer.getInstance();
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
    public void tick(final long currentTick) {
    }

    public final void sendToTrackers(final ClientboundPacket packet) {
        server().entityTracker().sendToViewers(this, packet);
    }

    public void sendSpawnPackets(final ClientConnection connection) {
        connection.send(ClientboundAddEntityPacket.of(this));
    }

    @Override
    public final boolean equals(final Object o) {
        return o instanceof final AbstractEntity other && other.entityId == entityId;
    }

    @Override
    public final int hashCode() {
        return Integer.hashCode(entityId);
    }

    @Override
    public String toString() {
        return type.key() + "#" + entityId;
    }

    @Override
    public CommandSender sender() {
        return null;
    }

    @Override
    public @Nullable Entity executor() {
        return this;
    }
}
