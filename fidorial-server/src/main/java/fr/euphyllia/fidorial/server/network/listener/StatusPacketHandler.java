package fr.euphyllia.fidorial.server.network.listener;

import fr.euphyllia.fidorial.server.FidorialServer;
import fr.euphyllia.fidorial.server.network.ClientConnection;
import fr.euphyllia.fidorial.server.protocol.packet.clientbound.status.ClientboundPongResponsePacket;
import fr.euphyllia.fidorial.server.protocol.packet.clientbound.status.ClientboundStatusResponsePacket;
import fr.euphyllia.fidorial.server.protocol.packet.listener.StatusPacketListener;
import fr.euphyllia.fidorial.server.protocol.packet.serverbound.status.ServerboundPingRequestPacket;
import fr.euphyllia.fidorial.server.protocol.packet.serverbound.status.ServerboundStatusRequestPacket;
import fr.euphyllia.fidorial.server.status.StatusResponseBuilder;
import fr.fidorial.event.server.ServerStatusRequestEvent;
import fr.fidorial.status.ServerStatus;

public final class StatusPacketHandler implements StatusPacketListener {

    private final ClientConnection connection;

    public StatusPacketHandler(ClientConnection connection) {
        this.connection = connection;
    }

    @Override
    public void handleStatusRequest(ServerboundStatusRequestPacket packet) {
        ServerStatus status = FidorialServer.getInstance().status();
        ServerStatusRequestEvent event = new ServerStatusRequestEvent(status);
        FidorialServer.getInstance().events().post(event);
        String json = StatusResponseBuilder.build(event.getStatus());
        connection.send(new ClientboundStatusResponsePacket(json));
    }

    @Override
    public void handlePingRequest(ServerboundPingRequestPacket packet) {
        connection.sendAndClose(new ClientboundPongResponsePacket(packet.payload()));
    }
}
