package fr.euphyllia.fidorial.testplugin.worldgen.stage;

import fr.euphyllia.fidorial.testplugin.worldgen.BiomeType;
import fr.euphyllia.fidorial.testplugin.worldgen.Blk;
import fr.euphyllia.fidorial.testplugin.worldgen.ChunkScratch;
import fr.euphyllia.fidorial.testplugin.worldgen.GeneratorSettings;
import fr.euphyllia.fidorial.testplugin.worldgen.climate.BiomeTable;
import fr.euphyllia.fidorial.testplugin.worldgen.feature.TreeKind;
import fr.euphyllia.fidorial.testplugin.worldgen.feature.Trees;
import fr.euphyllia.fidorial.testplugin.worldgen.noise.Seeds;

import java.util.Random;

public final class DecorationStage {

    private static final short[] PLAIN_FLOWERS = {
            Blk.DANDELION, Blk.POPPY, Blk.AZURE_BLUET, Blk.OXEYE_DAISY, Blk.CORNFLOWER
    };
    private static final short[] MEADOW_FLOWERS = {
            Blk.DANDELION, Blk.POPPY, Blk.ALLIUM, Blk.AZURE_BLUET, Blk.OXEYE_DAISY, Blk.CORNFLOWER
    };
    private static final short[] FOREST_FLOWERS = {
            Blk.DANDELION,
            Blk.POPPY,
            Blk.ALLIUM,
            Blk.AZURE_BLUET,
            Blk.RED_TULIP,
            Blk.ORANGE_TULIP,
            Blk.WHITE_TULIP,
            Blk.PINK_TULIP,
            Blk.OXEYE_DAISY,
            Blk.CORNFLOWER,
            Blk.LILY_OF_THE_VALLEY
    };
    private final GeneratorSettings settings;
    private final TerrainStage terrain;

    public DecorationStage(final GeneratorSettings settings, final TerrainStage terrain) {
        this.settings = settings;
        this.terrain = terrain;
    }

    private static boolean isKelpBiome(final BiomeType biome) {
        return switch (biome) {
            case OCEAN, DEEP_OCEAN, COLD_OCEAN, DEEP_COLD_OCEAN, LUKEWARM_OCEAN, DEEP_LUKEWARM_OCEAN, FROZEN_OCEAN,
                 DEEP_FROZEN_OCEAN -> true;
            default -> false;
        };
    }

    private static short pick(final Random random, final short[] values) {
        return values[random.nextInt(values.length)];
    }

    private static Deco decoFor(final BiomeType biome) {
        return switch (biome) {
            case PLAINS -> Deco.of(TreeKind.OAK, 0.15, 30, 6);
            case SUNFLOWER_PLAINS -> Deco.of(TreeKind.OAK, 0.15, 30, 16);
            case MEADOW -> Deco.of(TreeKind.OAK, 0.05, 40, 20);
            case CHERRY_GROVE -> Deco.of(TreeKind.CHERRY, 0.8, 32, 16);
            case SNOWY_PLAINS -> Deco.of(TreeKind.SPRUCE, 0.03, 6, 0);

            case FOREST -> new Deco(TreeKind.OAK, TreeKind.BIRCH, 0.2, 10.0, 22, 4);
            case FLOWER_FOREST -> new Deco(TreeKind.OAK, TreeKind.BIRCH, 0.3, 7.0, 22, 34);
            case BIRCH_FOREST -> Deco.of(TreeKind.BIRCH, 10.0, 22, 4);
            case OLD_GROWTH_BIRCH_FOREST -> Deco.of(TreeKind.TALL_BIRCH, 12.0, 22, 6);
            case DARK_FOREST -> new Deco(TreeKind.DARK_OAK, TreeKind.OAK, 0.2, 11.0, 12, 2);
            case PALE_GARDEN -> Deco.of(TreeKind.PALE_OAK, 10.0, 10, 0);

            case TAIGA -> Deco.of(TreeKind.SPRUCE, 10.0, 22, 2);
            case SNOWY_TAIGA -> Deco.of(TreeKind.SPRUCE, 8.0, 12, 0);
            case OLD_GROWTH_SPRUCE_TAIGA -> new Deco(TreeKind.MEGA_SPRUCE, TreeKind.SPRUCE, 0.55, 9.0, 24, 2);
            case OLD_GROWTH_PINE_TAIGA -> new Deco(TreeKind.PINE, TreeKind.SPRUCE, 0.4, 9.0, 24, 2);
            case GROVE -> Deco.of(TreeKind.SPRUCE, 9.0, 8, 0);

            case SAVANNA -> new Deco(TreeKind.ACACIA, TreeKind.OAK, 0.1, 1.5, 44, 2);
            case SAVANNA_PLATEAU -> new Deco(TreeKind.ACACIA, TreeKind.OAK, 0.1, 1.2, 44, 2);
            case WINDSWEPT_SAVANNA -> new Deco(TreeKind.ACACIA, TreeKind.OAK, 0.2, 0.8, 30, 2);

            case JUNGLE -> new Deco(TreeKind.JUNGLE, TreeKind.MEGA_JUNGLE, 0.15, 22.0, 50, 5);
            case SPARSE_JUNGLE -> Deco.of(TreeKind.JUNGLE, 3.0, 32, 3);
            case BAMBOO_JUNGLE -> Deco.of(TreeKind.JUNGLE, 6.0, 28, 3);

            case SWAMP -> Deco.of(TreeKind.SWAMP_OAK, 2.5, 16, 2);
            case MANGROVE_SWAMP -> Deco.of(TreeKind.MANGROVE, 4.0, 10, 0);

            case WOODED_BADLANDS -> Deco.of(TreeKind.OAK, 3.0, 8, 0);

            case WINDSWEPT_HILLS -> new Deco(TreeKind.OAK, TreeKind.SPRUCE, 0.5, 1.0, 16, 2);
            case WINDSWEPT_GRAVELLY_HILLS -> new Deco(TreeKind.OAK, TreeKind.SPRUCE, 0.5, 0.5, 10, 2);
            case WINDSWEPT_FOREST -> new Deco(TreeKind.SPRUCE, TreeKind.OAK, 0.35, 8.0, 16, 2);

            case MUSHROOM_FIELDS -> new Deco(TreeKind.HUGE_RED_MUSHROOM, TreeKind.HUGE_BROWN_MUSHROOM, 0.5, 1.2, 0, 0);

            case RIVER, FROZEN_RIVER -> Deco.plants(4, 0);

            default -> Deco.EMPTY;
        };
    }

    public void decorate(final ChunkScratch scratch) {
        plantTrees(scratch);

        final long seed = settings.seed();
        final int chunkX = scratch.chunkX();
        final int chunkZ = scratch.chunkZ();

        groundCover(scratch, Seeds.randomForChunk(seed, chunkX, chunkZ, "feature/ground"));
        specialities(scratch, Seeds.randomForChunk(seed, chunkX, chunkZ, "feature/special"));
        underwater(scratch, Seeds.randomForChunk(seed, chunkX, chunkZ, "feature/underwater"));
        caves(scratch, Seeds.randomForChunk(seed, chunkX, chunkZ, "feature/cave"));
    }

    private void plantTrees(final ChunkScratch scratch) {
        for (int neighbourZ = -1; neighbourZ <= 1; neighbourZ++) {
            for (int neighbourX = -1; neighbourX <= 1; neighbourX++) {
                plantTreesOf(scratch, scratch.chunkX() + neighbourX, scratch.chunkZ() + neighbourZ);
            }
        }
    }

    private void plantTreesOf(final ChunkScratch scratch, final int chunkX, final int chunkZ) {
        final Random random = Seeds.randomForChunk(settings.seed(), chunkX, chunkZ, "feature/tree");

        final BiomeType centre = biomeAt((chunkX << 4) + 8, (chunkZ << 4) + 8);
        final Deco deco = decoFor(centre);
        int count = (int) deco.trees();
        if (random.nextDouble() < deco.trees() - count) {
            count++;
        }

        final int originX = scratch.originX();
        final int originZ = scratch.originZ();

        for (int attempt = 0; attempt < count; attempt++) {
            final int worldX = (chunkX << 4) + random.nextInt(16);
            final int worldZ = (chunkZ << 4) + random.nextInt(16);

            final int localX = worldX - originX;
            final int localZ = worldZ - originZ;
            if (localX < -Trees.MAX_RADIUS || localX > 15 + Trees.MAX_RADIUS) {
                continue;
            }
            if (localZ < -Trees.MAX_RADIUS || localZ > 15 + Trees.MAX_RADIUS) {
                continue;
            }
            plantOne(scratch, worldX, worldZ, localX, localZ);
        }
    }

    private void plantOne(
            final ChunkScratch scratch, final int worldX, final int worldZ, final int localX, final int localZ) {

        final BiomeType biome = biomeAt(worldX, worldZ);
        final Deco deco = decoFor(biome);
        if (deco.tree() == TreeKind.NONE && deco.altTree() == TreeKind.NONE) {
            return;
        }

        final Random shape = Seeds.randomForChunk(settings.seed(), worldX, worldZ, "feature/tree_shape");
        final TreeKind kind = shape.nextDouble() < deco.altShare() ? deco.altTree() : deco.tree();
        if (kind == TreeKind.NONE) {
            return;
        }

        final int ground = terrain.surfaceAt(worldX, worldZ, scratch.minY(), scratch.maxY());
        if (ground > scratch.maxY() - 32) {
            return;
        }

        final int minimum = biome == BiomeType.MANGROVE_SWAMP ? settings.seaLevel() - 6 : settings.seaLevel();
        if (ground <= minimum) {
            return;
        }

        Trees.place(kind, scratch, shape, localX, ground, localZ);
    }

    private BiomeType biomeAt(final int worldX, final int worldZ) {
        return BiomeType.of(BiomeTable.surface(terrain.climateAt(worldX, worldZ)));
    }

    private void groundCover(final ChunkScratch scratch, final Random random) {
        final BiomeType biome = BiomeType.of(scratch.surfaceBiome(8, 8));
        final Deco deco = decoFor(biome);

        for (int attempt = 0; attempt < deco.grass(); attempt++) {
            final int x = random.nextInt(16);
            final int z = random.nextInt(16);
            final int y = groundOf(scratch, x, z);
            if (y == Integer.MIN_VALUE) {
                continue;
            }
            final BiomeType local = BiomeType.of(scratch.surfaceBiome(x, z));
            placeGrass(scratch, random, x, y, z, local);
        }

        for (int attempt = 0; attempt < deco.flowers(); attempt++) {
            final int x = random.nextInt(16);
            final int z = random.nextInt(16);
            final int y = groundOf(scratch, x, z);
            if (y == Integer.MIN_VALUE) {
                continue;
            }
            placeFlower(scratch, random, x, y, z, BiomeType.of(scratch.surfaceBiome(x, z)));
        }
    }

    private void placeGrass(
            final ChunkScratch scratch,
            final Random random,
            final int x,
            final int y,
            final int z,
            final BiomeType biome) {

        final boolean fernBiome = switch (biome) {
            case TAIGA, SNOWY_TAIGA, OLD_GROWTH_SPRUCE_TAIGA, OLD_GROWTH_PINE_TAIGA, GROVE, JUNGLE, SPARSE_JUNGLE,
                 BAMBOO_JUNGLE -> true;
            default -> false;
        };

        final int roll = random.nextInt(10);
        if (roll == 0) {
            final short lower = fernBiome ? Blk.LARGE_FERN : Blk.TALL_GRASS;
            final short upper = fernBiome ? Blk.LARGE_FERN_UPPER : Blk.TALL_GRASS_UPPER;
            if (scratch.get(x, y + 2, z) == Blk.AIR) {
                scratch.setIfReplaceable(x, y + 1, z, lower);
                scratch.setIfReplaceable(x, y + 2, z, upper);
            }
            return;
        }
        scratch.setIfReplaceable(x, y + 1, z, fernBiome && roll < 4 ? Blk.FERN : Blk.SHORT_GRASS);
    }

    private void placeFlower(
            final ChunkScratch scratch,
            final Random random,
            final int x,
            final int y,
            final int z,
            final BiomeType biome) {

        switch (biome) {
            case SUNFLOWER_PLAINS -> {
                if (random.nextInt(3) == 0 && scratch.get(x, y + 2, z) == Blk.AIR) {
                    scratch.setIfReplaceable(x, y + 1, z, Blk.SUNFLOWER);
                    scratch.setIfReplaceable(x, y + 2, z, Blk.SUNFLOWER_UPPER);
                    return;
                }
                scratch.setIfReplaceable(x, y + 1, z, pick(random, PLAIN_FLOWERS));
            }
            case FLOWER_FOREST -> {
                if (random.nextInt(4) == 0 && scratch.get(x, y + 2, z) == Blk.AIR) {
                    final int kind = random.nextInt(3);
                    final short lower = kind == 0 ? Blk.LILAC : kind == 1 ? Blk.ROSE_BUSH : Blk.PEONY;
                    final short upper =
                            kind == 0 ? Blk.LILAC_UPPER : kind == 1 ? Blk.ROSE_BUSH_UPPER : Blk.PEONY_UPPER;
                    scratch.setIfReplaceable(x, y + 1, z, lower);
                    scratch.setIfReplaceable(x, y + 2, z, upper);
                    return;
                }
                scratch.setIfReplaceable(x, y + 1, z, pick(random, FOREST_FLOWERS));
            }
            case MEADOW, CHERRY_GROVE -> scratch.setIfReplaceable(x, y + 1, z, pick(random, MEADOW_FLOWERS));
            case SWAMP, MANGROVE_SWAMP -> scratch.setIfReplaceable(x, y + 1, z, Blk.BLUE_ORCHID);
            case JUNGLE, BAMBOO_JUNGLE, SPARSE_JUNGLE ->
                    scratch.setIfReplaceable(x, y + 1, z, random.nextBoolean() ? Blk.BLUE_ORCHID : Blk.POPPY);
            default -> scratch.setIfReplaceable(x, y + 1, z, pick(random, PLAIN_FLOWERS));
        }
    }

    private void specialities(final ChunkScratch scratch, final Random random) {
        final BiomeType biome = BiomeType.of(scratch.surfaceBiome(8, 8));

        switch (biome) {
            case DESERT -> {
                scatterCactus(scratch, random, 6);
                scatter(scratch, random, 4, Blk.DEAD_BUSH);
            }
            case BADLANDS, ERODED_BADLANDS, WOODED_BADLANDS -> {
                scatterCactus(scratch, random, 2);
                scatter(scratch, random, 6, Blk.DEAD_BUSH);
            }
            case SAVANNA, SAVANNA_PLATEAU, WINDSWEPT_SAVANNA -> scatter(scratch, random, 1, Blk.DEAD_BUSH);
            case SNOWY_PLAINS, SNOWY_TAIGA, ICE_SPIKES -> scatter(scratch, random, 2, Blk.DEAD_BUSH);
            case TAIGA, OLD_GROWTH_SPRUCE_TAIGA, OLD_GROWTH_PINE_TAIGA -> {
                scatter(scratch, random, 3, Blk.SWEET_BERRY_BUSH);
                scatterMushrooms(scratch, random, 2);
            }
            case DARK_FOREST, PALE_GARDEN -> {
                scatterMushrooms(scratch, random, 6);
                scatterHugeMushrooms(scratch, random, 1);
            }
            case MUSHROOM_FIELDS -> scatterMushrooms(scratch, random, 12);
            case SWAMP -> {
                scatterMushrooms(scratch, random, 4);
                scatterLilyPads(scratch, random, 6);
            }
            case MANGROVE_SWAMP -> scatterLilyPads(scratch, random, 3);
            case JUNGLE -> {
                scatterBamboo(scratch, random, 6);
                scatterMelons(scratch, random, 2);
            }
            case BAMBOO_JUNGLE -> {
                scatterBamboo(scratch, random, 40);
                scatterMelons(scratch, random, 1);
            }
            case SPARSE_JUNGLE -> scatterBamboo(scratch, random, 2);
            default -> {
            }
        }

        scatterSugarCane(scratch, random, 10);
        if (random.nextInt(24) == 0) {
            scatter(scratch, random, 1, Blk.PUMPKIN);
        }
    }

    private void scatter(final ChunkScratch scratch, final Random random, final int attempts, final short plant) {
        for (int i = 0; i < attempts; i++) {
            final int x = random.nextInt(16);
            final int z = random.nextInt(16);
            final int y = groundOf(scratch, x, z);
            if (y != Integer.MIN_VALUE) {
                scratch.setIfReplaceable(x, y + 1, z, plant);
            }
        }
    }

    private void scatterMushrooms(final ChunkScratch scratch, final Random random, final int attempts) {
        for (int i = 0; i < attempts; i++) {
            final int x = random.nextInt(16);
            final int z = random.nextInt(16);
            final int y = groundOf(scratch, x, z);
            if (y != Integer.MIN_VALUE) {
                scratch.setIfReplaceable(
                        x, y + 1, z, random.nextBoolean() ? Blk.RED_MUSHROOM : Blk.BROWN_MUSHROOM);
            }
        }
    }

    private void scatterHugeMushrooms(final ChunkScratch scratch, final Random random, final int attempts) {
        for (int i = 0; i < attempts; i++) {
            final int x = 3 + random.nextInt(10);
            final int z = 3 + random.nextInt(10);
            final int y = groundOf(scratch, x, z);
            if (y == Integer.MIN_VALUE) {
                continue;
            }
            Trees.place(
                    random.nextBoolean() ? TreeKind.HUGE_RED_MUSHROOM : TreeKind.HUGE_BROWN_MUSHROOM,
                    scratch,
                    random,
                    x,
                    y,
                    z);
        }
    }

    private void scatterCactus(final ChunkScratch scratch, final Random random, final int attempts) {
        for (int i = 0; i < attempts; i++) {
            final int x = random.nextInt(16);
            final int z = random.nextInt(16);
            final int y = groundOf(scratch, x, z);
            if (y == Integer.MIN_VALUE) {
                continue;
            }
            final short soil = scratch.get(x, y, z);
            if (soil != Blk.SAND && soil != Blk.RED_SAND) {
                continue;
            }
            final int height = 1 + random.nextInt(3);
            for (int step = 1; step <= height; step++) {
                if (!scratch.setIfReplaceable(x, y + step, z, Blk.CACTUS)) {
                    break;
                }
            }
        }
    }

    private void scatterSugarCane(final ChunkScratch scratch, final Random random, final int attempts) {
        for (int i = 0; i < attempts; i++) {
            final int x = random.nextInt(16);
            final int z = random.nextInt(16);
            final int y = groundOf(scratch, x, z);
            if (y == Integer.MIN_VALUE || !nextToWater(scratch, x, y, z)) {
                continue;
            }
            final short soil = scratch.get(x, y, z);
            if (soil != Blk.GRASS_BLOCK && soil != Blk.DIRT && soil != Blk.SAND && soil != Blk.RED_SAND
                    && soil != Blk.MUD) {
                continue;
            }
            final int height = 2 + random.nextInt(2);
            for (int step = 1; step <= height; step++) {
                if (!scratch.setIfReplaceable(x, y + step, z, Blk.SUGAR_CANE)) {
                    break;
                }
            }
        }
    }

    private void scatterBamboo(final ChunkScratch scratch, final Random random, final int attempts) {
        for (int i = 0; i < attempts; i++) {
            final int x = random.nextInt(16);
            final int z = random.nextInt(16);
            final int y = groundOf(scratch, x, z);
            if (y == Integer.MIN_VALUE) {
                continue;
            }
            final int height = 6 + random.nextInt(10);
            for (int step = 1; step <= height; step++) {
                final short segment;
                if (step >= height - 1) {
                    segment = Blk.BAMBOO_LARGE_LEAVES;
                } else if (step >= height - 3) {
                    segment = Blk.BAMBOO_SMALL_LEAVES;
                } else {
                    segment = Blk.BAMBOO;
                }
                if (!scratch.setIfReplaceable(x, y + step, z, segment)) {
                    break;
                }
            }
        }
    }

    private void scatterMelons(final ChunkScratch scratch, final Random random, final int attempts) {
        for (int i = 0; i < attempts; i++) {
            final int x = random.nextInt(16);
            final int z = random.nextInt(16);
            final int y = groundOf(scratch, x, z);
            if (y != Integer.MIN_VALUE && Blk.isSoil(scratch.get(x, y, z))) {
                scratch.setIfReplaceable(x, y + 1, z, Blk.MELON);
            }
        }
    }

    private void scatterLilyPads(final ChunkScratch scratch, final Random random, final int attempts) {
        final int seaLevel = settings.seaLevel();
        for (int i = 0; i < attempts; i++) {
            final int x = random.nextInt(16);
            final int z = random.nextInt(16);
            if (scratch.get(x, seaLevel, z) == Blk.WATER && scratch.get(x, seaLevel + 1, z) == Blk.AIR) {
                scratch.set(x, seaLevel + 1, z, Blk.LILY_PAD);
            }
        }
    }

    private void underwater(final ChunkScratch scratch, final Random random) {
        final int seaLevel = settings.seaLevel();

        for (int attempt = 0; attempt < 24; attempt++) {
            final int x = random.nextInt(16);
            final int z = random.nextInt(16);
            final int floor = scratch.surfaceY(x, z);
            if (floor >= seaLevel || floor < scratch.minY()) {
                continue;
            }
            if (scratch.get(x, floor + 1, z) != Blk.WATER) {
                continue;
            }

            final BiomeType biome = BiomeType.of(scratch.surfaceBiome(x, z));
            final int depth = seaLevel - floor;

            if (random.nextInt(3) == 0 && depth > 3 && isKelpBiome(biome)) {
                growKelp(scratch, random, x, floor, z, seaLevel);
                continue;
            }
            if (biome == BiomeType.WARM_OCEAN && random.nextInt(6) == 0 && depth > 2) {
                growCoral(scratch, random, x, floor, z);
                continue;
            }
            if (random.nextInt(4) == 0 && depth > 2) {
                scratch.setIfReplaceable(x, floor + 1, z, Blk.SEAGRASS);
                continue;
            }
            if (random.nextInt(12) == 0 && depth > 4) {
                if (scratch.get(x, floor + 2, z) == Blk.WATER) {
                    scratch.set(x, floor + 1, z, Blk.TALL_SEAGRASS);
                    scratch.set(x, floor + 2, z, Blk.TALL_SEAGRASS_UPPER);
                }
            }
        }
    }

    private void growKelp(
            final ChunkScratch scratch,
            final Random random,
            final int x,
            final int floor,
            final int z,
            final int seaLevel) {

        final int height = 3 + random.nextInt(seaLevel - floor - 1);
        int y = floor + 1;
        for (int step = 0; step < height; step++, y++) {
            if (scratch.get(x, y, z) != Blk.WATER) {
                return;
            }
            scratch.set(x, y, z, Blk.KELP_PLANT);
        }
        if (scratch.get(x, y, z) == Blk.WATER) {
            scratch.set(x, y, z, Blk.KELP);
        }
    }

    private void growCoral(
            final ChunkScratch scratch, final Random random, final int x, final int floor, final int z) {

        final short[] corals = {
                Blk.TUBE_CORAL_BLOCK,
                Blk.BRAIN_CORAL_BLOCK,
                Blk.HORN_CORAL_BLOCK,
                Blk.FIRE_CORAL_BLOCK,
                Blk.BUBBLE_CORAL_BLOCK
        };
        final short coral = pick(random, corals);
        final int radius = 1 + random.nextInt(2);

        for (int dz = -radius; dz <= radius; dz++) {
            for (int dx = -radius; dx <= radius; dx++) {
                if (dx * dx + dz * dz > radius * radius) {
                    continue;
                }
                final int height = random.nextInt(3);
                for (int step = 0; step <= height; step++) {
                    if (scratch.get(x + dx, floor + 1 + step, z + dz) != Blk.WATER) {
                        break;
                    }
                    scratch.set(x + dx, floor + 1 + step, z + dz, coral);
                }
            }
        }
        if (random.nextBoolean()) {
            scratch.setIfReplaceable(x, floor + 2, z, Blk.SEA_PICKLE);
        }
    }

    private void caves(final ChunkScratch scratch, final Random random) {
        for (int attempt = 0; attempt < 40; attempt++) {
            final int x = random.nextInt(16);
            final int z = random.nextInt(16);
            final int ceiling = Math.min(scratch.surfaceY(x, z) - 6, settings.seaLevel());
            if (ceiling <= scratch.minY() + 6) {
                continue;
            }
            final int y = scratch.minY() + 5 + random.nextInt(ceiling - scratch.minY() - 5);
            if (scratch.get(x, y, z) != Blk.AIR) {
                continue;
            }

            final boolean hasFloor = Blk.isSolid(scratch.get(x, y - 1, z));
            final boolean hasRoof = Blk.isSolid(scratch.get(x, y + 1, z));
            if (!hasFloor && !hasRoof) {
                continue;
            }

            decorateCaveSpot(
                    scratch, random, x, y, z, BiomeType.of(scratch.surfaceBiome(x, z)), hasFloor, hasRoof, y);
        }
    }

    private void decorateCaveSpot(
            final ChunkScratch scratch,
            final Random random,
            final int x,
            final int y,
            final int z,
            final BiomeType surfaceBiome,
            final boolean hasFloor,
            final boolean hasRoof,
            final int worldY) {

        final BiomeType biome = caveBiomeAt(scratch, x, worldY, z, surfaceBiome);

        switch (biome) {
            case DRIPSTONE_CAVES -> {
                if (hasRoof) {
                    hang(scratch, random, x, y, z, Blk.DRIPSTONE_DOWN_MIDDLE, Blk.DRIPSTONE_DOWN, -1);
                } else {
                    hang(scratch, random, x, y, z, Blk.DRIPSTONE_UP_MIDDLE, Blk.DRIPSTONE_UP, 1);
                }
            }
            case LUSH_CAVES -> {
                if (hasFloor) {
                    if (Blk.isSolid(scratch.get(x, y - 1, z))) {
                        scratch.set(x, y - 1, z, Blk.MOSS_BLOCK);
                    }
                    final int roll = random.nextInt(6);
                    if (roll == 0) {
                        scratch.setIfReplaceable(x, y, z, Blk.AZALEA);
                    } else if (roll == 1) {
                        scratch.setIfReplaceable(x, y, z, Blk.FLOWERING_AZALEA);
                    } else if (roll == 2) {
                        scratch.setIfReplaceable(x, y, z, Blk.SMALL_DRIPLEAF);
                    } else {
                        scratch.setIfReplaceable(x, y, z, Blk.MOSS_CARPET);
                    }
                } else {
                    if (random.nextInt(4) == 0) {
                        scratch.set(x, y + 1, z, Blk.MOSS_BLOCK);
                        scratch.setIfReplaceable(x, y, z, Blk.SPORE_BLOSSOM);
                    } else {
                        growCaveVines(scratch, random, x, y, z);
                    }
                }
            }
            case DEEP_DARK -> {
                if (hasFloor) {
                    scratch.set(x, y - 1, z, Blk.SCULK);
                }
                if (random.nextInt(3) == 0) {
                    scratch.setIfReplaceable(x, y, z, Blk.SCULK_VEIN);
                }
            }
            case SULFUR_CAVES -> {
                if (hasFloor && random.nextInt(4) == 0) {
                    scratch.set(x, y - 1, z, Blk.MAGMA_BLOCK);
                }
            }
            default -> {
                if (hasRoof && random.nextInt(3) == 0) {
                    scratch.setIfReplaceable(x, y, z, Blk.GLOW_LICHEN_DOWN);
                } else if (hasRoof && random.nextInt(8) == 0) {
                    scratch.setIfReplaceable(x, y, z, Blk.COBWEB);
                } else if (hasFloor && random.nextInt(6) == 0) {
                    scratch.setIfReplaceable(x, y, z, Blk.HANGING_ROOTS);
                }
            }
        }
    }

    private BiomeType caveBiomeAt(
            final ChunkScratch scratch, final int x, final int y, final int z, final BiomeType fallback) {
        final int cellY = Math.clamp((y - scratch.minY()) / ChunkScratch.CELL, 0, scratch.cellCountY() - 1);
        final var key = scratch.biomeCell(x >> 2, cellY, z >> 2);
        return BiomeType.of(key);
    }

    private void hang(
            final ChunkScratch scratch,
            final Random random,
            final int x,
            final int y,
            final int z,
            final short body,
            final short tip,
            final int direction) {

        final int length = 1 + random.nextInt(4);
        for (int step = 0; step < length; step++) {
            final int currentY = y + direction * step;
            final boolean last = step == length - 1;
            if (!scratch.setIfReplaceable(x, currentY, z, last ? tip : body)) {
                return;
            }
        }
    }

    private void growCaveVines(
            final ChunkScratch scratch, final Random random, final int x, final int y, final int z) {
        final int length = 2 + random.nextInt(8);
        for (int step = 0; step < length; step++) {
            final boolean last = step == length - 1;
            final short segment = last
                    ? (random.nextInt(4) == 0 ? Blk.CAVE_VINES_BERRIES : Blk.CAVE_VINES)
                    : Blk.CAVE_VINES_PLANT;
            if (!scratch.setIfReplaceable(x, y - step, z, segment)) {
                return;
            }
        }
    }

    private int groundOf(final ChunkScratch scratch, final int x, final int z) {
        final int recorded = scratch.surfaceY(x, z);
        if (recorded < scratch.minY()) {
            return Integer.MIN_VALUE;
        }
        for (int y = Math.min(recorded, scratch.maxY() - 2); y >= recorded - 4 && y > scratch.minY(); y--) {
            if (!Blk.isSolid(scratch.get(x, y, z))) {
                continue;
            }
            if (scratch.get(x, y + 1, z) != Blk.AIR) {
                return Integer.MIN_VALUE;
            }
            return Blk.isSoil(scratch.get(x, y, z)) || scratch.get(x, y, z) == Blk.SAND
                    || scratch.get(x, y, z) == Blk.RED_SAND
                    ? y
                    : Integer.MIN_VALUE;
        }
        return Integer.MIN_VALUE;
    }

    private boolean nextToWater(final ChunkScratch scratch, final int x, final int y, final int z) {
        return scratch.get(x + 1, y, z) == Blk.WATER
                || scratch.get(x - 1, y, z) == Blk.WATER
                || scratch.get(x, y, z + 1) == Blk.WATER
                || scratch.get(x, y, z - 1) == Blk.WATER;
    }

    private record Deco(
            TreeKind tree, TreeKind altTree, double altShare, double trees, int grass, int flowers) {

        static final Deco EMPTY = new Deco(TreeKind.NONE, TreeKind.NONE, 0.0, 0.0, 0, 0);

        static Deco of(final TreeKind tree, final double trees, final int grass, final int flowers) {
            return new Deco(tree, TreeKind.NONE, 0.0, trees, grass, flowers);
        }

        static Deco plants(final int grass, final int flowers) {
            return new Deco(TreeKind.NONE, TreeKind.NONE, 0.0, 0.0, grass, flowers);
        }
    }
}
