package fr.euphyllia.fidorial.api.command;

public interface CommandRegistry {

    void register(String name, CommandExecutor executor);

    boolean unregister(String name);

    boolean isRegistered(String name);
}
