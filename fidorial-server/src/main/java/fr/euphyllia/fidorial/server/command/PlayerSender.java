package fr.euphyllia.fidorial.server.command;

import fr.euphyllia.fidorial.api.command.CommandSender;
import fr.euphyllia.fidorial.server.network.ClientConnection;
import fr.euphyllia.fidorial.server.protocol.packet.clientbound.play.ClientboundSystemChatPacket;

public class PlayerSender implements CommandSender {

    private final ClientConnection connection;

    public PlayerSender(ClientConnection connection) {
        this.connection = connection;
    }

    public ClientConnection connection() {
        return connection;
    }

    @Override
    public String name() {
        return connection.username();
    }

    @Override
    public void sendMessage(String message) {
        connection.send(new ClientboundSystemChatPacket(message, false));
    }
}
