package fr.euphyllia.fidorial.server.world.fluid;

import fr.euphyllia.fidorial.server.protocol.packet.ClientboundPacket;
import fr.euphyllia.fidorial.server.protocol.packet.clientbound.play.ClientboundBlockUpdatePacket;
import fr.euphyllia.fidorial.server.schedulers.ThreadedRegionRegionizer;
import fr.euphyllia.fidorial.server.world.BlockStateRegistry;
import fr.euphyllia.fidorial.server.world.ServerWorld;
import fr.euphyllia.fidorial.server.world.WorldManager;
import fr.euphyllia.fidorial.server.world.chunk.BlockState;
import fr.euphyllia.fidorial.server.world.storage.Dimension;
import fr.fidorial.world.BlockFace;
import fr.fidorial.world.BlockPos;
import fr.fidorial.world.ChunkPos;
import fr.fidorial.world.fluid.FluidManager;
import fr.fidorial.world.fluid.FluidState;
import fr.fidorial.world.fluid.FluidType;
import net.kyori.adventure.text.logger.slf4j.ComponentLogger;
import org.jspecify.annotations.Nullable;

import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

public final class FluidEngine implements FluidManager {

    private static final ComponentLogger LOGGER = ComponentLogger.logger(FluidEngine.class);

    private static final BlockState OBSIDIAN = BlockState.of("minecraft:obsidian");
    private static final BlockState COBBLESTONE = BlockState.of("minecraft:cobblestone");

    private static final BlockFace[] HORIZONTAL = {
        BlockFace.NORTH, BlockFace.SOUTH,
        BlockFace.WEST, BlockFace.EAST
    };

    private final WorldManager worlds;
    private final ThreadedRegionRegionizer regionizer;
    private final BlockStateRegistry blockRegistry;
    private final Consumer<ClientboundPacket> broadcaster;

    private final Map<String, Set<Long>> pending = new ConcurrentHashMap<>();

    public FluidEngine(
            WorldManager worlds,
            ThreadedRegionRegionizer regionizer,
            BlockStateRegistry blockRegistry,
            Consumer<ClientboundPacket> broadcaster
    ) {
        this.worlds = worlds;
        this.regionizer = regionizer;
        this.blockRegistry = blockRegistry;
        this.broadcaster = broadcaster;
    }

    private static long pack(int x, int y, int z) {
        return ((x & 0x3FFFFFFL) << 38) | ((z & 0x3FFFFFFL) << 12) | (y & 0xFFFL);
    }

    @Override
    public FluidState fluidAt(String world, int x, int y, int z) {
        ServerWorld w = worldByName(world);
        if (w == null) {
            return FluidState.empty();
        }
        try {
            return FluidBlockCodec.fromBlock(w.getBlock(x, y, z));
        } catch (IOException e) {
            LOGGER.error("Lecture du fluide impossible en {},{},{}", x, y, z, e);
            return FluidState.empty();
        }
    }

    @Override
    public boolean placeSource(String world, int x, int y, int z, FluidType type) {
        ServerWorld w = worldByName(world);
        if (w == null) {
            return false;
        }
        boolean applied = setAndBroadcast(w, x, y, z, FluidBlockCodec.toBlock(FluidState.source(type)));
        if (applied) {
            schedule(world, x, y, z, type.tickDelay());
        }
        return applied;
    }

    @Override
    public boolean removeFluid(String world, int x, int y, int z) {
        ServerWorld w = worldByName(world);
        if (w == null) {
            return false;
        }
        if (fluidAt(world, x, y, z).isEmpty()) {
            return false;
        }
        boolean applied = setAndBroadcast(w, x, y, z, BlockState.AIR);
        if (applied) {
            notifyBlockChanged(world, x, y, z);
        }
        return applied;
    }

    @Override
    public void scheduleUpdate(String world, int x, int y, int z) {
        FluidState state = fluidAt(world, x, y, z);
        if (state.type() != null) {
            schedule(world, x, y, z, state.type().tickDelay());
        }
    }

    @Override
    public void notifyBlockChanged(String world, int x, int y, int z) {
        scheduleUpdate(world, x, y, z);
        for (BlockFace dir : BlockFace.values()) {
            scheduleUpdate(world, x + dir.dx(), y + dir.dy(), z + dir.dz());
        }
    }

    private void schedule(String world, int x, int y, int z, int delayTicks) {
        Set<Long> set = pending.computeIfAbsent(world, k -> ConcurrentHashMap.newKeySet());
        long key = pack(x, y, z);
        if (!set.add(key)) {
            return;
        }
        regionizer.executeDelayed(
                world,
                ChunkPos.fromBlock(x, z),
                () -> {
                    set.remove(key);
                    try {
                        tick(world, x, y, z);
                    } catch (Throwable t) {
                        LOGGER.error("Tick fluide impossible en {},{},{}", x, y, z, t);
                    }
                },
                delayTicks);
    }

    private void tick(String worldName, int x, int y, int z) throws IOException {
        ServerWorld world = worldByName(worldName);
        if (world == null) {
            return;
        }

        FluidState self = FluidBlockCodec.fromBlock(world.getBlock(x, y, z));
        if (self.isEmpty()) {
            return;
        }
        FluidType type = self.type();

        // 1) Interactions lave <-> eau : la lave touchée par de l'eau se fige.
        if (type == FluidType.LAVA && touches(world, x, y, z, FluidType.WATER)) {
            BlockState solidified = self.isSource() ? OBSIDIAN : COBBLESTONE;
            if (setAndBroadcast(world, x, y, z, solidified)) {
                notifyBlockChanged(worldName, x, y, z);
            }
            return;
        }

        // 2) Recalcul du niveau pour les fluides en écoulement.
        if (!self.isSource()) {
            FluidState recomputed = recomputeLevel(world, worldName, x, y, z, self);
            if (recomputed == null) {
                return; // le bloc s'est asséché
            }
            self = recomputed;
        }

        // 3) Écoulement vertical prioritaire, sinon étalement horizontal.
        if (type != null && !flowDown(world, worldName, x, y, z, type)) {
            spreadHorizontally(world, worldName, x, y, z, self);
        }
    }

    private @Nullable FluidState recomputeLevel(
            ServerWorld world,
            String worldName,
            int x,
            int y,
            int z,
            FluidState self
    ) throws IOException {
        FluidType type = self.type();
        if (type == null) {
            return null;
        }
        FluidState above = FluidBlockCodec.fromBlock(world.getBlock(x, y + 1, z));
        boolean fedFromAbove = above.type() == type;

        int best = Integer.MAX_VALUE;
        int adjacentSources = 0;
        for (BlockFace dir : HORIZONTAL) {
            FluidState n = FluidBlockCodec.fromBlock(world.getBlock(x + dir.dx(), y, z + dir.dz()));
            if (n.type() == type) {
                best = Math.min(best, n.effectiveLevel() + type.dropOff());
                if (n.isSource()) {
                    adjacentSources++;
                }
            }
        }

        // Source infinie : deux sources voisines + support en dessous.
        if (type.canFormSources() && adjacentSources >= 2) {
            BlockState belowBlock = world.getBlock(x, y - 1, z);
            FluidState belowFluid = FluidBlockCodec.fromBlock(belowBlock);
            boolean supported = (!belowBlock.isAir() && belowFluid.isEmpty()) || belowFluid.isSource();
            if (supported) {
                return applyIfChanged(world, worldName, x, y, z, self, FluidState.source(type));
            }
        }

        FluidState wanted;
        if (fedFromAbove) {
            wanted = FluidState.fallingFluid(type);
        } else if (best <= type.maxSpreadLevel()) {
            wanted = FluidState.flowing(type, best);
        } else {
            // Plus alimenté : assèchement.
            if (setAndBroadcast(world, x, y, z, BlockState.AIR)) {
                notifyBlockChanged(worldName, x, y, z);
            }
            return null;
        }
        return applyIfChanged(world, worldName, x, y, z, self, wanted);
    }

    private FluidState applyIfChanged(
            ServerWorld world, String worldName, int x, int y, int z, FluidState current, FluidState wanted)
            throws IOException {
        if (wanted.equals(current)) {
            return current;
        }
        if (setAndBroadcast(world, x, y, z, FluidBlockCodec.toBlock(wanted))) {
            notifyBlockChanged(worldName, x, y, z);
        }
        return wanted;
    }

    private boolean flowDown(ServerWorld world, String worldName, int x, int y, int z, FluidType type)
            throws IOException {
        BlockState belowBlock = world.getBlock(x, y - 1, z);
        FluidState below = FluidBlockCodec.fromBlock(belowBlock);

        // La lave qui tombe dans l'eau se fige en pierre taillée.
        if (type == FluidType.LAVA && below.type() == FluidType.WATER) {
            if (setAndBroadcast(world, x, y - 1, z, COBBLESTONE)) {
                notifyBlockChanged(worldName, x, y - 1, z);
            }
            return false;
        }

        if (below.type() == type) {
            if (below.isSource() || below.falling()) {
                return true; // la colonne coule déjà
            }
            // Un écoulement horizontal en dessous est remplacé par une chute.
            if (setAndBroadcast(world, x, y - 1, z, FluidBlockCodec.toBlock(FluidState.fallingFluid(type)))) {
                schedule(worldName, x, y - 1, z, type.tickDelay());
            }
            return true;
        }

        if (belowBlock.isAir()) {
            if (setAndBroadcast(world, x, y - 1, z, FluidBlockCodec.toBlock(FluidState.fallingFluid(type)))) {
                schedule(worldName, x, y - 1, z, type.tickDelay());
            }
            return true;
        }
        if (below.type() != null) {
            schedule(worldName, x, y - 1, z, below.type().tickDelay());
        }
        return false; // bloqué : étalement horizontal
    }

    private void spreadHorizontally(ServerWorld world, String worldName, int x, int y, int z, FluidState self)
            throws IOException {
        FluidType type = self.type();
        if (type == null) {
            return;
        }
        int spreadLevel = self.effectiveLevel() + type.dropOff();
        if (spreadLevel > type.maxSpreadLevel()) {
            return;
        }
        for (BlockFace dir : HORIZONTAL) {
            int tx = x + dir.dx();
            int tz = z + dir.dz();
            BlockState targetBlock = world.getBlock(tx, y, tz);
            FluidState target = FluidBlockCodec.fromBlock(targetBlock);

            boolean canFlow = targetBlock.isAir()
                    || (target.type() == type && !target.isSource() && target.effectiveLevel() > spreadLevel);
            if (canFlow) {
                if (setAndBroadcast(world, tx, y, tz, FluidBlockCodec.toBlock(FluidState.flowing(type, spreadLevel)))) {
                    schedule(worldName, tx, y, tz, type.tickDelay());
                }
            } else if (target.type() != null && target.type() != type) {
                schedule(worldName, tx, y, tz, target.type().tickDelay());
            }
        }
    }

    private boolean touches(ServerWorld world, int x, int y, int z, FluidType other) throws IOException {
        if (FluidBlockCodec.fromBlock(world.getBlock(x, y + 1, z)).type() == other) {
            return true;
        }
        for (BlockFace dir : HORIZONTAL) {
            if (FluidBlockCodec.fromBlock(world.getBlock(x + dir.dx(), y, z + dir.dz()))
                            .type()
                    == other) {
                return true;
            }
        }
        return false;
    }

    private boolean setAndBroadcast(ServerWorld world, int x, int y, int z, BlockState state) {
        try {
            if (!world.setBlock(x, y, z, state)) {
                return false;
            }
        } catch (IOException e) {
            LOGGER.error("Écriture du fluide impossible en {},{},{}", x, y, z, e);
            return false;
        }
        broadcaster.accept(new ClientboundBlockUpdatePacket(new BlockPos(x, y, z), blockRegistry.networkId(state)));
        return true;
    }

    private @Nullable ServerWorld worldByName(@Nullable String name) {
        if (name == null || Dimension.OVERWORLD.id().equals(name)) {
            return worlds.overworld();
        }
        int sep = name.indexOf(':');
        if (sep <= 0) {
            return null;
        }
        return worlds.dimension(Dimension.datapack(name.substring(0, sep), name.substring(sep + 1)));
    }
}
