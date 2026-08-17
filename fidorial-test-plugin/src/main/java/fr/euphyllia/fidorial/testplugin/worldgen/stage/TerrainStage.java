package fr.euphyllia.fidorial.testplugin.worldgen.stage;

import fr.euphyllia.fidorial.testplugin.worldgen.Blk;
import fr.euphyllia.fidorial.testplugin.worldgen.ChunkScratch;
import fr.euphyllia.fidorial.testplugin.worldgen.GeneratorSettings;
import fr.euphyllia.fidorial.testplugin.worldgen.climate.ClimatePoint;
import fr.euphyllia.fidorial.testplugin.worldgen.climate.ClimateSampler;
import fr.euphyllia.fidorial.testplugin.worldgen.noise.OctaveNoise;
import fr.euphyllia.fidorial.testplugin.worldgen.noise.Seeds;
import fr.euphyllia.fidorial.testplugin.worldgen.shape.TerrainShape;
import fr.euphyllia.fidorial.testplugin.worldgen.shape.TerrainShaper;

public final class TerrainStage {

    private static final double BASE_3D_XZ = 0.0105;
    private static final double BASE_3D_Y = 0.0165;
    private static final double BASE_3D_AMPLITUDE = 1.25;

    private static final double CHEESE_XZ = 0.0125;
    private static final double CHEESE_Y = 0.0250;
    private static final double CHEESE_THRESHOLD = 0.26;

    private static final double SPAGHETTI_XZ = 0.0085;
    private static final double SPAGHETTI_Y = 0.0130;
    private static final double SPAGHETTI_RADIUS = 0.048;

    private static final double NOODLE_XZ = 0.0260;
    private static final double NOODLE_Y = 0.0300;
    private static final double NOODLE_RADIUS = 0.030;

    private static final int LAVA_LEVEL = -54;

    private static final int DEEPSLATE_TOP = 0;

    private static final int DEEPSLATE_FULL = -8;

    private final GeneratorSettings settings;
    private final ClimateSampler climate;
    private final TerrainShaper shaper;

    private final OctaveNoise base3d;
    private final OctaveNoise cheese;
    private final OctaveNoise spaghettiA;
    private final OctaveNoise spaghettiB;
    private final OctaveNoise noodleA;
    private final OctaveNoise noodleB;

    private final long bedrockSeed;
    private final long deepslateSeed;

    public TerrainStage(
            final GeneratorSettings settings, final ClimateSampler climate, final TerrainShaper shaper) {
        this.settings = settings;
        this.climate = climate;
        this.shaper = shaper;

        final long seed = settings.seed();
        this.base3d = new OctaveNoise(Seeds.derive(seed, "terrain/base3d"), 4);
        this.cheese = new OctaveNoise(Seeds.derive(seed, "cave/cheese"), 3);
        this.spaghettiA = new OctaveNoise(Seeds.derive(seed, "cave/spaghetti_a"), 2);
        this.spaghettiB = new OctaveNoise(Seeds.derive(seed, "cave/spaghetti_b"), 2);
        this.noodleA = new OctaveNoise(Seeds.derive(seed, "cave/noodle_a"), 2);
        this.noodleB = new OctaveNoise(Seeds.derive(seed, "cave/noodle_b"), 2);

        this.bedrockSeed = Seeds.derive(seed, "terrain/bedrock");
        this.deepslateSeed = Seeds.derive(seed, "terrain/deepslate");
    }

    private static int alignDown(final int value, final int step) {
        return Math.floorDiv(value, step) * step;
    }

    private static double lerp(final double a, final double b, final double t) {
        return a + t * (b - a);
    }

    public void fill(final ChunkScratch scratch) {
        sampleClimate(scratch);
        sampleShapes(scratch);
        sampleDensity(scratch);
        placeBlocks(scratch);
    }

    private void sampleClimate(final ChunkScratch scratch) {
        final int originX = scratch.originX();
        final int originZ = scratch.originZ();
        for (int cellZ = 0; cellZ < 4; cellZ++) {
            for (int cellX = 0; cellX < 4; cellX++) {
                scratch.setClimate(
                        cellX, cellZ, climate.sample(originX + (cellX << 2) + 2, originZ + (cellZ << 2) + 2));
            }
        }
    }

    private void sampleShapes(final ChunkScratch scratch) {
        final int originX = scratch.originX();
        final int originZ = scratch.originZ();
        for (int cornerZ = 0; cornerZ <= 4; cornerZ++) {
            for (int cornerX = 0; cornerX <= 4; cornerX++) {
                scratch.setCornerShape(cornerX, cornerZ, shapeAt(originX + (cornerX << 2), originZ + (cornerZ << 2)));
            }
        }
    }

    private TerrainShape shapeAt(final int worldX, final int worldZ) {
        return shaper.shape(climate.sample(worldX, worldZ));
    }

    private void sampleDensity(final ChunkScratch scratch) {
        final int originX = scratch.originX();
        final int originZ = scratch.originZ();
        final int minY = scratch.minY();
        for (int cornerY = 0; cornerY <= scratch.cellCountY(); cornerY++) {
            final int worldY = minY + (cornerY << 2);
            for (int cornerZ = 0; cornerZ <= 4; cornerZ++) {
                for (int cornerX = 0; cornerX <= 4; cornerX++) {
                    final double value = density(
                            originX + (cornerX << 2),
                            worldY,
                            originZ + (cornerZ << 2),
                            scratch.cornerShape(cornerX, cornerZ),
                            minY);
                    scratch.setDensity(cornerX, cornerY, cornerZ, value);
                }
            }
        }
    }

    private double density(final int x, final int y, final int z, final TerrainShape shape, final int minY) {
        final double gradient = (shape.height() - y) / shape.verticalScale();

        double value;
        if (gradient > 3.0 || gradient < -3.0) {
            value = gradient;
        } else {
            value = gradient + base3d.sample(x * BASE_3D_XZ, y * BASE_3D_Y, z * BASE_3D_XZ) * BASE_3D_AMPLITUDE;
        }

        if (y < minY + 6) {
            value += (minY + 6 - y) * 0.6;
        }
        if (y > 296) {
            value -= (y - 296) * 0.06;
        }

        if (settings.caves() && y <= shape.caveTop()) {
            value = Math.min(value, caveSolidity(x, y, z));
        }
        return value;
    }

    private double caveSolidity(final int x, final int y, final int z) {
        final double cheeseValue = cheese.sample(x * CHEESE_XZ, y * CHEESE_Y, z * CHEESE_XZ);
        double solid = CHEESE_THRESHOLD - cheeseValue;

        final double sa = spaghettiA.sample(x * SPAGHETTI_XZ, y * SPAGHETTI_Y, z * SPAGHETTI_XZ);
        final double sb = spaghettiB.sample(x * SPAGHETTI_XZ, y * SPAGHETTI_Y, z * SPAGHETTI_XZ);
        solid = Math.min(solid, Math.sqrt(sa * sa + sb * sb) - SPAGHETTI_RADIUS);

        final double na = noodleA.sample(x * NOODLE_XZ, y * NOODLE_Y, z * NOODLE_XZ);
        final double nb = noodleB.sample(x * NOODLE_XZ, y * NOODLE_Y, z * NOODLE_XZ);
        solid = Math.min(solid, Math.sqrt(na * na + nb * nb) - NOODLE_RADIUS);

        return solid;
    }

    private void placeBlocks(final ChunkScratch scratch) {
        final int minY = scratch.minY();
        final int maxY = scratch.maxY();
        final int seaLevel = settings.seaLevel();

        for (int z = 0; z < 16; z++) {
            for (int x = 0; x < 16; x++) {
                final double columnHeight = interpolateHeight(scratch, x, z);
                scratch.setColumnHeight(x, z, columnHeight);

                final int scanTop = Math.min(maxY, Math.max(seaLevel, ceilingFor(scratch, x, z)));

                boolean openToSky = true;
                int surface = minY - 1;
                int water = Integer.MIN_VALUE;

                for (int y = scanTop; y >= minY; y--) {
                    final double value = interpolateDensity(scratch, x, y, z);
                    if (value > 0.0) {
                        scratch.set(x, y, z, stoneFor(scratch.originX() + x, y, scratch.originZ() + z));
                        if (openToSky) {
                            surface = y;
                            openToSky = false;
                        }
                    } else if (openToSky && y <= seaLevel) {
                        scratch.set(x, y, z, Blk.WATER);
                        if (water == Integer.MIN_VALUE) {
                            water = y;
                        }
                    } else if (!openToSky && y <= LAVA_LEVEL) {
                        scratch.set(x, y, z, Blk.LAVA);
                    }
                }

                scratch.setSurfaceY(x, z, surface);
                scratch.setWaterTop(x, z, water);
                placeBedrock(scratch, x, z);
            }
        }
    }

    private short stoneFor(final int worldX, final int y, final int worldZ) {
        if (y > DEEPSLATE_TOP) {
            return Blk.STONE;
        }
        if (y <= DEEPSLATE_FULL) {
            return Blk.DEEPSLATE;
        }
        final double ratio = (DEEPSLATE_TOP - y) / (double) (DEEPSLATE_TOP - DEEPSLATE_FULL);
        return Seeds.hash01(deepslateSeed, worldX, y, worldZ) < ratio ? Blk.DEEPSLATE : Blk.STONE;
    }

    private void placeBedrock(final ChunkScratch scratch, final int x, final int z) {
        final int minY = scratch.minY();
        final int worldX = scratch.originX() + x;
        final int worldZ = scratch.originZ() + z;
        scratch.set(x, minY, z, Blk.BEDROCK);
        for (int offset = 1; offset <= 4; offset++) {
            final int y = minY + offset;
            if (Seeds.hash01(bedrockSeed, worldX, y, worldZ) < (5 - offset) / 5.0) {
                scratch.set(x, y, z, Blk.BEDROCK);
            }
        }
    }

    private int ceilingFor(final ChunkScratch scratch, final int x, final int z) {
        final int cornerX = x >> 2;
        final int cornerZ = z >> 2;
        double top = Double.NEGATIVE_INFINITY;
        for (int dz = 0; dz <= 1; dz++) {
            for (int dx = 0; dx <= 1; dx++) {
                final TerrainShape shape = scratch.cornerShape(cornerX + dx, cornerZ + dz);
                top = Math.max(top, shape.height() + shape.verticalScale() * 1.3);
            }
        }
        return (int) Math.ceil(top) + 2;
    }

    private double interpolateHeight(final ChunkScratch scratch, final int x, final int z) {
        final int cornerX = x >> 2;
        final int cornerZ = z >> 2;
        final double fx = (x & 3) * 0.25;
        final double fz = (z & 3) * 0.25;
        final double h00 = scratch.cornerShape(cornerX, cornerZ).height();
        final double h10 = scratch.cornerShape(cornerX + 1, cornerZ).height();
        final double h01 = scratch.cornerShape(cornerX, cornerZ + 1).height();
        final double h11 = scratch.cornerShape(cornerX + 1, cornerZ + 1).height();
        return lerp(lerp(h00, h10, fx), lerp(h01, h11, fx), fz);
    }

    private double interpolateDensity(final ChunkScratch scratch, final int x, final int y, final int z) {
        final int localY = y - scratch.minY();
        final int cornerX = x >> 2;
        final int cornerZ = z >> 2;
        final int cornerY = localY >> 2;
        final double fx = (x & 3) * 0.25;
        final double fz = (z & 3) * 0.25;
        final double fy = (localY & 3) * 0.25;

        final double d000 = scratch.densityCorner(cornerX, cornerY, cornerZ);
        final double d100 = scratch.densityCorner(cornerX + 1, cornerY, cornerZ);
        final double d010 = scratch.densityCorner(cornerX, cornerY, cornerZ + 1);
        final double d110 = scratch.densityCorner(cornerX + 1, cornerY, cornerZ + 1);
        final double d001 = scratch.densityCorner(cornerX, cornerY + 1, cornerZ);
        final double d101 = scratch.densityCorner(cornerX + 1, cornerY + 1, cornerZ);
        final double d011 = scratch.densityCorner(cornerX, cornerY + 1, cornerZ + 1);
        final double d111 = scratch.densityCorner(cornerX + 1, cornerY + 1, cornerZ + 1);

        final double low = lerp(lerp(d000, d100, fx), lerp(d010, d110, fx), fz);
        final double high = lerp(lerp(d001, d101, fx), lerp(d011, d111, fx), fz);
        return lerp(low, high, fy);
    }

    public int surfaceAt(final int worldX, final int worldZ, final int minY, final int maxY) {
        final int baseX = Math.floorDiv(worldX, 4) * 4;
        final int baseZ = Math.floorDiv(worldZ, 4) * 4;

        final TerrainShape s00 = shapeAt(baseX, baseZ);
        final TerrainShape s10 = shapeAt(baseX + 4, baseZ);
        final TerrainShape s01 = shapeAt(baseX, baseZ + 4);
        final TerrainShape s11 = shapeAt(baseX + 4, baseZ + 4);

        final double fx = (worldX - baseX) * 0.25;
        final double fz = (worldZ - baseZ) * 0.25;

        double top = Double.NEGATIVE_INFINITY;
        for (final TerrainShape shape : new TerrainShape[]{s00, s10, s01, s11}) {
            top = Math.max(top, shape.height() + shape.verticalScale() * 1.3);
        }

        int level = Math.min(alignDown(maxY - minY, 4) + minY, alignDown((int) Math.ceil(top) + 6 - minY, 4) + minY);
        double above = levelDensity(baseX, level, baseZ, s00, s10, s01, s11, fx, fz, minY);

        for (level -= 4; level >= minY; level -= 4) {
            final double here = levelDensity(baseX, level, baseZ, s00, s10, s01, s11, fx, fz, minY);
            if (here > 0.0) {
                for (int y = level + 3; y >= level; y--) {
                    final double t = (y - level) * 0.25;
                    if (here + (above - here) * t > 0.0) {
                        return y;
                    }
                }
                return level;
            }
            above = here;
        }
        return minY - 1;
    }

    private double levelDensity(
            final int baseX,
            final int y,
            final int baseZ,
            final TerrainShape s00,
            final TerrainShape s10,
            final TerrainShape s01,
            final TerrainShape s11,
            final double fx,
            final double fz,
            final int minY) {
        final double d00 = density(baseX, y, baseZ, s00, minY);
        final double d10 = density(baseX + 4, y, baseZ, s10, minY);
        final double d01 = density(baseX, y, baseZ + 4, s01, minY);
        final double d11 = density(baseX + 4, y, baseZ + 4, s11, minY);
        return lerp(lerp(d00, d10, fx), lerp(d01, d11, fx), fz);
    }

    public ClimatePoint climateAt(final int worldX, final int worldZ) {
        return climate.sample(worldX, worldZ);
    }
}
