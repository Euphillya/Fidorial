package fr.euphyllia.fidorial.testplugin.worldgen.stage;

import fr.euphyllia.fidorial.testplugin.worldgen.BiomeType;
import fr.euphyllia.fidorial.testplugin.worldgen.Blk;
import fr.euphyllia.fidorial.testplugin.worldgen.ChunkScratch;
import fr.euphyllia.fidorial.testplugin.worldgen.GeneratorSettings;
import fr.euphyllia.fidorial.testplugin.worldgen.SurfaceKind;
import fr.euphyllia.fidorial.testplugin.worldgen.noise.OctaveNoise;
import fr.euphyllia.fidorial.testplugin.worldgen.noise.Seeds;

import java.util.Random;

public final class SurfaceStage {

    private static final int BAND_COUNT = 64;

    private final GeneratorSettings settings;
    private final OctaveNoise variation;
    private final OctaveNoise patches;
    private final OctaveNoise swampWater;
    private final short[] terracottaBands = new short[BAND_COUNT];

    public SurfaceStage(final GeneratorSettings settings) {
        this.settings = settings;
        final long seed = settings.seed();
        this.variation = new OctaveNoise(Seeds.derive(seed, "surface/variation"), 2);
        this.patches = new OctaveNoise(Seeds.derive(seed, "surface/patches"), 2);
        this.swampWater = new OctaveNoise(Seeds.derive(seed, "surface/swamp"), 1);
        buildTerracottaBands(Seeds.derive(seed, "surface/badlands"));
    }

    private void buildTerracottaBands(final long seed) {
        final Random random = new Random(seed);
        java.util.Arrays.fill(terracottaBands, Blk.TERRACOTTA);

        addBands(random, Blk.ORANGE_TERRACOTTA, 8, 3);
        addBands(random, Blk.YELLOW_TERRACOTTA, 5, 2);
        addBands(random, Blk.BROWN_TERRACOTTA, 5, 2);
        addBands(random, Blk.RED_TERRACOTTA, 4, 2);
        addBands(random, Blk.WHITE_TERRACOTTA, 4, 2);
        addBands(random, Blk.LIGHT_GRAY_TERRACOTTA, 3, 1);
    }

    private void addBands(final Random random, final short block, final int count, final int maxThickness) {
        for (int i = 0; i < count; i++) {
            final int thickness = 1 + random.nextInt(maxThickness);
            final int start = random.nextInt(BAND_COUNT);
            for (int t = 0; t < thickness; t++) {
                terracottaBands[(start + t) % BAND_COUNT] = block;
            }
        }
    }

    public void apply(final ChunkScratch scratch) {
        final int originX = scratch.originX();
        final int originZ = scratch.originZ();
        final int seaLevel = settings.seaLevel();

        for (int z = 0; z < 16; z++) {
            for (int x = 0; x < 16; x++) {
                final int surfaceY = scratch.surfaceY(x, z);
                if (surfaceY < scratch.minY()) {
                    continue;
                }

                final int worldX = originX + x;
                final int worldZ = originZ + z;
                final BiomeType biome = BiomeType.of(scratch.surfaceBiome(x, z));
                final int waterTop = scratch.waterTop(x, z);
                final boolean submerged = waterTop > surfaceY;

                final double detail = variation.sample2d(worldX * 0.08, worldZ * 0.08);
                final int depth = 3 + (int) Math.round(detail * 1.5);

                paintColumn(scratch, x, z, worldX, worldZ, surfaceY, depth, biome, submerged);

                if (biome.cold()) {
                    freeze(scratch, x, z, surfaceY, waterTop, submerged, seaLevel);
                }
            }
        }
    }

    private void paintColumn(
            final ChunkScratch scratch,
            final int x,
            final int z,
            final int worldX,
            final int worldZ,
            final int surfaceY,
            final int depth,
            final BiomeType biome,
            final boolean submerged) {

        final SurfaceKind kind = biome.surface();
        switch (kind) {
            case TERRACOTTA -> paintBadlands(scratch, x, z, worldX, worldZ, surfaceY, biome);
            case OCEAN_FLOOR -> paintOceanFloor(scratch, x, z, worldX, worldZ, surfaceY, depth);
            case SWAMP -> paintSwamp(scratch, x, z, worldX, worldZ, surfaceY, depth, submerged);
            case STONE -> {
            }
            default -> paintSimple(scratch, x, z, worldX, worldZ, surfaceY, depth, kind, submerged);
        }
    }

    private void paintSimple(
            final ChunkScratch scratch,
            final int x,
            final int z,
            final int worldX,
            final int worldZ,
            final int surfaceY,
            final int depth,
            final SurfaceKind kind,
            final boolean submerged) {

        final double patch = patches.sample2d(worldX * 0.15, worldZ * 0.15);

        final short top;
        final short filler;
        short deep = 0;
        switch (kind) {
            case SAND -> {
                top = Blk.SAND;
                filler = Blk.SAND;
                deep = Blk.SANDSTONE;
            }
            case RED_SAND -> {
                top = Blk.RED_SAND;
                filler = Blk.RED_SAND;
                deep = Blk.RED_SANDSTONE;
            }
            case GRAVEL -> {
                top = patch > 0.25 ? Blk.STONE : Blk.GRAVEL;
                filler = Blk.GRAVEL;
            }
            case SNOW_BLOCK -> {
                top = Blk.SNOW_BLOCK;
                filler = Blk.DIRT;
            }
            case POWDER_SNOW -> {
                top = patch > 0.15 ? Blk.POWDER_SNOW : Blk.SNOW_BLOCK;
                filler = Blk.SNOW_BLOCK;
            }
            case PACKED_ICE -> {
                top = patch > 0.1 ? Blk.PACKED_ICE : (patch < -0.35 ? Blk.ICE : Blk.SNOW_BLOCK);
                filler = Blk.PACKED_ICE;
            }
            case STONE_SNOW -> {
                top = patch > -0.2 ? Blk.SNOW_BLOCK : Blk.STONE;
                filler = Blk.STONE;
            }
            case STONY_PEAKS -> {
                top = patch > 0.3 ? Blk.CALCITE : (patch < -0.35 ? Blk.GRAVEL : Blk.STONE);
                filler = Blk.STONE;
            }
            case PODZOL -> {
                top = patch > 0.1 ? Blk.PODZOL : (patch < -0.4 ? Blk.COARSE_DIRT : Blk.GRASS_BLOCK);
                filler = Blk.DIRT;
            }
            case MYCELIUM -> {
                top = Blk.MYCELIUM;
                filler = Blk.DIRT;
            }
            case MUD -> {
                top = Blk.MUD;
                filler = patch > 0.0 ? Blk.MUD : Blk.DIRT;
            }
            default -> {
                top = submerged ? Blk.DIRT : Blk.GRASS_BLOCK;
                filler = Blk.DIRT;
            }
        }

        if (Blk.isSolid(scratch.get(x, surfaceY, z))) {
            scratch.set(x, surfaceY, z, top);
        }
        for (int i = 1; i <= depth; i++) {
            final int y = surfaceY - i;
            if (!Blk.isSolid(scratch.get(x, y, z))) {
                break;
            }
            scratch.set(x, y, z, filler);
        }
        if (deep != 0) {
            for (int i = depth + 1; i <= depth + 3; i++) {
                final int y = surfaceY - i;
                if (!Blk.isSolid(scratch.get(x, y, z))) {
                    break;
                }
                scratch.set(x, y, z, deep);
            }
        }
    }

    private void paintBadlands(
            final ChunkScratch scratch,
            final int x,
            final int z,
            final int worldX,
            final int worldZ,
            final int surfaceY,
            final BiomeType biome) {

        final double patch = patches.sample2d(worldX * 0.12, worldZ * 0.12);
        final boolean sandCap = surfaceY < 74 + patch * 4;

        if (sandCap) {
            scratch.set(x, surfaceY, z, Blk.RED_SAND);
            for (int i = 1; i <= 3; i++) {
                final int y = surfaceY - i;
                if (!Blk.isSolid(scratch.get(x, y, z))) {
                    break;
                }
                scratch.set(x, y, z, i <= 1 ? Blk.RED_SAND : Blk.RED_SANDSTONE);
            }
            return;
        }

        if (biome == BiomeType.WOODED_BADLANDS && surfaceY > 97) {
            scratch.set(x, surfaceY, z, patch > 0.0 ? Blk.COARSE_DIRT : Blk.GRASS_BLOCK);
            for (int i = 1; i <= 2; i++) {
                final int y = surfaceY - i;
                if (!Blk.isSolid(scratch.get(x, y, z))) {
                    break;
                }
                scratch.set(x, y, z, Blk.DIRT);
            }
            return;
        }

        for (int i = 0; i <= 12; i++) {
            final int y = surfaceY - i;
            if (!Blk.isSolid(scratch.get(x, y, z))) {
                break;
            }
            scratch.set(x, y, z, terracottaBands[Math.floorMod(y, BAND_COUNT)]);
        }
    }

    private void paintOceanFloor(
            final ChunkScratch scratch,
            final int x,
            final int z,
            final int worldX,
            final int worldZ,
            final int surfaceY,
            final int depth) {

        final double patch = patches.sample2d(worldX * 0.09, worldZ * 0.09);
        final int seaLevel = settings.seaLevel();
        final int waterDepth = seaLevel - surfaceY;

        final short top;
        if (waterDepth <= 5 && patch > -0.3) {
            top = Blk.SAND;
        } else if (patch > 0.35) {
            top = Blk.CLAY;
        } else if (patch < -0.15) {
            top = Blk.GRAVEL;
        } else {
            top = Blk.DIRT;
        }

        if (Blk.isSolid(scratch.get(x, surfaceY, z))) {
            scratch.set(x, surfaceY, z, top);
        }
        for (int i = 1; i <= depth; i++) {
            final int y = surfaceY - i;
            if (!Blk.isSolid(scratch.get(x, y, z))) {
                break;
            }
            scratch.set(x, y, z, top == Blk.SAND ? Blk.SAND : Blk.DIRT);
        }
    }

    private void paintSwamp(
            final ChunkScratch scratch,
            final int x,
            final int z,
            final int worldX,
            final int worldZ,
            final int surfaceY,
            final int depth,
            final boolean submerged) {

        final int seaLevel = settings.seaLevel();
        if (surfaceY == seaLevel && swampWater.sample2d(worldX * 0.25, worldZ * 0.25) > 0.12) {
            scratch.set(x, surfaceY, z, Blk.WATER);
            scratch.setWaterTop(x, z, surfaceY);
            scratch.setSurfaceY(x, z, surfaceY - 1);
            for (int i = 1; i <= depth; i++) {
                final int y = surfaceY - i;
                if (!Blk.isSolid(scratch.get(x, y, z))) {
                    break;
                }
                scratch.set(x, y, z, Blk.DIRT);
            }
            return;
        }

        scratch.set(x, surfaceY, z, submerged ? Blk.DIRT : Blk.GRASS_BLOCK);
        for (int i = 1; i <= depth; i++) {
            final int y = surfaceY - i;
            if (!Blk.isSolid(scratch.get(x, y, z))) {
                break;
            }
            scratch.set(x, y, z, Blk.DIRT);
        }
    }

    private void freeze(
            final ChunkScratch scratch,
            final int x,
            final int z,
            final int surfaceY,
            final int waterTop,
            final boolean submerged,
            final int seaLevel) {

        if (submerged) {
            if (waterTop >= seaLevel && scratch.get(x, waterTop, z) == Blk.WATER) {
                scratch.set(x, waterTop, z, Blk.ICE);
            }
            return;
        }

        final int above = surfaceY + 1;
        final short top = scratch.get(x, surfaceY, z);
        if (scratch.get(x, above, z) == Blk.AIR && Blk.isSolid(top) && top != Blk.SNOW_BLOCK) {
            scratch.set(x, above, z, Blk.SNOW);
        }
    }
}
