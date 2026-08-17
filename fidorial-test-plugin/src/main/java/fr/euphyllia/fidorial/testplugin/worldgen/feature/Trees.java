package fr.euphyllia.fidorial.testplugin.worldgen.feature;

import fr.euphyllia.fidorial.testplugin.worldgen.Blk;
import fr.euphyllia.fidorial.testplugin.worldgen.ChunkScratch;

import java.util.Random;

public final class Trees {
    public static final int MAX_RADIUS = 8;

    private Trees() {
        throw new UnsupportedOperationException("Trees cannot be instantiated.");
    }

    public static void place(
            final TreeKind kind,
            final ChunkScratch scratch,
            final Random random,
            final int x,
            final int groundY,
            final int z) {

        switch (kind) {
            case OAK -> blobTree(scratch, random, x, groundY, z, Blk.OAK_LOG, Blk.OAK_LEAVES, 4, 6, false);
            case BIG_OAK -> blobTree(scratch, random, x, groundY, z, Blk.OAK_LOG, Blk.OAK_LEAVES, 7, 11, false);
            case SWAMP_OAK -> blobTree(scratch, random, x, groundY, z, Blk.OAK_LOG, Blk.OAK_LEAVES, 5, 7, true);
            case BIRCH -> blobTree(scratch, random, x, groundY, z, Blk.BIRCH_LOG, Blk.BIRCH_LEAVES, 5, 7, false);
            case TALL_BIRCH -> blobTree(scratch, random, x, groundY, z, Blk.BIRCH_LOG, Blk.BIRCH_LEAVES, 8, 12, false);
            case SPRUCE -> spruce(scratch, random, x, groundY, z);
            case PINE -> pine(scratch, random, x, groundY, z);
            case MEGA_SPRUCE -> megaSpruce(scratch, random, x, groundY, z);
            case JUNGLE -> jungle(scratch, random, x, groundY, z);
            case MEGA_JUNGLE -> megaJungle(scratch, random, x, groundY, z);
            case ACACIA -> acacia(scratch, random, x, groundY, z);
            case DARK_OAK -> canopyTree(scratch, random, x, groundY, z, Blk.DARK_OAK_LOG, Blk.DARK_OAK_LEAVES);
            case PALE_OAK -> canopyTree(scratch, random, x, groundY, z, Blk.PALE_OAK_LOG, Blk.PALE_OAK_LEAVES);
            case CHERRY -> cherry(scratch, random, x, groundY, z);
            case MANGROVE -> mangrove(scratch, random, x, groundY, z);
            case HUGE_RED_MUSHROOM -> hugeMushroom(scratch, random, x, groundY, z, true);
            case HUGE_BROWN_MUSHROOM -> hugeMushroom(scratch, random, x, groundY, z, false);
            case NONE -> {
            }
        }
    }

    private static void blobTree(
            final ChunkScratch scratch,
            final Random random,
            final int x,
            final int groundY,
            final int z,
            final short log,
            final short leaves,
            final int minHeight,
            final int maxHeight,
            final boolean vines) {

        final int height = minHeight + random.nextInt(maxHeight - minHeight + 1);
        final int top = groundY + height;

        for (int layer = 0; layer < 4; layer++) {
            final int y = top - 3 + layer;
            final int radius = layer <= 1 ? 2 : 1;
            disc(scratch, random, x, y, z, radius, leaves, radius == 2);
        }
        scratch.setIfReplaceable(x, top + 1, z, leaves);

        trunk(scratch, x, groundY + 1, top, z, log);

        if (vines) {
            drapeVines(scratch, random, x, top, z, 3);
        }
    }

    private static void spruce(
            final ChunkScratch scratch, final Random random, final int x, final int groundY, final int z) {

        final int height = 7 + random.nextInt(6);
        final int top = groundY + height;
        final int leavesBottom = groundY + 2 + random.nextInt(2);
        final int maxRadius = 2 + random.nextInt(2);

        int radius = 0;
        int step = 0;
        for (int y = top; y >= leavesBottom; y--) {
            disc(scratch, random, x, y, z, radius, Blk.SPRUCE_LEAVES, false);
            step++;
            if (step % 2 == 0) {
                radius++;
            }
            if (radius > maxRadius) {
                radius = 1;
            }
        }
        scratch.setIfReplaceable(x, top + 1, z, Blk.SPRUCE_LEAVES);
        trunk(scratch, x, groundY + 1, top, z, Blk.SPRUCE_LOG);
    }

    private static void pine(
            final ChunkScratch scratch, final Random random, final int x, final int groundY, final int z) {

        final int height = 12 + random.nextInt(9);
        final int top = groundY + height;
        final int[] radii = {1, 2, 3, 2, 1};

        for (int i = 0; i < radii.length; i++) {
            disc(scratch, random, x, top - radii.length + 1 + i, z, radii[radii.length - 1 - i], Blk.SPRUCE_LEAVES,
                    false);
        }
        scratch.setIfReplaceable(x, top + 1, z, Blk.SPRUCE_LEAVES);
        trunk(scratch, x, groundY + 1, top, z, Blk.SPRUCE_LOG);
    }

    private static void megaSpruce(
            final ChunkScratch scratch, final Random random, final int x, final int groundY, final int z) {

        final int height = 17 + random.nextInt(9);
        final int top = groundY + height;
        final int leavesBottom = groundY + 5 + random.nextInt(4);

        int radius = 0;
        int step = 0;
        for (int y = top; y >= leavesBottom; y--) {
            wideDisc(scratch, random, x, y, z, radius, Blk.SPRUCE_LEAVES);
            step++;
            if (step % 2 == 0) {
                radius++;
            }
            if (radius > 4) {
                radius = 1;
            }
        }
        scratch.setIfReplaceable(x, top + 1, z, Blk.SPRUCE_LEAVES);
        scratch.setIfReplaceable(x + 1, top + 1, z, Blk.SPRUCE_LEAVES);

        for (int dz = 0; dz <= 1; dz++) {
            for (int dx = 0; dx <= 1; dx++) {
                trunk(scratch, x + dx, groundY + 1, top, z + dz, Blk.SPRUCE_LOG);
                for (int rz = -2; rz <= 3; rz++) {
                    for (int rx = -2; rx <= 3; rx++) {
                        if (rx * rx + rz * rz <= 7 && random.nextInt(3) != 0) {
                            replaceSoil(scratch, x + rx, groundY, z + rz, Blk.PODZOL);
                        }
                    }
                }
            }
        }
    }

    private static void jungle(
            final ChunkScratch scratch, final Random random, final int x, final int groundY, final int z) {

        final int height = 8 + random.nextInt(8);
        final int top = groundY + height;

        disc(scratch, random, x, top - 2, z, 2, Blk.JUNGLE_LEAVES, true);
        disc(scratch, random, x, top - 1, z, 2, Blk.JUNGLE_LEAVES, true);
        disc(scratch, random, x, top, z, 1, Blk.JUNGLE_LEAVES, false);
        scratch.setIfReplaceable(x, top + 1, z, Blk.JUNGLE_LEAVES);

        trunk(scratch, x, groundY + 1, top, z, Blk.JUNGLE_LOG);
        climbVines(scratch, random, x, groundY + 1, top, z);
        drapeVines(scratch, random, x, top, z, 6);
    }

    private static void megaJungle(
            final ChunkScratch scratch, final Random random, final int x, final int groundY, final int z) {

        final int height = 14 + random.nextInt(9);
        final int top = groundY + height;

        for (int dz = 0; dz <= 1; dz++) {
            for (int dx = 0; dx <= 1; dx++) {
                trunk(scratch, x + dx, groundY + 1, top, z + dz, Blk.JUNGLE_LOG);
            }
        }

        wideDisc(scratch, random, x, top - 1, z, 3, Blk.JUNGLE_LEAVES);
        wideDisc(scratch, random, x, top, z, 2, Blk.JUNGLE_LEAVES);
        wideDisc(scratch, random, x, top + 1, z, 1, Blk.JUNGLE_LEAVES);

        final int branches = 2 + random.nextInt(2);
        for (int i = 0; i < branches; i++) {
            final int branchY = groundY + 6 + random.nextInt(Math.max(1, height - 8));
            final int dirX = random.nextInt(3) - 1;
            final int dirZ = dirX == 0 ? (random.nextBoolean() ? 1 : -1) : random.nextInt(3) - 1;
            final int length = 2 + random.nextInt(3);
            int bx = x;
            int bz = z;
            for (int step = 0; step < length; step++) {
                bx += dirX;
                bz += dirZ;
                scratch.set(bx, branchY, bz, Blk.JUNGLE_LOG);
            }
            disc(scratch, random, bx, branchY, bz, 2, Blk.JUNGLE_LEAVES, true);
            disc(scratch, random, bx, branchY + 1, bz, 1, Blk.JUNGLE_LEAVES, false);
        }

        drapeVines(scratch, random, x, top, z, 8);
    }

    private static void acacia(
            final ChunkScratch scratch, final Random random, final int x, final int groundY, final int z) {

        final int straight = 3 + random.nextInt(3);
        trunk(scratch, x, groundY + 1, groundY + straight, z, Blk.ACACIA_LOG);

        final int dirX = random.nextBoolean() ? 1 : -1;
        final int dirZ = random.nextBoolean() ? 1 : -1;
        final boolean alongX = random.nextBoolean();

        int bx = x;
        int bz = z;
        int by = groundY + straight;
        final int bend = 2 + random.nextInt(3);
        for (int step = 0; step < bend; step++) {
            by++;
            if (alongX) {
                bx += dirX;
            } else {
                bz += dirZ;
            }
            scratch.set(bx, by, bz, Blk.ACACIA_LOG);
        }

        disc(scratch, random, bx, by + 1, bz, 3, Blk.ACACIA_LEAVES, true);
        disc(scratch, random, bx, by + 2, bz, 2, Blk.ACACIA_LEAVES, true);

        if (random.nextBoolean()) {
            int sx = x;
            int sz = z;
            int sy = groundY + straight;
            for (int step = 0; step < 2; step++) {
                sy++;
                if (alongX) {
                    sz += dirZ;
                } else {
                    sx += dirX;
                }
                scratch.set(sx, sy, sz, Blk.ACACIA_LOG);
            }
            disc(scratch, random, sx, sy + 1, sz, 2, Blk.ACACIA_LEAVES, true);
        }
    }

    private static void canopyTree(
            final ChunkScratch scratch,
            final Random random,
            final int x,
            final int groundY,
            final int z,
            final short log,
            final short leaves) {

        final int height = 6 + random.nextInt(4);
        final int top = groundY + height;

        for (int dz = 0; dz <= 1; dz++) {
            for (int dx = 0; dx <= 1; dx++) {
                trunk(scratch, x + dx, groundY + 1, top - 1, z + dz, log);
            }
        }

        wideDisc(scratch, random, x, top - 2, z, 3, leaves);
        wideDisc(scratch, random, x, top - 1, z, 3, leaves);
        wideDisc(scratch, random, x, top, z, 2, leaves);
        wideDisc(scratch, random, x, top + 1, z, 1, leaves);
    }

    private static void cherry(
            final ChunkScratch scratch, final Random random, final int x, final int groundY, final int z) {

        final int height = 5 + random.nextInt(4);
        final int top = groundY + height;
        trunk(scratch, x, groundY + 1, top, z, Blk.CHERRY_LOG);

        final int branches = 2 + random.nextInt(3);
        for (int i = 0; i < branches; i++) {
            final int dirX = random.nextInt(3) - 1;
            final int dirZ = dirX == 0 ? (random.nextBoolean() ? 1 : -1) : random.nextInt(3) - 1;
            int bx = x;
            int bz = z;
            int by = top - 1 - random.nextInt(2);
            final int length = 2 + random.nextInt(2);
            for (int step = 0; step < length; step++) {
                bx += dirX;
                bz += dirZ;
                by++;
                scratch.set(bx, by, bz, Blk.CHERRY_LOG);
            }
            blob(scratch, random, bx, by + 1, bz, 2, Blk.CHERRY_LEAVES);
        }
        blob(scratch, random, x, top + 1, z, 3, Blk.CHERRY_LEAVES);
    }

    private static void mangrove(
            final ChunkScratch scratch, final Random random, final int x, final int groundY, final int z) {

        final int rootHeight = 1 + random.nextInt(3);
        final int base = groundY + rootHeight;
        final int height = 5 + random.nextInt(4);
        final int top = base + height;

        for (int corner = 0; corner < 4; corner++) {
            final int dx = (corner & 1) == 0 ? 1 : -1;
            final int dz = (corner & 2) == 0 ? 1 : -1;
            for (int step = 0; step <= rootHeight; step++) {
                scratch.set(x + dx, base - step, z + dz, Blk.MANGROVE_LOG);
            }
        }

        trunk(scratch, x, groundY + 1, top, z, Blk.MANGROVE_LOG);
        disc(scratch, random, x, top - 1, z, 3, Blk.MANGROVE_LEAVES, true);
        disc(scratch, random, x, top, z, 2, Blk.MANGROVE_LEAVES, true);
        disc(scratch, random, x, top + 1, z, 1, Blk.MANGROVE_LEAVES, false);
        drapeVines(scratch, random, x, top, z, 3);
    }

    private static void hugeMushroom(
            final ChunkScratch scratch,
            final Random random,
            final int x,
            final int groundY,
            final int z,
            final boolean red) {

        final int height = 4 + random.nextInt(4);
        final int top = groundY + height;
        final short cap = red ? Blk.RED_MUSHROOM_BLOCK : Blk.BROWN_MUSHROOM_BLOCK;

        trunk(scratch, x, groundY + 1, top - 1, z, Blk.MUSHROOM_STEM);

        if (red) {
            for (int dz = -2; dz <= 2; dz++) {
                for (int dx = -2; dx <= 2; dx++) {
                    if (Math.abs(dx) == 2 && Math.abs(dz) == 2) {
                        continue;
                    }
                    scratch.setIfReplaceable(x + dx, top, z + dz, cap);
                }
            }
            for (int dz = -1; dz <= 1; dz++) {
                for (int dx = -1; dx <= 1; dx++) {
                    scratch.setIfReplaceable(x + dx, top + 1, z + dz, cap);
                }
            }
            for (int dz = -2; dz <= 2; dz++) {
                for (int dx = -2; dx <= 2; dx++) {
                    if (Math.abs(dx) == 2 ^ Math.abs(dz) == 2) {
                        scratch.setIfReplaceable(x + dx, top - 1, z + dz, cap);
                    }
                }
            }
        } else {
            for (int dz = -3; dz <= 3; dz++) {
                for (int dx = -3; dx <= 3; dx++) {
                    if (Math.abs(dx) + Math.abs(dz) > 4) {
                        continue;
                    }
                    scratch.setIfReplaceable(x + dx, top, z + dz, cap);
                }
            }
        }
    }

    private static void trunk(
            final ChunkScratch scratch, final int x, final int fromY, final int toY, final int z, final short log) {
        for (int y = fromY; y <= toY; y++) {
            scratch.set(x, y, z, log);
        }
    }

    private static void disc(
            final ChunkScratch scratch,
            final Random random,
            final int x,
            final int y,
            final int z,
            final int radius,
            final short leaves,
            final boolean trimCorners) {

        for (int dz = -radius; dz <= radius; dz++) {
            for (int dx = -radius; dx <= radius; dx++) {
                if (dx * dx + dz * dz > radius * radius + radius) {
                    continue;
                }
                if (trimCorners
                        && Math.abs(dx) == radius
                        && Math.abs(dz) == radius
                        && random.nextInt(2) == 0) {
                    continue;
                }
                scratch.setIfReplaceable(x + dx, y, z + dz, leaves);
            }
        }
    }

    private static void wideDisc(
            final ChunkScratch scratch,
            final Random random,
            final int x,
            final int y,
            final int z,
            final int radius,
            final short leaves) {

        for (int dz = -radius; dz <= radius + 1; dz++) {
            for (int dx = -radius; dx <= radius + 1; dx++) {
                final double cx = dx - 0.5;
                final double cz = dz - 0.5;
                if (cx * cx + cz * cz > (radius + 0.5) * (radius + 0.5)) {
                    continue;
                }
                if (radius > 1 && random.nextInt(12) == 0) {
                    continue;
                }
                scratch.setIfReplaceable(x + dx, y, z + dz, leaves);
            }
        }
    }

    private static void blob(
            final ChunkScratch scratch,
            final Random random,
            final int x,
            final int y,
            final int z,
            final int radius,
            final short leaves) {

        for (int dy = -radius; dy <= radius; dy++) {
            for (int dz = -radius; dz <= radius; dz++) {
                for (int dx = -radius; dx <= radius; dx++) {
                    final int distance = dx * dx + dy * dy * 2 + dz * dz;
                    if (distance > radius * radius + radius) {
                        continue;
                    }
                    if (distance > radius * radius && random.nextInt(3) == 0) {
                        continue;
                    }
                    scratch.setIfReplaceable(x + dx, y + dy, z + dz, leaves);
                }
            }
        }
    }

    private static void climbVines(
            final ChunkScratch scratch,
            final Random random,
            final int x,
            final int fromY,
            final int toY,
            final int z) {

        for (int y = fromY; y <= toY; y++) {
            if (random.nextInt(3) != 0) {
                continue;
            }
            final int side = random.nextInt(4);
            final int dx = side == 0 ? 1 : side == 1 ? -1 : 0;
            final int dz = side == 2 ? 1 : side == 3 ? -1 : 0;
            final short vine = switch (side) {
                case 0 -> Blk.VINE_WEST;
                case 1 -> Blk.VINE_EAST;
                case 2 -> Blk.VINE_NORTH;
                default -> Blk.VINE_SOUTH;
            };
            scratch.setIfReplaceable(x + dx, y, z + dz, vine);
        }
    }

    private static void drapeVines(
            final ChunkScratch scratch,
            final Random random,
            final int x,
            final int top,
            final int z,
            final int attempts) {

        for (int i = 0; i < attempts; i++) {
            final int dx = random.nextInt(5) - 2;
            final int dz = random.nextInt(5) - 2;
            final int length = 2 + random.nextInt(5);
            for (int step = 0; step < length; step++) {
                if (!scratch.setIfReplaceable(x + dx, top - 2 - step, z + dz, Blk.VINE_UP)) {
                    break;
                }
            }
        }
    }

    private static void replaceSoil(final ChunkScratch scratch, final int x, final int y, final int z,
                                    final short soil) {
        if (Blk.isSoil(scratch.get(x, y, z))) {
            scratch.set(x, y, z, soil);
        }
    }
}
