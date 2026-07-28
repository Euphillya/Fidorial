package fr.euphyllia.fidorial.server.world.chunk;

public final class ChunkSection {

    public static final int BLOCK_COUNT = 4096; // 16^3
    public static final int BIOME_COUNT = 64;   // 4^3

    private final int sectionY; // indice de section (ex. -4 pour y=-64)
    private final PalettedContainer<BlockState> blocks;
    private final PalettedContainer<String> biomes;
    private int nonAirCount;

    public ChunkSection(final int sectionY, final BlockState fillBlock, final String fillBiome) {
        this.sectionY = sectionY;
        this.blocks = new PalettedContainer<>(BLOCK_COUNT, 4, fillBlock);
        this.biomes = new PalettedContainer<>(BIOME_COUNT, 1, fillBiome);
        this.nonAirCount = fillBlock.isAir() ? 0 : BLOCK_COUNT;
    }

    public ChunkSection(final int sectionY, final PalettedContainer<BlockState> blocks,
                        final PalettedContainer<String> biomes) {
        this.sectionY = sectionY;
        this.blocks = blocks;
        this.biomes = biomes;
        recomputeNonAir();
    }

    private static int blockIndex(final int x, final int y, final int z) {
        return (y << 8) | (z << 4) | x;
    }

    public int sectionY() {
        return sectionY;
    }

    public PalettedContainer<BlockState> blocks() {
        return blocks;
    }

    public PalettedContainer<String> biomes() {
        return biomes;
    }

    public int nonAirCount() {
        return nonAirCount;
    }

    public boolean isEmpty() {
        return nonAirCount == 0;
    }

    public void setBlock(final int x, final int y, final int z, final BlockState state) {
        final int i = blockIndex(x, y, z);
        final boolean wasAir = blocks.get(i).isAir();
        final boolean isAir = state.isAir();
        if (wasAir && !isAir) nonAirCount++;
        else if (!wasAir && isAir) nonAirCount--;
        blocks.set(i, state);
    }

    public BlockState getBlock(final int x, final int y, final int z) {
        return blocks.get(blockIndex(x, y, z));
    }

    public void setBiome(final int bx, final int by, final int bz, final String biome) {
        biomes.set((by << 4) | (bz << 2) | bx, biome);
    }

    /**
     * Recalcule le compteur de blocs pleins (après reconstruction depuis le NBT).
     */
    public void recomputeNonAir() {
        int c = 0;
        for (int i = 0; i < BLOCK_COUNT; i++) {
            if (!blocks.get(i).isAir()) c++;
        }
        this.nonAirCount = c;
    }
}
