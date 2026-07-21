package fr.euphyllia.fidorial.server.protocol.impl;

import fr.euphyllia.fidorial.server.entity.player.ServerPlayer;
import fr.euphyllia.fidorial.server.network.ClientConnection;
import fr.euphyllia.fidorial.server.network.ConnectionState;
import fr.euphyllia.fidorial.server.protocol.packet.ClientboundPacket;
import fr.euphyllia.fidorial.server.protocol.packet.ServerboundPacket;
import fr.fidorial.entity.Player;
import fr.fidorial.event.EventPriority;
import fr.fidorial.event.Subscription;
import fr.fidorial.protocol.ConnectionPhase;
import fr.fidorial.protocol.PacketContainer;
import fr.fidorial.protocol.PacketDirection;
import fr.fidorial.protocol.PacketEvent;
import fr.fidorial.protocol.PacketListener;
import fr.fidorial.protocol.PacketType;
import fr.fidorial.protocol.ProtocolManager;
import net.kyori.adventure.text.logger.slf4j.ComponentLogger;
import org.jspecify.annotations.Nullable;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import static fr.euphyllia.fidorial.server.adventure.AdventureHelper.getLogger;

public final class FidorialProtocolManager implements ProtocolManager {

    private static final ComponentLogger LOGGER = getLogger(FidorialProtocolManager.class);

    private final Map<PacketType, CopyOnWriteArrayList<Registration>> byType = new ConcurrentHashMap<>();
    private final Map<PacketDirection, CopyOnWriteArrayList<Registration>> byDirection =
            new ConcurrentHashMap<>();

    private static ClientConnection connectionOf(Player player) {
        if (player instanceof ServerPlayer serverPlayer) {
            return serverPlayer.connection();
        }
        throw new IllegalArgumentException("Player not managed by this server: " + player.getClass());
    }

    private static ConnectionPhase phase(ConnectionState state) {
        return ConnectionPhase.valueOf(state.name());
    }

    private static Subscription register(CopyOnWriteArrayList<Registration> list,
                                         EventPriority priority, PacketListener listener) {
        Registration reg = new Registration(priority, listener);
        list.add(reg);
        list.sort(Comparator.comparingInt(r -> r.priority.ordinal()));
        return reg.handle(list);
    }

    @Override
    public Subscription addListener(EventPriority priority, PacketType type, PacketListener listener) {
        CopyOnWriteArrayList<Registration> list =
                byType.computeIfAbsent(type, t -> new CopyOnWriteArrayList<>());
        return register(list, priority, listener);
    }

    @Override
    public Subscription addListener(EventPriority priority, PacketDirection direction, PacketListener listener) {
        CopyOnWriteArrayList<Registration> list =
                byDirection.computeIfAbsent(direction, d -> new CopyOnWriteArrayList<>());
        return register(list, priority, listener);
    }

    @Override
    public PacketContainer createPacket(PacketType type) {
        throw new UnsupportedOperationException(
                "createPacket is not available yet for " + type + ": intercept an existing "
                        + "packet then use packet().copy() to resend it through sendPacket().");
    }

    @Override
    public void sendPacket(Player player, PacketContainer packet) {
        if (packet.type().direction() != PacketDirection.CLIENTBOUND) {
            throw new IllegalArgumentException("sendPacket expects a clientbound packet, got " + packet.type());
        }
        ClientConnection connection = connectionOf(player);
        Object rebuilt = ((RecordPacketContainer) packet).rebuild();
        connection.send((ClientboundPacket) rebuilt);
    }

    @Override
    public void receivePacket(Player player, PacketContainer packet) {
        if (packet.type().direction() != PacketDirection.SERVERBOUND) {
            throw new IllegalArgumentException("receivePacket expects a serverbound packet, got " + packet.type());
        }
        ClientConnection connection = connectionOf(player);
        Object rebuilt = ((RecordPacketContainer) packet).rebuild();
        connection.execute(() -> connection.injectServerbound(
                packet.type().key().asString(), (ServerboundPacket) rebuilt));
    }

    @Override
    public void unregisterAll(Object owner) {
        byType.values().forEach(l -> l.removeIf(r -> r.listener == owner));
        byDirection.values().forEach(l -> l.removeIf(r -> r.listener == owner));
    }

    /**
     * Fires the clientbound listeners before a packet is encoded.
     *
     * @param connection sending connection
     * @param packet     packet about to be sent
     * @return the packet to encode (possibly modified), or {@code null} if it was cancelled
     */
    public @Nullable ClientboundPacket fireClientbound(ClientConnection connection, ClientboundPacket packet) {
        if (hasNoListeners() || !(packet instanceof Record record)) {
            return packet;
        }
        PacketType type = PacketType.of(phase(connection.state()), PacketDirection.CLIENTBOUND, packet.name());
        List<Registration> regs = matching(type, PacketDirection.CLIENTBOUND);
        if (regs.isEmpty()) {
            return packet;
        }
        RecordPacketContainer container = RecordPacketContainer.of(type, record);
        PacketEvent event = new PacketEvent(connection.player(), PacketDirection.CLIENTBOUND, container);
        dispatch(regs, event);
        if (event.isCancelled()) {
            return null;
        }
        try {
            return (ClientboundPacket) container.rebuild();
        } catch (RuntimeException e) {
            LOGGER.warn("Cannot rebuild clientbound packet {}, sending the original", type, e);
            return packet;
        }
    }

    /**
     * Fires the serverbound listeners after a packet is decoded.
     *
     * @param connection receiving connection
     * @param resource   resource key of the packet (e.g. {@code minecraft:chat})
     * @param packet     decoded packet
     * @return the packet to handle (possibly modified), or {@code null} if it was cancelled
     */
    public @Nullable ServerboundPacket fireServerbound(ClientConnection connection, String resource,
                                                       ServerboundPacket packet) {
        if (hasNoListeners() || !(packet instanceof Record record)) {
            return packet;
        }
        PacketType type = PacketType.of(phase(connection.state()), PacketDirection.SERVERBOUND, resource);
        List<Registration> regs = matching(type, PacketDirection.SERVERBOUND);
        if (regs.isEmpty()) {
            return packet;
        }
        RecordPacketContainer container = RecordPacketContainer.of(type, record);
        PacketEvent event = new PacketEvent(connection.player(), PacketDirection.SERVERBOUND, container);
        dispatch(regs, event);
        if (event.isCancelled()) {
            return null;
        }
        try {
            return (ServerboundPacket) container.rebuild();
        } catch (RuntimeException e) {
            LOGGER.warn("Cannot rebuild serverbound packet {}, handling the original", type, e);
            return packet;
        }
    }

    private List<Registration> matching(PacketType type, PacketDirection direction) {
        CopyOnWriteArrayList<Registration> typed = byType.get(type);
        CopyOnWriteArrayList<Registration> wild = byDirection.get(direction);
        if (wild == null || wild.isEmpty()) {
            return typed == null ? List.of() : typed;
        }
        if (typed == null || typed.isEmpty()) {
            return wild;
        }
        var merged = new java.util.ArrayList<Registration>(typed.size() + wild.size());
        merged.addAll(typed);
        merged.addAll(wild);
        merged.sort(Comparator.comparingInt(r -> r.priority.ordinal()));
        return merged;
    }

    private void dispatch(List<Registration> regs, PacketEvent event) {
        for (Registration reg : regs) {
            try {
                reg.listener.onPacket(event);
            } catch (Throwable t) {
                LOGGER.error("Packet listener failed on {}", event.type(), t);
            }
        }
    }

    private boolean hasNoListeners() {
        return byType.isEmpty() && byDirection.isEmpty();
    }

    private record Registration(EventPriority priority, PacketListener listener) {

        Subscription handle(CopyOnWriteArrayList<Registration> owner) {
                return new Handle(owner, this);
            }
        }

    private static final class Handle implements Subscription {
        private final CopyOnWriteArrayList<Registration> owner;
        private final Registration registration;
        private volatile boolean active = true;

        Handle(CopyOnWriteArrayList<Registration> owner, Registration registration) {
            this.owner = owner;
            this.registration = registration;
        }

        @Override
        public boolean isActive() {
            return active;
        }

        @Override
        public void unsubscribe() {
            if (active) {
                active = false;
                owner.remove(registration);
            }
        }
    }
}
