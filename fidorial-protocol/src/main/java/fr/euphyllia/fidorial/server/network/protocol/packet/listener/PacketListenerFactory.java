package fr.euphyllia.fidorial.server.network.protocol.packet.listener;

import fr.euphyllia.fidorial.server.network.ClientConnection;
import fr.euphyllia.fidorial.server.network.ConnectionState;
import fr.fidorial.protocol.PacketListener;

@FunctionalInterface
public interface PacketListenerFactory {

    PacketListener create(ClientConnection connection, ConnectionState state);
}
