package fr.euphyllia.fidorial.api.command;

import fr.euphyllia.fidorial.api.permission.Permissible;

public interface CommandSender extends Permissible {

    String name();

    void sendMessage(String message);

    default boolean isConsole() {
        return false;
    }
}
