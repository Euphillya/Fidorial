package fr.euphyllia.fidorial.api.command;

@FunctionalInterface
public interface CommandExecutor {
    void execute(CommandSender sender, String label, String[] args);
}

