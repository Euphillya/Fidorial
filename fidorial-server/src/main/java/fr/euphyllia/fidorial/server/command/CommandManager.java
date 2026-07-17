package fr.euphyllia.fidorial.server.command;

import fr.euphyllia.fidorial.api.command.CommandExecutor;
import fr.euphyllia.fidorial.api.command.CommandRegistry;
import fr.euphyllia.fidorial.api.command.CommandSender;
import fr.euphyllia.fidorial.server.command.defaults.*;
import net.kyori.adventure.text.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class CommandManager implements CommandRegistry {

    private static final Logger LOGGER = LoggerFactory.getLogger(CommandManager.class);

    private final Map<String, CommandExecutor> commands = new ConcurrentHashMap<>();

    public CommandManager() {
        registerDefaults();
    }

    private void registerDefaults() {
        register("tps", new TpsCommand());
        register("weather", new WeatherCommand());
        register("gamemode", new GameModeCommand());
        register("gm", new GameModeCommand());
        register("op", new OpCommand(true));
        register("deop", new OpCommand(false));
        register("stop", new StopCommand());
    }

    @Override
    public void register(String name, CommandExecutor executor) {
        commands.put(name.toLowerCase(Locale.ROOT), executor);
    }

    @Override
    public boolean unregister(String name) {
        return commands.remove(name.toLowerCase(Locale.ROOT)) != null;
    }

    @Override
    public boolean isRegistered(String name) {
        return commands.containsKey(name.toLowerCase(Locale.ROOT));
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
            sender.sendMessage(Component.text("Commande inconnue : /" + label));
            return;
        }
        try {
            executor.execute(sender, label, args);
        } catch (Throwable t) {
            LOGGER.error("Erreur pendant /{} (emise par {})", label, sender.name(), t);
            sender.sendMessage(Component.text("Une erreur est survenue pendant /" + label));
        }
    }

}