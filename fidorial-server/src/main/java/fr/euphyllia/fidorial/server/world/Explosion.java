package fr.euphyllia.fidorial.server.world;

import fr.euphyllia.fidorial.server.FidorialServer;
import fr.euphyllia.fidorial.server.entity.AbstractEntity;
import fr.euphyllia.fidorial.server.entity.ai.BlockView;
import fr.euphyllia.fidorial.server.entity.mob.Mob;
import fr.euphyllia.fidorial.server.entity.player.ServerPlayer;
import fr.euphyllia.fidorial.server.protocol.packet.clientbound.play.ClientboundHurtAnimationPacket;
import fr.euphyllia.fidorial.server.protocol.packet.clientbound.play.ClientboundLevelEventPacket;
import fr.euphyllia.fidorial.server.protocol.packet.clientbound.play.ClientboundSetEntityMotionPacket;
import fr.euphyllia.fidorial.server.protocol.packet.clientbound.play.ClientboundSetHealthPacket;
import fr.euphyllia.fidorial.server.world.chunk.BlockState;
import fr.fidorial.entity.GameMode;
import fr.fidorial.world.BlockPos;
import fr.fidorial.world.Location;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Source used: <a href="https://minecraft.wiki/w/Explosion">Explosion Wiki</a>
 */
public final class Explosion {

    private static final float STEP = 0.3f;
    private static final float AIR_ATTENUATION = 0.225f;

    private static final float DEFAULT_BLAST_RESISTANCE = 0.5f;
    // TODO : Add the `BLAST_RESISTANCE` method to BlockState.
    private static final Map<String, Float> BLAST_RESISTANCE = Map.ofEntries(
            Map.entry("minecraft:bedrock", 3_600_000f),
            Map.entry("minecraft:barrier", 3_600_000f),
            Map.entry("minecraft:command_block", 3_600_000f),
            Map.entry("minecraft:end_portal_frame", 3_600_000f),
            Map.entry("minecraft:obsidian", 1_200f),
            Map.entry("minecraft:crying_obsidian", 1_200f),
            Map.entry("minecraft:respawn_anchor", 1_200f),
            Map.entry("minecraft:reinforced_deepslate", 1_200f),
            Map.entry("minecraft:ancient_debris", 1_200f),
            Map.entry("minecraft:netherite_block", 1_200f),
            Map.entry("minecraft:enchanting_table", 1_200f),
            Map.entry("minecraft:ender_chest", 600f),
            Map.entry("minecraft:water", 100f),
            Map.entry("minecraft:lava", 100f),
            Map.entry("minecraft:end_stone", 9f),
            Map.entry("minecraft:stone", 6f),
            Map.entry("minecraft:cobblestone", 6f),
            Map.entry("minecraft:deepslate", 6f),
            Map.entry("minecraft:granite", 6f),
            Map.entry("minecraft:diorite", 6f),
            Map.entry("minecraft:andesite", 6f),
            Map.entry("minecraft:bricks", 6f),
            Map.entry("minecraft:planks", 3f),
            Map.entry("minecraft:glass", 0.3f),
            Map.entry("minecraft:leaves", 0.2f));

    private Explosion() {
    }

    public static void explode(ServerWorld world, Location center, float power, AbstractEntity source) {
        FidorialServer server = FidorialServer.getInstance();
        destroyBlocks(server, world, center, power);
        damageEntities(server, world, center, power, source);
    }

    private static void destroyBlocks(FidorialServer server, ServerWorld world, Location center, float power) {
        Set<BlockPos> toDestroy = collectExplodedBlocks(world, center, power);
        List<BlockPos> destroyed = new ArrayList<>(toDestroy.size());

        for (BlockPos pos : toDestroy) {
            if (server.blockEdits().set(world, pos, BlockState.AIR)) {
                destroyed.add(pos);
            }
            if (ThreadLocalRandom.current().nextFloat() < 1.0f / power) {
                // TODO : drop item
            }
        }

        for (int i = 0; i < destroyed.size(); i += 5) {
            server.broadcast(new ClientboundLevelEventPacket(
                    ClientboundLevelEventPacket.BLOCK_BREAK, destroyed.get(i), 0, false));
        }
    }

    private static Set<BlockPos> collectExplodedBlocks(ServerWorld world, Location center, float power) {
        Set<BlockPos> out = new HashSet<>();
        var random = ThreadLocalRandom.current();
        double ox = center.x(), oy = center.y(), oz = center.z();

        for (int j = 0; j < 16; j++) {
            for (int k = 0; k < 16; k++) {
                for (int l = 0; l < 16; l++) {
                    if (j != 0 && j != 15 && k != 0 && k != 15 && l != 0 && l != 15) {
                        continue;
                    }
                    double dx = j / 15.0 * 2.0 - 1.0;
                    double dy = k / 15.0 * 2.0 - 1.0;
                    double dz = l / 15.0 * 2.0 - 1.0;
                    double norm = Math.sqrt(dx * dx + dy * dy + dz * dz);
                    dx /= norm;
                    dy /= norm;
                    dz /= norm;

                    float intensity = power * (0.7f + random.nextFloat() * 0.6f);
                    double px = ox, py = oy, pz = oz;

                    for (; intensity > 0.0f; intensity -= AIR_ATTENUATION) {
                        int bx = (int) Math.floor(px);
                        int by = (int) Math.floor(py);
                        int bz = (int) Math.floor(pz);
                        BlockState state = BlockView.blockAt(world, bx, by, bz);
                        if (state != null && !state.isAir()) {
                            intensity -= (resistanceOf(state) + 0.3f) * 0.3f;
                            if (intensity > 0.0f) {
                                out.add(new BlockPos(bx, by, bz));
                            }
                        }
                        px += dx * STEP;
                        py += dy * STEP;
                        pz += dz * STEP;
                    }
                }
            }
        }
        return out;
    }

    private static float resistanceOf(BlockState state) {
        return BLAST_RESISTANCE.getOrDefault(
                state.name(), DEFAULT_BLAST_RESISTANCE); // Todo : Add the `BLAST_RESISTANCE` method to BlockState.
    }

    private static void damageEntities(
            FidorialServer server,
            ServerWorld world,
            Location center,
            float power,
            AbstractEntity source
    ) {
        double range = power * 2.0;
        double rangeSq = range * range;
        double cx = center.x(), cy = center.y(), cz = center.z();

        for (var entity : world.entities()) {
            if (!(entity instanceof AbstractEntity abstractEntity)
                    || abstractEntity == source
                    || abstractEntity.isRemoved()) {
                continue;
            }
            Location pos = abstractEntity.location();

            double fx = pos.x() - cx;
            double fy = pos.y() - cy;
            double fz = pos.z() - cz;
            double feetDistSq = fx * fx + fy * fy + fz * fz;
            if (feetDistSq > rangeSq) {
                continue;
            }
            double feetDist = Math.sqrt(feetDistSq);
            double exposure = getExposure(world, center, abstractEntity);
            double impact = (1.0 - feetDist / range) * exposure;
            float damage = (float) ((impact * impact + impact) / 2.0 * 7.0 * range + 1.0);

            double eye = eyeHeight(abstractEntity);
            double ex = pos.x() - cx;
            double ey = pos.y() + eye - cy;
            double ez = pos.z() - cz;
            double eyeDist = Math.sqrt(ex * ex + ey * ey + ez * ez);
            double knockX = 0.0, knockY = 0.0, knockZ = 0.0;
            if (eyeDist > 1.0E-4) {
                knockX = ex / eyeDist * impact;
                knockY = ey / eyeDist * impact;
                knockZ = ez / eyeDist * impact;
            }

            switch (abstractEntity) {
                case ServerPlayer player -> {
                    GameMode mode = player.gameMode();
                    if (mode == GameMode.CREATIVE || mode == GameMode.SPECTATOR) {
                        continue;
                    }

                    float finalDamage = damage;
                    // Todo : Uncomment once implemented.
                    //                    switch (world.difficulty()) {
                    //                        case PEACEFUL -> { continue; }
                    //                        case EASY     -> finalDamage = Math.min(damage / 2f + 1f, damage);
                    //                        case NORMAL   -> finalDamage = damage;
                    //                        case HARD     -> finalDamage = damage * 1.5f;
                    //                    }

                    player.setHealth(player.health() - finalDamage);
                    player.connection().send(new ClientboundSetHealthPacket(player.health(), 20, 5f));
                    player.connection()
                            .send(new ClientboundSetEntityMotionPacket(player.entityId(), knockX, knockY, knockZ));
                    server.broadcast(new ClientboundHurtAnimationPacket(player.entityId(), pos.yaw()));
                }
                case Mob mob -> {
                    mob.setHealth(mob.health() - damage);
                    if (!mob.isRemoved()) {
                        server.broadcast(new ClientboundHurtAnimationPacket(mob.entityId(), 0f));
                    }
                }
                default -> {
                }
            }
        }
    }

    private static float getExposure(ServerWorld world, Location center, AbstractEntity entity) {
        double[] box = boundingBox(entity);
        double width = box[3] - box[0];
        double height = box[4] - box[1];

        double stepX = 1.0 / (width * 2.0 + 1.0);
        double stepY = 1.0 / (height * 2.0 + 1.0);
        double stepZ = stepX;
        if (stepX <= 0.0 || stepY <= 0.0) {
            return 0.0f;
        }
        double offX = (1.0 - Math.floor(1.0 / stepX) * stepX) / 2.0;
        double offZ = (1.0 - Math.floor(1.0 / stepZ) * stepZ) / 2.0;

        int clear = 0, total = 0;
        for (double fx = 0.0; fx <= 1.0; fx += stepX) {
            for (double fy = 0.0; fy <= 1.0; fy += stepY) {
                for (double fz = 0.0; fz <= 1.0; fz += stepZ) {
                    double sx = lerp(fx, box[0], box[3]) + offX;
                    double sy = lerp(fy, box[1], box[4]);
                    double sz = lerp(fz, box[2], box[5]) + offZ;
                    if (clearLineOfSight(world, sx, sy, sz, center.x(), center.y(), center.z())) {
                        clear++;
                    }
                    total++;
                }
            }
        }
        return total == 0 ? 0.0f : (float) clear / total;
    }

    private static boolean clearLineOfSight(
            ServerWorld world,
            double sx,
            double sy,
            double sz,
            double cx,
            double cy,
            double cz
    ) {

        double dx = cx - sx, dy = cy - sy, dz = cz - sz;
        double dist = Math.sqrt(dx * dx + dy * dy + dz * dz);
        if (dist < 1.0E-6) {
            return true;
        }
        int steps = (int) Math.ceil(dist / 0.3);
        double stepX = dx / steps, stepY = dy / steps, stepZ = dz / steps;
        double x = sx, y = sy, z = sz;
        for (int i = 0; i < steps; i++) {
            x += stepX;
            y += stepY;
            z += stepZ;
            BlockState state = BlockView.blockAt(world, (int) Math.floor(x), (int) Math.floor(y), (int) Math.floor(z));
            if (state != null && !state.isAir() && !isFluid(state)) {
                return false;
            }
        }
        return true;
    }

    private static boolean isFluid(BlockState state) {
        String name = state.name();
        return name.equals("minecraft:water") || name.equals("minecraft:lava"); // Todo : Add isFluid to BlockState
    }

    private static double eyeHeight(AbstractEntity entity) {
        double[] box = boundingBox(entity);
        return (box[4] - box[1]) * 0.85;
    }

    private static double[] boundingBox(AbstractEntity entity) {
        double width = 0.6;
        double height = 1.8;
        if (entity instanceof ServerPlayer) {
            width = 0.6;
            height = 1.8;
        } else if (entity instanceof Mob) {
            width = 0.6;
            height = 1.7;
        }
        Location loc = entity.location();
        double half = width / 2.0;
        return new double[] {loc.x() - half, loc.y(), loc.z() - half, loc.x() + half, loc.y() + height, loc.z() + half};
    }

    private static double lerp(double t, double a, double b) {
        return a + (b - a) * t;
    }
}
