package fr.euphyllia.fidorial.testplugin.worldgen.stage;

import fr.euphyllia.fidorial.testplugin.worldgen.BiomeType;
import fr.euphyllia.fidorial.testplugin.worldgen.Blk;
import fr.euphyllia.fidorial.testplugin.worldgen.ChunkScratch;
import fr.euphyllia.fidorial.testplugin.worldgen.GeneratorSettings;
import fr.euphyllia.fidorial.testplugin.worldgen.climate.BiomeTable;
import fr.euphyllia.fidorial.testplugin.worldgen.climate.ClimateSampler;
import fr.euphyllia.fidorial.testplugin.worldgen.noise.Seeds;

import java.util.List;
import java.util.Random;

public final class OreStage {

    private static final List<Vein> VEINS = List.of(
            new Vein(Blk.DIRT, Blk.DIRT, 7, 33, 0, 160, Spread.UNIFORM, "blob/dirt"),
            new Vein(Blk.GRAVEL, Blk.GRAVEL, 8, 33, 0, 160, Spread.UNIFORM, "blob/gravel"),
            new Vein(Blk.GRANITE, Blk.GRANITE, 10, 33, 0, 80, Spread.UNIFORM, "blob/granite"),
            new Vein(Blk.DIORITE, Blk.DIORITE, 10, 33, 0, 80, Spread.UNIFORM, "blob/diorite"),
            new Vein(Blk.ANDESITE, Blk.ANDESITE, 10, 33, 0, 80, Spread.UNIFORM, "blob/andesite"),
            new Vein(Blk.TUFF, Blk.TUFF, 2, 64, -64, 0, Spread.UNIFORM, "blob/tuff"),

            new Vein(Blk.COAL_ORE, Blk.DEEPSLATE_COAL_ORE, 30, 17, 136, 320, Spread.UNIFORM, "ore/coal_upper"),
            new Vein(Blk.COAL_ORE, Blk.DEEPSLATE_COAL_ORE, 20, 17, 0, 192, Spread.TRIANGLE, "ore/coal_lower"),
            new Vein(Blk.IRON_ORE, Blk.DEEPSLATE_IRON_ORE, 90, 9, 80, 384, Spread.TRIANGLE, "ore/iron_upper"),
            new Vein(Blk.IRON_ORE, Blk.DEEPSLATE_IRON_ORE, 10, 9, -24, 56, Spread.TRIANGLE, "ore/iron_middle"),
            new Vein(Blk.IRON_ORE, Blk.DEEPSLATE_IRON_ORE, 10, 4, -64, 72, Spread.UNIFORM, "ore/iron_small"),
            new Vein(Blk.COPPER_ORE, Blk.DEEPSLATE_COPPER_ORE, 16, 10, -16, 112, Spread.TRIANGLE, "ore/copper"),
            new Vein(Blk.GOLD_ORE, Blk.DEEPSLATE_GOLD_ORE, 4, 9, -64, 32, Spread.TRIANGLE, "ore/gold"),
            new Vein(
                    Blk.GOLD_ORE,
                    Blk.DEEPSLATE_GOLD_ORE,
                    50,
                    9,
                    32,
                    256,
                    Spread.UNIFORM,
                    Restriction.BADLANDS,
                    "ore/gold_badlands"),
            new Vein(Blk.REDSTONE_ORE, Blk.DEEPSLATE_REDSTONE_ORE, 4, 8, -64, 15, Spread.UNIFORM, "ore/redstone"),
            new Vein(
                    Blk.REDSTONE_ORE,
                    Blk.DEEPSLATE_REDSTONE_ORE,
                    5,
                    8,
                    -96,
                    -32,
                    Spread.TRIANGLE,
                    "ore/redstone_lower"),
            new Vein(Blk.LAPIS_ORE, Blk.DEEPSLATE_LAPIS_ORE, 1, 6, -64, 64, Spread.TRIANGLE, "ore/lapis"),
            new Vein(Blk.LAPIS_ORE, Blk.DEEPSLATE_LAPIS_ORE, 1, 6, -32, 32, Spread.UNIFORM, "ore/lapis_buried"),
            new Vein(Blk.DIAMOND_ORE, Blk.DEEPSLATE_DIAMOND_ORE, 3, 6, -144, 16, Spread.TRIANGLE, "ore/diamond"),
            new Vein(
                    Blk.EMERALD_ORE,
                    Blk.DEEPSLATE_EMERALD_ORE,
                    100,
                    3,
                    -16,
                    320,
                    Spread.TRIANGLE,
                    Restriction.MOUNTAIN,
                    "ore/emerald"));
    private final GeneratorSettings settings;
    private final ClimateSampler climate;

    public OreStage(final GeneratorSettings settings, final ClimateSampler climate) {
        this.settings = settings;
        this.climate = climate;
    }

    private static boolean isMountain(final BiomeType biome) {
        return switch (biome) {
            case WINDSWEPT_HILLS,
                 WINDSWEPT_GRAVELLY_HILLS,
                 WINDSWEPT_FOREST,
                 JAGGED_PEAKS,
                 FROZEN_PEAKS,
                 STONY_PEAKS,
                 SNOWY_SLOPES,
                 GROVE,
                 MEADOW -> true;
            default -> false;
        };
    }

    private static double lerp(final double a, final double b, final double t) {
        return a + t * (b - a);
    }

    public void apply(final ChunkScratch scratch) {
        for (int neighbourZ = -1; neighbourZ <= 1; neighbourZ++) {
            for (int neighbourX = -1; neighbourX <= 1; neighbourX++) {
                applyChunk(scratch, neighbourX, neighbourZ);
            }
        }
    }

    private void applyChunk(final ChunkScratch scratch, final int neighbourX, final int neighbourZ) {
        final int chunkX = scratch.chunkX() + neighbourX;
        final int chunkZ = scratch.chunkZ() + neighbourZ;
        final int offsetX = neighbourX << 4;
        final int offsetZ = neighbourZ << 4;

        final BiomeType chunkBiome =
                BiomeType.of(BiomeTable.surface(climate.sample((chunkX << 4) + 8, (chunkZ << 4) + 8)));

        for (final Vein vein : VEINS) {
            final Random random = Seeds.randomForChunk(settings.seed(), chunkX, chunkZ, vein.salt());
            for (int attempt = 0; attempt < vein.count(); attempt++) {
                final int localX = random.nextInt(16) + offsetX;
                final int localZ = random.nextInt(16) + offsetZ;
                final int y = sampleY(random, vein);

                if (localX < -6 || localX > 21 || localZ < -6 || localZ > 21) {
                    continue;
                }
                if (y < scratch.minY() + 1 || y > scratch.maxY()) {
                    continue;
                }
                if (!allowed(vein.restriction(), chunkBiome)) {
                    continue;
                }
                final Random shape = Seeds.randomForChunk(
                        settings.seed(),
                        scratch.originX() + localX,
                        scratch.originZ() + localZ + y * 4096,
                        vein.salt());
                place(scratch, vein, localX, y, localZ, shape);
            }
        }
    }

    private int sampleY(final Random random, final Vein vein) {
        final int span = vein.maxY() - vein.minY();
        if (vein.spread() == Spread.UNIFORM) {
            return vein.minY() + random.nextInt(span + 1);
        }
        return vein.minY() + (random.nextInt(span + 1) + random.nextInt(span + 1)) / 2;
    }

    private boolean allowed(final Restriction restriction, final BiomeType biome) {
        if (restriction == Restriction.NONE) {
            return true;
        }
        return switch (restriction) {
            case MOUNTAIN -> isMountain(biome);
            case BADLANDS -> biome == BiomeType.BADLANDS
                    || biome == BiomeType.ERODED_BADLANDS
                    || biome == BiomeType.WOODED_BADLANDS;
            default -> true;
        };
    }

    private void place(
            final ChunkScratch scratch,
            final Vein vein,
            final int centreX,
            final int centreY,
            final int centreZ,
            final Random shape) {

        final int size = vein.size();
        final double spread = size / 16.0;
        final double angle = shape.nextDouble() * Math.PI;

        final double startX = centreX + Math.sin(angle) * spread;
        final double endX = centreX - Math.sin(angle) * spread;
        final double startZ = centreZ + Math.cos(angle) * spread;
        final double endZ = centreZ - Math.cos(angle) * spread;
        final double startY = centreY + shape.nextInt(3) - 2;
        final double endY = centreY + shape.nextInt(3) - 2;

        for (int step = 0; step <= size; step++) {
            final double t = step / (double) size;
            final double x = lerp(startX, endX, t);
            final double y = lerp(startY, endY, t);
            final double z = lerp(startZ, endZ, t);
            final double radius = ((Math.sin(Math.PI * t) + 1.0) * shape.nextDouble() * spread + 1.0) / 2.0;
            sphere(scratch, vein, x, y, z, radius);
        }
    }

    private void sphere(
            final ChunkScratch scratch,
            final Vein vein,
            final double centreX,
            final double centreY,
            final double centreZ,
            final double radius) {

        final int minX = (int) Math.floor(centreX - radius);
        final int maxX = (int) Math.ceil(centreX + radius);
        final int minY = (int) Math.floor(centreY - radius);
        final int maxY = (int) Math.ceil(centreY + radius);
        final int minZ = (int) Math.floor(centreZ - radius);
        final int maxZ = (int) Math.ceil(centreZ + radius);

        for (int y = minY; y <= maxY; y++) {
            if (y < scratch.minY() + 1 || y > scratch.maxY()) {
                continue;
            }
            for (int z = minZ; z <= maxZ; z++) {
                if (z < 0 || z > 15) {
                    continue;
                }
                for (int x = minX; x <= maxX; x++) {
                    if (x < 0 || x > 15) {
                        continue;
                    }
                    final double dx = x + 0.5 - centreX;
                    final double dy = y + 0.5 - centreY;
                    final double dz = z + 0.5 - centreZ;
                    if (dx * dx + dy * dy + dz * dz > radius * radius) {
                        continue;
                    }
                    final short current = scratch.get(x, y, z);
                    if (!Blk.isOreReplaceable(current)) {
                        continue;
                    }
                    scratch.set(x, y, z, current == Blk.DEEPSLATE ? vein.deepslateBlock() : vein.block());
                }
            }
        }
    }

    private enum Spread {
        UNIFORM,
        TRIANGLE
    }

    private enum Restriction {
        NONE,
        MOUNTAIN,
        BADLANDS
    }

    private record Vein(
            short block,
            short deepslateBlock,
            int count,
            int size,
            int minY,
            int maxY,
            Spread spread,
            Restriction restriction,
            String salt) {

        Vein(final short block, final short deepslateBlock, final int count, final int size,
             final int minY, final int maxY, final Spread spread, final String salt) {
            this(block, deepslateBlock, count, size, minY, maxY, spread, Restriction.NONE, salt);
        }
    }

}
