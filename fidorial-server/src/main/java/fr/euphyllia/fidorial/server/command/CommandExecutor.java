package fr.euphyllia.fidorial.server.command;

import fr.euphyllia.fidorial.server.network.ClientConnection;

@FunctionalInterface
public interface CommandExecutor {
    void execute(ClientConnection sender, String[] args);
}