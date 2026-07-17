package fr.euphyllia.fidorial.server.command.defaults;

import fr.euphyllia.fidorial.api.command.CommandExecutor;
import fr.euphyllia.fidorial.api.command.CommandSender;
import fr.euphyllia.fidorial.api.entity.GameMode;
import fr.euphyllia.fidorial.api.entity.Player;
import fr.euphyllia.fidorial.server.FidorialServer;
import net.kyori.adventure.text.Component;

public final class GameModeCommand implements CommandExecutor {

    private static String describe(GameMode mode) {
        return switch (mode) {
            case SURVIVAL -> "survie";
            case CREATIVE -> "créatif";
            case ADVENTURE -> "aventure";
            case SPECTATOR -> "spectateur";
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
                sender.sendMessage(Component.text("Mode de jeu actuel : " + describe(self.gameMode())));
            } else {
                sender.sendMessage(Component.text("Usage : /" + label + " <survival|creative|adventure|spectator> [joueur]"));
            }
            return;
        }

        GameMode mode = GameMode.byName(args[0]);
        if (mode == null) {
            sender.sendMessage(Component.text("Mode de jeu inconnu : " + args[0]
                    + " (survival, creative, adventure, spectator)"));
            return;
        }

        Player target;
        if (args.length >= 2) {
            target = findPlayer(args[1]);
            if (target == null) {
                sender.sendMessage(Component.text("Joueur introuvable : " + args[1]));
                return;
            }
        } else if (sender instanceof Player self) {
            target = self;
        } else {
            sender.sendMessage(Component.text("Depuis la console : /" + label + " <mode> <joueur>"));
            return;
        }

        target.setGameMode(mode);
        target.sendMessage(Component.text("Mode de jeu changé : " + describe(mode)));
        if (target != sender) {
            sender.sendMessage(Component.text("Mode de jeu de " + target.name() + " changé : " + describe(mode)));
        }
    }

    private Player findPlayer(String name) {
        return FidorialServer.getInstance().player(name).orElse(null);
    }
}
