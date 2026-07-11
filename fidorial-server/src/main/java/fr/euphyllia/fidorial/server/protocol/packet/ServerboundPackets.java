package fr.euphyllia.fidorial.server.protocol.packet;

import fr.euphyllia.fidorial.server.network.ConnectionState;
import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.protocol.catalog.*;
import fr.euphyllia.fidorial.server.protocol.packet.serverbound.configuration.ServerboundFinishConfigurationPacket;
import fr.euphyllia.fidorial.server.protocol.packet.serverbound.configuration.ServerboundSelectKnownPacksPacket;
import fr.euphyllia.fidorial.server.protocol.packet.serverbound.handshake.ServerboundIntentionPacket;
import fr.euphyllia.fidorial.server.protocol.packet.serverbound.login.ServerboundHelloPacket;
import fr.euphyllia.fidorial.server.protocol.packet.serverbound.login.ServerboundKeyPacket;
import fr.euphyllia.fidorial.server.protocol.packet.serverbound.login.ServerboundLoginAcknowledgedPacket;
import fr.euphyllia.fidorial.server.protocol.packet.serverbound.play.ServerboundAcceptTeleportationPacket;
import fr.euphyllia.fidorial.server.protocol.packet.serverbound.play.ServerboundKeepAlivePacket;
import fr.euphyllia.fidorial.server.protocol.packet.serverbound.play.ServerboundPlayerLoadedPacket;
import fr.euphyllia.fidorial.server.protocol.packet.serverbound.status.ServerboundPingRequestPacket;
import fr.euphyllia.fidorial.server.protocol.packet.serverbound.status.ServerboundStatusRequestPacket;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;


public class ServerboundPackets {

    private static final Map<ConnectionState, Map<String, Reader>> READERS =
            new EnumMap<>(ConnectionState.class);

    static {
        register(ConnectionState.HANDSHAKE, HandshakeServerboundPackets.INTENTION,
                ServerboundIntentionPacket::read);

        register(ConnectionState.STATUS, StatusServerboundPackets.STATUS_REQUEST,
                ServerboundStatusRequestPacket::read);
        register(ConnectionState.STATUS, StatusServerboundPackets.PING_REQUEST,
                ServerboundPingRequestPacket::read);

        register(ConnectionState.LOGIN, LoginServerboundPackets.HELLO,
                ServerboundHelloPacket::read);
        register(ConnectionState.LOGIN, LoginServerboundPackets.KEY,
                ServerboundKeyPacket::read);
        register(ConnectionState.LOGIN, LoginServerboundPackets.LOGIN_ACKNOWLEDGED,
                ServerboundLoginAcknowledgedPacket::read);

        register(ConnectionState.CONFIGURATION, ConfigurationServerboundPackets.SELECT_KNOWN_PACKS,
                ServerboundSelectKnownPacksPacket::read);
        register(ConnectionState.CONFIGURATION, ConfigurationServerboundPackets.FINISH_CONFIGURATION,
                ServerboundFinishConfigurationPacket::read);

        register(ConnectionState.PLAY, PlayServerboundPackets.PLAYER_LOADED,
                ServerboundPlayerLoadedPacket::read);
        register(ConnectionState.PLAY, PlayServerboundPackets.ACCEPT_TELEPORTATION,
                ServerboundAcceptTeleportationPacket::read);
        register(ConnectionState.PLAY, PlayServerboundPackets.KEEP_ALIVE,
                ServerboundKeepAlivePacket::read);
    }

    private ServerboundPackets() {
    }

    private static void register(ConnectionState state, String name, Reader reader) {
        READERS.computeIfAbsent(state, s -> new HashMap<>()).put(name, reader);
    }

    public static ServerboundPacket decode(ConnectionState state, String name, PacketBuffer buf) {
        Reader reader = READERS.getOrDefault(state, Map.of()).get(name);
        return reader == null ? null : reader.read(buf);
    }

    @FunctionalInterface
    public interface Reader {
        ServerboundPacket read(PacketBuffer buf);
    }

}
