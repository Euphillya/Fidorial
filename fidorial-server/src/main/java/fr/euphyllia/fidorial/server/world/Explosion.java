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
import java.util.List;
import java.util.Set;

public final class Explosion {

    private static final Set<String> INDESTRUCTIBLE = Set.of(
            "minecraft:bedrock",
            "minecraft:barrier",
            "minecraft:end_portal_frame",
            "minecraft:command_block",
            "minecraft:obsidian",
            "minecraft:crying_obsidian",
            "minecraft:water",
            "minecraft:lava");

    private Explosion() {
    }

    public static void explode(ServerWorld world, Location center, float power, AbstractEntity source) {
        FidorialServer server = FidorialServer.getInstance();
        destroyBlocks(server, world, center, power);
        damageEntities(server, world, center, power, source);
    }

    private static void destroyBlocks(FidorialServer server, ServerWorld world,
                                      Location center, float power) {
        int radius = (int) Math.ceil(power);
        int centerX = (int) Math.floor(center.x());
        int centerY = (int) Math.floor(center.y());
        int centerZ = (int) Math.floor(center.z());
        double radiusSq = power * power;

        List<BlockPos> destroyed = new ArrayList<>();
        for (int dx = -radius; dx <= radius; dx++) {
            for (int dy = -radius; dy <= radius; dy++) {
                for (int dz = -radius; dz <= radius; dz++) {
                    if (dx * dx + dy * dy + dz * dz > radiusSq) {
                        continue;
                    }
                    BlockPos pos = new BlockPos(centerX + dx, centerY + dy, centerZ + dz);
                    BlockState state = BlockView.blockAt(world, pos.x(), pos.y(), pos.z());
                    if (state == null || state.isAir() || INDESTRUCTIBLE.contains(state.name())) {
                        continue;
                    }
                    if (server.blockEdits().set(world, pos, BlockState.AIR)) {
                        destroyed.add(pos);
                    }
                }
            }
        }

        for (int i = 0; i < destroyed.size(); i += 5) {
            server.broadcast(new ClientboundLevelEventPacket(
                    ClientboundLevelEventPacket.BLOCK_BREAK, destroyed.get(i), 0, false));
        }
    }

    private static void damageEntities(FidorialServer server, ServerWorld world,
                                       Location center, float power, AbstractEntity source) {
        double range = power * 2.0;
        double rangeSq = range * range;

        for (var entity : world.entities()) {
            if (!(entity instanceof AbstractEntity abstractEntity)
                    || abstractEntity == source || abstractEntity.isRemoved()) {
                continue;
            }
            Location pos = abstractEntity.location();
            double dx = pos.x() - center.x();
            double dy = pos.y() + 1.0 - center.y();
            double dz = pos.z() - center.z();
            double distSq = dx * dx + dy * dy + dz * dz;
            if (distSq > rangeSq) {
                continue;
            }
            double distance = Math.sqrt(distSq);
            double impact = 1.0 - distance / range;
            float damage = (float) ((impact * impact + impact) * 3.5 * power + 1.0);

            double knockX = 0.0;
            double knockY = 0.3;
            double knockZ = 0.0;
            if (distance > 1.0E-4) {
                double strength = impact * 1.2;
                knockX = dx / distance * strength;
                knockY = Math.min(dy / distance * strength + 0.35, 1.0);
                knockZ = dz / distance * strength;
            }

            switch (abstractEntity) {
                case ServerPlayer player -> {
                    GameMode mode = player.gameMode();
                    if (mode == GameMode.CREATIVE || mode == GameMode.SPECTATOR) {
                        continue;
                    }
                    player.setHealth(player.health() - damage);
                    player.connection().send(new ClientboundSetHealthPacket(player.health(), 20, 5f));
                    player.connection().send(new ClientboundSetEntityMotionPacket(
                            player.entityId(), knockX, knockY, knockZ));
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
}
