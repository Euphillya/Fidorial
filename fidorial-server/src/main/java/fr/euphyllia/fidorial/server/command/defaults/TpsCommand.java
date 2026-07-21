package fr.euphyllia.fidorial.server.command.defaults;

import fr.euphyllia.fidorial.server.FidorialServer;
import fr.euphyllia.fidorial.server.schedulers.ThreadedRegionRegionizer.RegionTpsSnapshot;
import fr.fidorial.command.CommandExecutor;
import fr.fidorial.command.CommandSender;
import net.kyori.adventure.text.Component;

import java.util.List;
import java.util.Locale;

public final class TpsCommand implements CommandExecutor {

    private static final int MAX_LINES = 10;

    @Override
    public void execute(CommandSender sender, String label, String[] args) {
        if (!sender.hasPermission("fidorial.command.tps")) {
            sender.sendMessage(Component.translatable("command.error.nopermission"));
            return;
        }
        List<RegionTpsSnapshot> snapshots =
                FidorialServer.getInstance().regionizer().tpsSnapshots();

        if (snapshots.isEmpty()) {
            sender.sendMessage(Component.translatable("command.tps.noregion"));
            return;
        }

        double worstTps = Double.MAX_VALUE;
        double sumTps = 0;
        for (RegionTpsSnapshot s : snapshots) {
            worstTps = Math.min(worstTps, s.tps());
            sumTps += s.tps();
        }

        sender.sendMessage(Component.translatable("command.tps.summary",
                Component.text(snapshots.size()),
                Component.text(format1(worstTps)),
                Component.text(format1(sumTps / snapshots.size()))));

        int shown = Math.min(snapshots.size(), MAX_LINES);
        for (int i = 0; i < shown; i++) {
            RegionTpsSnapshot s = snapshots.get(i);
            sender.sendMessage(Component.translatable("command.tps.line",
                    Component.text(s.world()),
                    Component.text(s.sectionX()),
                    Component.text(s.sectionZ()),
                    Component.text(s.originChunkX()),
                    Component.text(s.originChunkZ()),
                    Component.text(format1(s.tps())),
                    Component.text(String.format(Locale.ROOT, "%.2f", s.msptAvg())),
                    Component.text(s.queuedTasks()),
                    Component.text(s.tickets())));
        }
        if (snapshots.size() > shown) {
            sender.sendMessage(Component.translatable("command.tps.more",
                    Component.text(snapshots.size() - shown)));
        }
    }

    private static String format1(double value) {
        return String.format(Locale.ROOT, "%.1f", value);
    }
}
