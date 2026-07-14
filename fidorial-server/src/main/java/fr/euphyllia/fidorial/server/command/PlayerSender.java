package fr.euphyllia.fidorial.server.command;

import fr.euphyllia.fidorial.api.command.CommandSender;
import fr.euphyllia.fidorial.server.network.ClientConnection;
import fr.euphyllia.fidorial.server.protocol.packet.clientbound.play.ClientboundSystemChatPacket;

public record PlayerSender(ClientConnection connection) implements CommandSender {

    @Override
    public String name() {
        return connection.username();
    }

    @Override
    public void sendMessage(String message) {
        connection.send(new ClientboundSystemChatPacket(message, false));
    }
}
