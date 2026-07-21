package fr.euphyllia.fidorial.server.command.defaults;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.tree.LiteralCommandNode;
import fr.euphyllia.fidorial.server.FidorialServer;
import fr.euphyllia.fidorial.server.schedulers.ThreadedRegionRegionizer.RegionTpsSnapshot;
import fr.fidorial.command.CommandSource;
import fr.fidorial.command.CommandTree;
import net.kyori.adventure.text.Component;

import java.util.List;
import java.util.Locale;

public final class TpsCommand {

    private static final int MAX_LINES = 10;

    private TpsCommand() {
    }

    public static CommandTree create() {
        LiteralCommandNode<CommandSource> command = CommandTree.literal("tps")
                .requires(source -> source.sender().hasPermission("fidorial.command.tps"))
                .executes(TpsCommand::execute)
                .build();
        return new CommandTree(command);
    }

    private static int execute(CommandContext<CommandSource> context) {
        List<RegionTpsSnapshot> snapshots =
                FidorialServer.getInstance().regionizer().tpsSnapshots();

        if (snapshots.isEmpty()) {
            context.getSource().sender().sendMessage(Component.translatable("command.tps.noregion"));
            return Command.SINGLE_SUCCESS;
        }

        double worstTps = Double.MAX_VALUE;
        double sumTps = 0;

        for (RegionTpsSnapshot snapshot : snapshots) {
            worstTps = Math.min(worstTps, snapshot.tps());
            sumTps += snapshot.tps();
        }

        context.getSource()
                .sender()
                .sendMessage(Component.translatable(
                        "command.tps.summary",
                        Component.text(snapshots.size()),
                        Component.text(format1(worstTps)),
                        Component.text(format1(sumTps / snapshots.size()))));

        int shown = Math.min(snapshots.size(), MAX_LINES);

        for (int i = 0; i < shown; i++) {
            RegionTpsSnapshot snapshot = snapshots.get(i);

            context.getSource()
                    .sender()
                    .sendMessage(Component.translatable(
                            "command.tps.line",
                            Component.text(snapshot.world()),
                            Component.text(snapshot.sectionX()),
                            Component.text(snapshot.sectionZ()),
                            Component.text(snapshot.originChunkX()),
                            Component.text(snapshot.originChunkZ()),
                            Component.text(format1(snapshot.tps())),
                            Component.text(String.format(Locale.ROOT, "%.2f", snapshot.msptAvg())),
                            Component.text(snapshot.queuedTasks()),
                            Component.text(snapshot.tickets())));
        }

        if (snapshots.size() > shown) {
            context.getSource()
                    .sender()
                    .sendMessage(Component.translatable("command.tps.more", Component.text(snapshots.size() - shown)));
        }

        return Command.SINGLE_SUCCESS;
    }

    private static String format1(double value) {
        return String.format(Locale.ROOT, "%.1f", value);
    }
}
