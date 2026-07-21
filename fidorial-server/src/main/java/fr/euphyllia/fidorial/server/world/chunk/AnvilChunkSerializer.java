package fr.euphyllia.fidorial.server.world.chunk;

import fr.euphyllia.fidorial.server.world.nbt.*;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class AnvilChunkSerializer {

    public static final int DATA_VERSION_26_2 = 4903;

    private final int dataVersion;

    public AnvilChunkSerializer() {
        this(DATA_VERSION_26_2);
    }

    public AnvilChunkSerializer(int dataVersion) {
        this.dataVersion = dataVersion;
    }

    public NbtCompound toNbt(ChunkColumn chunk) {
        NbtCompound root = new NbtCompound();
        root.putInt("DataVersion", dataVersion);
        root.putInt("xPos", chunk.chunkX());
        root.putInt("zPos", chunk.chunkZ());
        root.putInt("yPos", chunk.minSectionY());
        root.putString("Status", chunk.status());
        root.putLong("LastUpdate", chunk.lastUpdate());
        root.putLong("InhabitedTime", chunk.inhabitedTime());

        root.putBoolean("isLightOn", false);

        NbtList sections = new NbtList(NbtType.COMPOUND);
        for (ChunkSection section : chunk.sections()) {
            sections.add(sectionToNbt(section));
        }
        root.put("sections", sections);

        NbtCompound heightmaps = new NbtCompound();
        heightmaps.putLongArray("MOTION_BLOCKING", chunk.computeHeightmap(bs -> !bs.isAir()));
        heightmaps.putLongArray("WORLD_SURFACE", chunk.computeHeightmap(bs -> !bs.isAir()));
        root.put("Heightmaps", heightmaps);

        root.put("block_entities", new NbtList(NbtType.COMPOUND));
        root.put("block_ticks", new NbtList(NbtType.COMPOUND));
        root.put("fluid_ticks", new NbtList(NbtType.COMPOUND));
        return root;
    }

    private NbtCompound sectionToNbt(ChunkSection section) {
        NbtCompound c = new NbtCompound();
        c.putByte("Y", section.sectionY());

        // block_states
        NbtCompound blockStates = new NbtCompound();
        NbtList blockPalette = new NbtList(NbtType.COMPOUND);
        for (BlockState state : section.blocks().palette()) {
            blockPalette.add(blockStateToNbt(state));
        }
        blockStates.put("palette", blockPalette);
        long[] blockData = section.blocks().packedData();
        if (blockData != null) {
            blockStates.putLongArray("tool/data", blockData);
        }
        c.put("block_states", blockStates);

        // biomes
        NbtCompound biomes = new NbtCompound();
        NbtList biomePalette = new NbtList(NbtType.STRING);
        for (String biome : section.biomes().palette()) {
            biomePalette.addString(biome);
        }
        biomes.put("palette", biomePalette);
        long[] biomeData = section.biomes().packedData();
        if (biomeData != null) {
            biomes.putLongArray("tool/data", biomeData);
        }
        c.put("biomes", biomes);

        return c;
    }

    private NbtCompound blockStateToNbt(BlockState state) {
        NbtCompound c = new NbtCompound();
        c.putString("Name", state.name());
        if (!state.properties().isEmpty()) {
            NbtCompound props = new NbtCompound();
            for (var e : state.properties().entrySet()) {
                props.putString(e.getKey(), e.getValue());
            }
            c.put("Properties", props);
        }
        return c;
    }

    public ChunkColumn fromNbt(NbtCompound root, int minY, int height, BlockState defaultBlock, String defaultBiome) {
        int chunkX = root.getInt("xPos");
        int chunkZ = root.getInt("zPos");

        ChunkColumn chunk = new ChunkColumn(chunkX, chunkZ, minY, height, defaultBlock, defaultBiome);
        chunk.setStatus(root.contains("Status") ? root.getString("Status") : "minecraft:full");
        chunk.setInhabitedTime(root.getLong("InhabitedTime"));
        chunk.setLastUpdate(root.getLong("LastUpdate"));

        NbtList sections = root.getList("sections");
        if (sections != null) {
            for (Nbt tag : sections.items()) {
                if (tag instanceof NbtCompound sc) {
                    ChunkSection section = sectionFromNbt(sc, defaultBlock, defaultBiome);
                    if (section != null) chunk.putSection(section);
                }
            }
        }
        return chunk;
    }

    private @Nullable ChunkSection sectionFromNbt(NbtCompound c, BlockState defaultBlock, String defaultBiome) {
        if (!c.contains("Y")) return null;
        int sectionY = c.getByte("Y");

        // block_states
        List<BlockState> blockPalette = new ArrayList<>();
        long[] blockData = new long[0];
        NbtCompound bs = c.getCompound("block_states");
        if (bs != null) {
            NbtList pal = bs.getList("palette");
            if (pal != null) {
                for (Nbt t : pal.items()) {
                    if (t instanceof NbtCompound entry) {
                        blockPalette.add(blockStateFromNbt(entry));
                    }
                }
            }
            blockData = bs.getLongArray("tool/data");
        }
        if (blockPalette.isEmpty()) blockPalette.add(defaultBlock);
        PalettedContainer<BlockState> blocks =
                PalettedContainer.fromNbt(ChunkSection.BLOCK_COUNT, 4, blockPalette, blockData);

        // biomes
        List<String> biomePalette = new ArrayList<>();
        long[] biomeData = new long[0];
        NbtCompound bio = c.getCompound("biomes");
        if (bio != null) {
            NbtList pal = bio.getList("palette");
            if (pal != null) {
                for (Nbt t : pal.items()) {
                    if (t instanceof NbtString(String value)) biomePalette.add(value);
                }
            }
            biomeData = bio.getLongArray("tool/data");
        }
        if (biomePalette.isEmpty()) biomePalette.add(defaultBiome);
        PalettedContainer<String> biomes =
                PalettedContainer.fromNbt(ChunkSection.BIOME_COUNT, 1, biomePalette, biomeData);

        return new ChunkSection(sectionY, blocks, biomes);
    }

    private BlockState blockStateFromNbt(NbtCompound c) {
        String name = c.getString("Name");
        NbtCompound props = c.getCompound("Properties");
        if (props == null || props.tags().isEmpty()) {
            return BlockState.of(name);
        }
        Map<String, String> map = new TreeMap<>();
        for (var e : props.tags().entrySet()) {
            if (e.getValue() instanceof NbtString(String value)) {
                map.put(e.getKey(), value);
            }
        }
        return new BlockState(name, map);
    }
}
