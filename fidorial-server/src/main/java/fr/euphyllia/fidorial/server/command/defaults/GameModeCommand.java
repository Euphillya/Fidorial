package fr.euphyllia.fidorial.server.command.defaults;

import fr.euphyllia.fidorial.api.command.CommandExecutor;
import fr.euphyllia.fidorial.api.command.CommandSender;
import fr.euphyllia.fidorial.api.entity.GameMode;
import fr.euphyllia.fidorial.api.entity.Player;
import fr.euphyllia.fidorial.server.FidorialServer;
import net.kyori.adventure.text.Component;

public final class GameModeCommand implements CommandExecutor {

    private static Component describe(GameMode mode) {
        return switch (mode) {
            case SURVIVAL -> Component.translatable("gamemode.survival");
            case CREATIVE -> Component.translatable("gamemode.creative");
            case ADVENTURE -> Component.translatable("gamemode.adventure");
            case SPECTATOR -> Component.translatable("gamemode.spectator");
        };
    }

    @Override
    public void execute(CommandSender sender, String label, String[] args) {
        if (!sender.hasPermission("fidorial.command.gamemode")) {
            sender.sendMessage(Component.translatable("command.error.nopermission"));
            return;
        }
        if (args.length == 0) {
            if (sender instanceof Player self) {
                sender.sendMessage(Component.translatable("command.gamemode.current", describe(self.gameMode())));
            } else {
                sender.sendMessage(Component.translatable("command.gamemode.usage", Component.text(label)));
            }
            return;
        }

        GameMode mode = GameMode.byName(args[0]);
        if (mode == null) {
            sender.sendMessage(Component.translatable("command.gamemode.unknown", Component.text(args[0])));
            return;
        }

        Player target;
        if (args.length >= 2) {
            target = findPlayer(args[1]);
            if (target == null) {
                sender.sendMessage(Component.translatable("command.error.playernotfound", Component.text(args[1])));
                return;
            }
        } else if (sender instanceof Player self) {
            target = self;
        } else {
            sender.sendMessage(Component.translatable("command.gamemode.console", Component.text(label)));
            return;
        }

        target.setGameMode(mode);
        target.sendMessage(Component.translatable("command.gamemode.changed.self", describe(mode)));
        if (target != sender) {
            sender.sendMessage(Component.translatable("command.gamemode.changed.other",
                    Component.text(target.name()), describe(mode)));
        }
    }

    private Player findPlayer(String name) {
        return FidorialServer.getInstance().player(name).orElse(null);
    }
}
