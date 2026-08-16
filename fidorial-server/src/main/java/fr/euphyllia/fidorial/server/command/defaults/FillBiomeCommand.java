package fr.euphyllia.fidorial.server.command.defaults;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.tree.LiteralCommandNode;
import fr.euphyllia.fidorial.server.FidorialServer;
import fr.euphyllia.fidorial.server.entity.player.ServerPlayer;
import fr.euphyllia.fidorial.server.network.protocol.packet.clientbound.play.ClientboundChunksBiomesPacket;
import fr.euphyllia.fidorial.server.world.ServerWorld;
import fr.euphyllia.fidorial.server.world.chunk.ChunkColumn;
import fr.fidorial.command.CommandSource;
import fr.fidorial.command.argument.ArgumentTypes;
import fr.fidorial.command.argument.resolvers.BlockPosResolver;
import fr.fidorial.entity.Entity;
import fr.fidorial.registry.RegistryKey;
import fr.fidorial.registry.TypedKey;
import fr.fidorial.registry.data.Biome;
import fr.fidorial.world.BlockPos;
import fr.fidorial.world.ChunkPos;
import fr.fidorial.world.biome.BiomeRegistry;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import org.jspecify.annotations.Nullable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import static fr.fidorial.command.Commands.argument;
import static fr.fidorial.command.Commands.literal;

public class FillBiomeCommand {

    private static final String PERMISSION = "fidorial.command.fillbiome";

    private FillBiomeCommand() {
        throw new UnsupportedOperationException("FillBiomeCommand cannot be instantiated.");
    }

    public static LiteralCommandNode<CommandSource> create() {
        return literal("fillbiome")
                .requires(source -> source.sender().hasPermission(PERMISSION))
                .then(argument("from", ArgumentTypes.blockPosition())
                        .then(argument("to", ArgumentTypes.blockPosition())
                                .then(argument("biome", ArgumentTypes.resourceKey(RegistryKey.BIOME))
                                        .executes(context -> fill(context, null))
                                        .then(literal("replace")
                                                .then(argument("filter", ArgumentTypes.resourceKey(RegistryKey.BIOME))
                                                        .executes(context -> fill(
                                                                context,
                                                                context.getArgument("filter", TypedKey.class).key())))))))
                .build();
    }

    private static int fill(
            final CommandContext<CommandSource> context,
            final @Nullable Key filter
    ) throws CommandSyntaxException {

        final FidorialServer server = FidorialServer.getInstance();
        final BiomeRegistry biomes = server.biomes();

        @SuppressWarnings("unchecked")
        final TypedKey<Biome> target = context.getArgument("biome", TypedKey.class);
        final Key biome = target.key();

        if (!biomes.contains(biome)) {
            context.getSource().sender().sendMessage(
                    Component.translatable("command.fillbiome.unknown", Component.text(biome.asString())));
            return 0;
        }

        if (filter != null && !biomes.contains(filter)) {
            context.getSource().sender().sendMessage(
                    Component.translatable("command.fillbiome.unknown", Component.text(filter.asString())));
            return 0;
        }

        final ServerWorld world = worldOf(context);
        if (world == null) {
            context.getSource().sender().sendMessage(Component.translatable("command.fillbiome.console"));
            return 0;
        }
        final BlockPos from = context.getArgument("from", BlockPosResolver.class).resolve(context.getSource());
        final BlockPos to = context.getArgument("to", BlockPosResolver.class).resolve(context.getSource());

        final int minX = Math.min(from.x(), to.x());
        final int minZ = Math.min(from.z(), to.z());
        final int maxX = Math.max(from.x(), to.x());
        final int maxZ = Math.max(from.z(), to.z());

        final int floor = world.minY();
        final int ceiling = floor + world.height() - 1;
        final int minY = Math.max(floor, Math.min(from.y(), to.y()));
        final int maxY = Math.min(ceiling, Math.max(from.y(), to.y()));

        if (minY > maxY) {
            context.getSource().sender().sendMessage(Component.translatable("command.fillbiome.outofworld"));
            return 0;
        }

        final Set<ChunkPos> touched = new LinkedHashSet<>();
        int changed = 0;

        try {
            for (int x = minX & ~3; x <= maxX; x += 4) {
                for (int z = minZ & ~3; z <= maxZ; z += 4) {
                    for (int y = minY & ~3; y <= maxY; y += 4) {
                        if (filter != null && !filter.equals(world.getBiome(x, y, z))) {
                            continue;
                        }
                        if (world.setBiome(x, y, z, biome)) {
                            changed++;
                            touched.add(new ChunkPos(x >> 4, z >> 4));
                        }
                    }
                }
            }
        } catch (final IOException failure) {
            context.getSource().sender().sendMessage(Component.translatable(
                    "command.fillbiome.failed", Component.text(String.valueOf(failure.getMessage()))));
            return 0;
        }

        resend(server, world, touched);

        context.getSource().sender().sendMessage(Component.translatable(
                "command.fillbiome.success",
                Component.text(changed),
                Component.text(biome.asString())));

        return changed == 0 ? 0 : Command.SINGLE_SUCCESS;
    }

    private static void resend(final FidorialServer server, final ServerWorld world, final Set<ChunkPos> chunks) {
        final List<ChunkColumn> columns = new ArrayList<>(chunks.size());
        for (final ChunkPos pos : chunks) {
            try {
                columns.add(world.getChunk(pos.x(), pos.z()));
            } catch (final IOException ignored) {
            }

            if (columns.isEmpty()) {
                return;
            }

            final ClientboundChunksBiomesPacket packet =
                    new ClientboundChunksBiomesPacket(server.chunkSerializer(), columns);

            for (final ServerPlayer player : server.players()) {
                if (!player.isRemoved() && player.world() == world) {
                    player.connection().send(packet);
                }
            }
        }
    }

    private static @Nullable ServerWorld worldOf(final CommandContext<CommandSource> context) {
        if (context.getSource().sender() instanceof final ServerPlayer player
                && player.world() instanceof final ServerWorld world) {
            return world;
        }

        final Entity executor = context.getSource().executor();
        if (executor != null && executor.world() instanceof final ServerWorld world) {
            return world;
        }

        return null;
    }
}
