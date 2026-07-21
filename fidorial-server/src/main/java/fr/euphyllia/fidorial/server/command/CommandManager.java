package fr.euphyllia.fidorial.server.command;

import fr.fidorial.command.CommandExecutor;
import fr.fidorial.command.CommandRegistry;
import fr.fidorial.command.CommandSender;
import fr.euphyllia.fidorial.server.command.defaults.*;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.logger.slf4j.ComponentLogger;

import java.util.Arrays;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static fr.euphyllia.fidorial.server.adventure.AdventureHelper.getLogger;

public final class CommandManager implements CommandRegistry {

    private static final ComponentLogger LOGGER = getLogger(CommandManager.class);

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
        register("summon", new SummonCommand());
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
        String trimmed = line.strip();
        if (trimmed.startsWith("/")) trimmed = trimmed.substring(1);
        if (trimmed.isEmpty()) return;

        String[] parts = trimmed.split("\\s+");
        String label = parts[0].toLowerCase(Locale.ROOT);
        String[] args = Arrays.copyOfRange(parts, 1, parts.length);

        CommandExecutor executor = commands.get(label);
        if (executor == null) {
            sender.sendMessage(Component.translatable("command.error.unknown", Component.text(label)));
            return;
        }
        try {
            executor.execute(sender, label, args);
        } catch (Throwable t) {
            LOGGER.error("Erreur pendant /{} (emise par {})", label, sender.name(), t);
            sender.sendMessage(Component.translatable("command.error.exception", Component.text(label)));
        }
    }

}