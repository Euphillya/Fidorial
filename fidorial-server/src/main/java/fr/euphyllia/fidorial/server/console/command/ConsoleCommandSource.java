package fr.euphyllia.fidorial.server.console.command;

import fr.euphyllia.fidorial.server.FidorialServer;
import fr.fidorial.Server;
import fr.fidorial.command.CommandSender;
import fr.fidorial.command.CommandSource;
import fr.fidorial.entity.Entity;
import fr.fidorial.world.Location;
import org.jspecify.annotations.Nullable;

public final class ConsoleCommandSource implements CommandSource {

    private final CommandSender console;

    public ConsoleCommandSource(CommandSender console) {
        this.console = console;
    }

    @Override
    public CommandSender sender() {
        return console;
    }

    @Override
    public @Nullable Entity executor() {
        return null;
    }

    @Override
    public Server server() {
        return FidorialServer.getInstance();
    }

    @Override
    public @Nullable Location location() {
        return null;
    }
}
