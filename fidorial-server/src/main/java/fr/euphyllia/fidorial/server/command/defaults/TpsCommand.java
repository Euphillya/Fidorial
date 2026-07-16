package fr.euphyllia.fidorial.server.command.defaults;

import fr.euphyllia.fidorial.api.command.CommandExecutor;
import fr.euphyllia.fidorial.api.command.CommandSender;
import fr.euphyllia.fidorial.server.FidorialServer;
import fr.euphyllia.fidorial.server.schedulers.ThreadedRegionRegionizer.RegionTpsSnapshot;

import java.util.List;
import java.util.Locale;

public final class TpsCommand implements CommandExecutor {

    private static final int MAX_LINES = 10;

    @Override
    public void execute(CommandSender sender, String label, String[] args) {
        if (!sender.hasPermission("fidorial.command.tps")) {
            sender.sendMessage("Vous n'avez pas la permission d'utiliser cette commande.");
            return;
        }
        List<RegionTpsSnapshot> snapshots =
                FidorialServer.getInstance().regionizer().tpsSnapshots();

        if (snapshots.isEmpty()) {
            sender.sendMessage("Aucune region active (ou pas encore assez de ticks mesures).");
            return;
        }

        double worstTps = Double.MAX_VALUE;
        double sumTps = 0;
        for (RegionTpsSnapshot s : snapshots) {
            worstTps = Math.min(worstTps, s.tps());
            sumTps += s.tps();
        }

        sender.sendMessage(String.format(Locale.ROOT,
                "Regions actives : %d | TPS min : %.1f | TPS moyen : %.1f",
                snapshots.size(), worstTps, sumTps / snapshots.size()));

        int shown = Math.min(snapshots.size(), MAX_LINES);
        for (int i = 0; i < shown; i++) {
            RegionTpsSnapshot s = snapshots.get(i);
            sender.sendMessage(String.format(Locale.ROOT,
                    " - %s [%d,%d] (chunks %d,%d) : %.1f TPS, %.2f ms/tick, %d tache(s), %d ticket(s)",
                    s.world(), s.sectionX(), s.sectionZ(),
                    s.originChunkX(), s.originChunkZ(),
                    s.tps(), s.msptAvg(), s.queuedTasks(), s.tickets()));
        }
        if (snapshots.size() > shown) {
            sender.sendMessage("   ... et " + (snapshots.size() - shown) + " autre(s) region(s)");
        }
    }
}
