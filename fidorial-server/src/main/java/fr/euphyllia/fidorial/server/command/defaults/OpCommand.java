package fr.euphyllia.fidorial.server.command.defaults;

import fr.euphyllia.fidorial.api.command.CommandExecutor;
import fr.euphyllia.fidorial.api.command.CommandSender;
import fr.euphyllia.fidorial.api.entity.Player;
import fr.euphyllia.fidorial.server.FidorialServer;
import net.kyori.adventure.text.Component;

/**
 * /op <joueur>   -> promeut un joueur operateur
 * /deop <joueur> -> retrograde un joueur
 */
public final class OpCommand implements CommandExecutor {

    private final boolean grant;

    public OpCommand(boolean grant) {
        this.grant = grant;
    }

    @Override
    public void execute(CommandSender sender, String label, String[] args) {
        String permission = grant ? "fidorial.command.op" : "fidorial.command.deop";
        if (!sender.hasPermission(permission)) {
            sender.sendMessage(Component.translatable("command.error.nopermission"));
            return;
        }
        if (args.length != 1) {
            sender.sendMessage(Component.translatable("command.op.usage", Component.text(label)));
            return;
        }
        Player target = FidorialServer.getInstance().player(args[0]).orElse(null);
        if (target == null) {
            sender.sendMessage(Component.translatable("command.error.playernotfound", Component.text(args[0])));
            return;
        }
        if (target.isOp() == grant) {
            sender.sendMessage(Component.translatable(grant ? "command.op.already" : "command.op.not",
                    Component.text(target.name())));
            return;
        }
        target.setOp(grant);
        sender.sendMessage(Component.translatable(grant ? "command.op.granted.other" : "command.op.revoked.other",
                Component.text(target.name())));
        target.sendMessage(Component.translatable(grant ? "command.op.granted.self" : "command.op.revoked.self"));
    }
}
