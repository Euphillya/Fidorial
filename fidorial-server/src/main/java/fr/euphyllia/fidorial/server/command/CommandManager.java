package fr.euphyllia.fidorial.server.command;

import fr.euphyllia.fidorial.api.command.CommandExecutor;
import fr.euphyllia.fidorial.api.command.CommandSender;
import fr.euphyllia.fidorial.server.command.defaults.TpsCommand;
import fr.euphyllia.fidorial.server.command.defaults.WeatherCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class CommandManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(CommandManager.class);

    private final Map<String, CommandExecutor> commands = new ConcurrentHashMap<>();

    public CommandManager() {
        registerDefaults();
    }

    private void registerDefaults() {
        register("tps", new TpsCommand());
        register("weather", new WeatherCommand());
    }

    public void register(String name, CommandExecutor executor) {
        commands.put(name.toLowerCase(Locale.ROOT), executor);
    }

    public void dispatch(CommandSender sender, String line) {
        if (line == null) return;
        String trimmed = line.strip();
        if (trimmed.startsWith("/")) trimmed = trimmed.substring(1);
        if (trimmed.isEmpty()) return;

        String[] parts = trimmed.split("\\s+");
        String label = parts[0].toLowerCase(Locale.ROOT);
        String[] args = Arrays.copyOfRange(parts, 1, parts.length);

        CommandExecutor executor = commands.get(label);
        if (executor == null) {
            sender.sendMessage("Commande inconnue : /" + label);
            return;
        }
        try {
            executor.execute(sender, label, args);
        } catch (Throwable t) {
            LOGGER.error("Erreur pendant /{} (emise par {})", label, sender.name(), t);
            sender.sendMessage("Une erreur est survenue pendant /" + label);
        }
    }

}