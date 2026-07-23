package fr.euphyllia.fidorial.server.entity;

import fr.euphyllia.fidorial.server.entity.player.ServerPlayer;
import fr.euphyllia.fidorial.server.network.ClientConnection;
import fr.euphyllia.fidorial.server.protocol.packet.ClientboundPacket;
import fr.euphyllia.fidorial.server.protocol.packet.clientbound.play.ClientboundRemoveEntitiesPacket;
import fr.fidorial.world.Location;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;


public final class EntityTracker {

    public static final double MAX_TRACKING_RANGE = 48.0;

    private static final double UNTRACK_MARGIN = 8.0;

    public static final int UPDATE_INTERVAL_TICKS = 2;

    private final Map<Integer, Set<ClientConnection>> viewers = new ConcurrentHashMap<>();

    private final double trackDistanceSq;
    private final double untrackDistanceSq;

    public EntityTracker(final int sendDistanceChunks) {
        final double range = Math.min(MAX_TRACKING_RANGE, Math.max(1, sendDistanceChunks) * 16.0);
        this.trackDistanceSq = range * range;
        this.untrackDistanceSq = (range + UNTRACK_MARGIN) * (range + UNTRACK_MARGIN);
    }

    public static boolean shouldUpdate(final AbstractEntity entity, final long currentTick) {
        return Math.floorMod(currentTick + entity.entityId(), UPDATE_INTERVAL_TICKS) == 0;
    }


    public void update(final AbstractEntity entity, final Collection<ServerPlayer> players) {
        if (entity.isRemoved()) {
            untrack(entity);
            return;
        }
        if (entity instanceof ServerPlayer || !EntityTypes.hasNetworkId(entity.type())) {
            return;
        }

        final Set<ClientConnection> current =
                viewers.computeIfAbsent(entity.entityId(), key -> ConcurrentHashMap.newKeySet());
        final Location self = entity.location();

        for (final ServerPlayer player : players) {
            if (player.isRemoved()) {
                continue;
            }
            final ClientConnection connection = player.connection();
            final boolean tracked = current.contains(connection);
            final double limit = tracked ? untrackDistanceSq : trackDistanceSq;
            final boolean visible =
                    player.world() == entity.world() && distanceSq(self, player.location()) <= limit;

            if (visible && !tracked) {
                if (current.add(connection)) {
                    entity.sendSpawnPackets(connection);
                }
            } else if (!visible && tracked) {
                if (current.remove(connection)) {
                    connection.send(new ClientboundRemoveEntitiesPacket(entity.entityId()));
                }
            }
        }
    }

    public void sendToViewers(final AbstractEntity entity, final ClientboundPacket packet) {
        final Set<ClientConnection> current = viewers.get(entity.entityId());
        if (current == null || current.isEmpty()) {
            return;
        }
        for (final ClientConnection connection : current) {
            connection.send(packet);
        }
    }

    public void untrack(final AbstractEntity entity) {
        final Set<ClientConnection> current = viewers.remove(entity.entityId());
        if (current == null || current.isEmpty()) {
            return;
        }
        final ClientboundRemoveEntitiesPacket packet = new ClientboundRemoveEntitiesPacket(entity.entityId());
        for (final ClientConnection connection : current) {
            connection.send(packet);
        }
        current.clear();
    }

    public void removeViewer(final ClientConnection connection) {
        for (final Set<ClientConnection> current : viewers.values()) {
            current.remove(connection);
        }
    }

    public int viewerCount(final AbstractEntity entity) {
        final Set<ClientConnection> current = viewers.get(entity.entityId());
        return current == null ? 0 : current.size();
    }

    public int trackedCount() {
        return viewers.size();
    }

    private static double distanceSq(final Location a, final Location b) {
        final double dx = a.x() - b.x();
        final double dy = a.y() - b.y();
        final double dz = a.z() - b.z();
        return dx * dx + dy * dy + dz * dz;
    }
}