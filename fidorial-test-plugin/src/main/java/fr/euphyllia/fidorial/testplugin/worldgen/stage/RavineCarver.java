package fr.euphyllia.fidorial.testplugin.worldgen.stage;

import fr.euphyllia.fidorial.testplugin.worldgen.Blk;
import fr.euphyllia.fidorial.testplugin.worldgen.ChunkScratch;
import fr.euphyllia.fidorial.testplugin.worldgen.GeneratorSettings;
import fr.euphyllia.fidorial.testplugin.worldgen.noise.Seeds;

import java.util.Random;

public final class RavineCarver {

    private static final int RARITY = 55;

    private static final int SEARCH_RADIUS = 8;

    private static final int LAVA_LEVEL = -54;

    private final GeneratorSettings settings;

    public RavineCarver(final GeneratorSettings settings) {
        this.settings = settings;
    }

    public void carve(final ChunkScratch scratch) {
        for (int dz = -SEARCH_RADIUS; dz <= SEARCH_RADIUS; dz++) {
            for (int dx = -SEARCH_RADIUS; dx <= SEARCH_RADIUS; dx++) {
                carveFrom(scratch, scratch.chunkX() + dx, scratch.chunkZ() + dz, dx << 4, dz << 4);
            }
        }
    }

    private void carveFrom(
            final ChunkScratch scratch, final int chunkX, final int chunkZ, final int offsetX, final int offsetZ) {

        final Random random = Seeds.randomForChunk(settings.seed(), chunkX, chunkZ, "carver/ravine");
        if (random.nextInt(RARITY) != 0) {
            return;
        }

        double x = random.nextInt(16) + offsetX;
        double z = random.nextInt(16) + offsetZ;
        double y = 16 + random.nextInt(40);

        double yaw = random.nextDouble() * Math.PI * 2.0;
        double pitch = (random.nextDouble() - 0.5) * 0.16;
        final double thickness = 1.6 + random.nextDouble() * 2.2;
        final int length = 84 + random.nextInt(40);

        for (int step = 0; step < length; step++) {
            final double envelope = Math.sin(Math.PI * step / length);
            final double radiusH = thickness * envelope + 0.8;
            final double radiusV = radiusH * 3.4;

            carveDisc(scratch, x, y, z, radiusH, radiusV);

            x += Math.cos(yaw);
            z += Math.sin(yaw);
            y += Math.sin(pitch) * 1.4;

            yaw += (random.nextDouble() - 0.5) * 0.28;
            pitch += (random.nextDouble() - 0.5) * 0.08;
            pitch = Math.clamp(pitch, -0.25, 0.25);

            if (y < scratch.minY() + 8 || y > 96) {
                return;
            }
        }
    }

    private void carveDisc(
            final ChunkScratch scratch,
            final double centreX,
            final double centreY,
            final double centreZ,
            final double radiusH,
            final double radiusV) {

        final int minX = Math.max(0, (int) Math.floor(centreX - radiusH));
        final int maxX = Math.min(15, (int) Math.ceil(centreX + radiusH));
        final int minZ = Math.max(0, (int) Math.floor(centreZ - radiusH));
        final int maxZ = Math.min(15, (int) Math.ceil(centreZ + radiusH));
        if (minX > maxX || minZ > maxZ) {
            return;
        }

        final int minY = Math.max(scratch.minY() + 1, (int) Math.floor(centreY - radiusV));
        final int maxY = (int) Math.ceil(centreY + radiusV);

        for (int z = minZ; z <= maxZ; z++) {
            for (int x = minX; x <= maxX; x++) {
                final double dx = (x + 0.5 - centreX) / radiusH;
                final double dz = (z + 0.5 - centreZ) / radiusH;
                final double horizontal = dx * dx + dz * dz;
                if (horizontal > 1.0) {
                    continue;
                }
                final int ceiling = Math.min(maxY, roofFor(scratch, x, z));
                for (int y = minY; y <= ceiling; y++) {
                    final double dy = (y + 0.5 - centreY) / radiusV;
                    if (horizontal + dy * dy > 1.0) {
                        continue;
                    }
                    final short current = scratch.get(x, y, z);
                    if (current == Blk.AIR || current == Blk.WATER || current == Blk.LAVA || current == Blk.BEDROCK) {
                        continue;
                    }
                    scratch.set(x, y, z, y <= LAVA_LEVEL ? Blk.LAVA : Blk.AIR);
                }
            }
        }
    }

    private int roofFor(final ChunkScratch scratch, final int x, final int z) {
        final int surface = scratch.surfaceY(x, z);
        if (surface <= settings.seaLevel() + 2) {
            return Math.min(surface - 6, settings.seaLevel() - 6);
        }
        return surface - 1;
    }
}
