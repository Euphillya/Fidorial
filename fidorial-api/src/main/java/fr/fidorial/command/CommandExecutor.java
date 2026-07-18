package fr.fidorial.command;

@FunctionalInterface
public interface CommandExecutor {
    void execute(CommandSender sender, String label, String[] args);
}

