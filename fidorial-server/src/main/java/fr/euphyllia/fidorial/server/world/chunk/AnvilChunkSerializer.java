package fr.euphyllia.fidorial.server.world.chunk;

import fr.euphyllia.fidorial.server.world.nbt.Nbt;
import fr.euphyllia.fidorial.server.world.nbt.NbtCompound;
import fr.euphyllia.fidorial.server.world.nbt.NbtList;
import fr.euphyllia.fidorial.server.world.nbt.NbtString;
import fr.euphyllia.fidorial.server.world.nbt.NbtType;
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

    public AnvilChunkSerializer(final int dataVersion) {
        this.dataVersion = dataVersion;
    }

    public NbtCompound toNbt(final ChunkColumn chunk) {
        final NbtCompound root = new NbtCompound();
        root.putInt("DataVersion", dataVersion);
        root.putInt("xPos", chunk.chunkX());
        root.putInt("zPos", chunk.chunkZ());
        root.putInt("yPos", chunk.minSectionY());
        root.putString("Status", chunk.status());
        root.putLong("LastUpdate", chunk.lastUpdate());
        root.putLong("InhabitedTime", chunk.inhabitedTime());

        root.putBoolean("isLightOn", false);

        final NbtList sections = new NbtList(NbtType.COMPOUND);
        for (final ChunkSection section : chunk.sections()) {
            sections.add(sectionToNbt(section));
        }
        root.put("sections", sections);

        final NbtCompound heightmaps = new NbtCompound();
        heightmaps.putLongArray("MOTION_BLOCKING", chunk.computeHeightmap(bs -> !bs.isAir()));
        heightmaps.putLongArray("WORLD_SURFACE", chunk.computeHeightmap(bs -> !bs.isAir()));
        root.put("Heightmaps", heightmaps);

        root.put("block_entities", new NbtList(NbtType.COMPOUND));
        root.put("block_ticks", new NbtList(NbtType.COMPOUND));
        root.put("fluid_ticks", new NbtList(NbtType.COMPOUND));
        return root;
    }

    private NbtCompound sectionToNbt(final ChunkSection section) {
        final NbtCompound c = new NbtCompound();
        c.putByte("Y", section.sectionY());

        // block_states
        final NbtCompound blockStates = new NbtCompound();
        final NbtList blockPalette = new NbtList(NbtType.COMPOUND);
        for (final BlockState state : section.blocks().palette()) {
            blockPalette.add(blockStateToNbt(state));
        }
        blockStates.put("palette", blockPalette);
        final long[] blockData = section.blocks().packedData();
        if (blockData != null) {
            blockStates.putLongArray("tool/data", blockData);
        }
        c.put("block_states", blockStates);

        // biomes
        final NbtCompound biomes = new NbtCompound();
        final NbtList biomePalette = new NbtList(NbtType.STRING);
        for (final String biome : section.biomes().palette()) {
            biomePalette.addString(biome);
        }
        biomes.put("palette", biomePalette);
        final long[] biomeData = section.biomes().packedData();
        if (biomeData != null) {
            biomes.putLongArray("tool/data", biomeData);
        }
        c.put("biomes", biomes);

        return c;
    }

    private NbtCompound blockStateToNbt(final BlockState state) {
        final NbtCompound c = new NbtCompound();
        c.putString("Name", state.name());
        if (!state.properties().isEmpty()) {
            final NbtCompound props = new NbtCompound();
            for (final var e : state.properties().entrySet()) {
                props.putString(e.getKey(), e.getValue());
            }
            c.put("Properties", props);
        }
        return c;
    }

    public ChunkColumn fromNbt(final NbtCompound root, final int minY, final int height, final BlockState defaultBlock, final String defaultBiome) {
        final int chunkX = root.getInt("xPos");
        final int chunkZ = root.getInt("zPos");

        final ChunkColumn chunk = new ChunkColumn(chunkX, chunkZ, minY, height, defaultBlock, defaultBiome);
        chunk.setStatus(root.contains("Status") ? root.getString("Status") : "minecraft:full");
        chunk.setInhabitedTime(root.getLong("InhabitedTime"));
        chunk.setLastUpdate(root.getLong("LastUpdate"));

        final NbtList sections = root.getList("sections");
        if (sections != null) {
            for (final Nbt tag : sections.items()) {
                if (tag instanceof final NbtCompound sc) {
                    final ChunkSection section = sectionFromNbt(sc, defaultBlock, defaultBiome);
                    if (section != null) chunk.putSection(section);
                }
            }
        }
        return chunk;
    }

    private @Nullable ChunkSection sectionFromNbt(final NbtCompound c, final BlockState defaultBlock, final String defaultBiome) {
        if (!c.contains("Y")) return null;
        final int sectionY = c.getByte("Y");

        // block_states
        final List<BlockState> blockPalette = new ArrayList<>();
        long[] blockData = new long[0];
        final NbtCompound bs = c.getCompound("block_states");
        if (bs != null) {
            final NbtList pal = bs.getList("palette");
            if (pal != null) {
                for (final Nbt t : pal.items()) {
                    if (t instanceof final NbtCompound entry) {
                        blockPalette.add(blockStateFromNbt(entry));
                    }
                }
            }
            blockData = bs.getLongArray("tool/data");
        }
        if (blockPalette.isEmpty()) blockPalette.add(defaultBlock);
        final PalettedContainer<BlockState> blocks =
                PalettedContainer.fromNbt(ChunkSection.BLOCK_COUNT, 4, blockPalette, blockData);

        // biomes
        final List<String> biomePalette = new ArrayList<>();
        long[] biomeData = new long[0];
        final NbtCompound bio = c.getCompound("biomes");
        if (bio != null) {
            final NbtList pal = bio.getList("palette");
            if (pal != null) {
                for (final Nbt t : pal.items()) {
                    if (t instanceof NbtString(final String value)) biomePalette.add(value);
                }
            }
            biomeData = bio.getLongArray("tool/data");
        }
        if (biomePalette.isEmpty()) biomePalette.add(defaultBiome);
        final PalettedContainer<String> biomes =
                PalettedContainer.fromNbt(ChunkSection.BIOME_COUNT, 1, biomePalette, biomeData);

        return new ChunkSection(sectionY, blocks, biomes);
    }

    private BlockState blockStateFromNbt(final NbtCompound c) {
        final String name = c.getString("Name");
        final NbtCompound props = c.getCompound("Properties");
        if (props == null || props.tags().isEmpty()) {
            return BlockState.of(name);
        }
        final Map<String, String> map = new TreeMap<>();
        for (final var e : props.tags().entrySet()) {
            if (e.getValue() instanceof NbtString(final String value)) {
                map.put(e.getKey(), value);
            }
        }
        return new BlockState(name, map);
    }
}
