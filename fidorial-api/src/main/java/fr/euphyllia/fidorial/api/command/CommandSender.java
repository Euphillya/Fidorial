package fr.euphyllia.fidorial.api.command;

public interface CommandSender {

    String name();

    void sendMessage(String message);

    default boolean isConsole() {
        return false;
    }
}
