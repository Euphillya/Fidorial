package fr.euphyllia.fidorial.server.command.defaults;

import fr.euphyllia.fidorial.api.command.CommandExecutor;
import fr.euphyllia.fidorial.api.command.CommandSender;
import fr.euphyllia.fidorial.api.entity.Player;
import fr.euphyllia.fidorial.server.FidorialServer;

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
            sender.sendMessage("Vous n'avez pas la permission d'utiliser cette commande.");
            return;
        }
        if (args.length != 1) {
            sender.sendMessage("Usage : /" + label + " <joueur>");
            return;
        }
        Player target = FidorialServer.getInstance().player(args[0]).orElse(null);
        if (target == null) {
            sender.sendMessage("Joueur introuvable : " + args[0]);
            return;
        }
        if (target.isOp() == grant) {
            sender.sendMessage(target.name() + (grant
                    ? " est deja operateur." : " n'est pas operateur."));
            return;
        }
        target.setOp(grant);
        sender.sendMessage(grant
                ? target.name() + " est maintenant operateur."
                : target.name() + " n'est plus operateur.");
        target.sendMessage(grant
                ? "Vous etes maintenant operateur."
                : "Vous n'etes plus operateur.");
    }
}
