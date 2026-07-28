package fr.euphyllia.fidorial.server.world;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.world.chunk.BlockState;
import fr.euphyllia.fidorial.server.world.chunk.ChunkColumn;
import fr.euphyllia.fidorial.server.world.chunk.ChunkSection;
import fr.euphyllia.fidorial.server.world.chunk.PalettedContainer;
import fr.euphyllia.fidorial.server.world.light.ChunkLightData;
import fr.fidorial.world.light.LightType;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import net.kyori.adventure.text.logger.slf4j.ComponentLogger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class ChunkNetworkSerializer {

    private static final ComponentLogger LOGGER = ComponentLogger.logger(ChunkNetworkSerializer.class);

    private final BlockStateRegistry blockRegistry;
    private final int biomeNetworkId;
    private static final byte[] FULL_LIGHT = fullLight();

    public ChunkNetworkSerializer(final BlockStateRegistry blockRegistry, final int biomeNetworkId) {
        this.blockRegistry = blockRegistry;
        this.biomeNetworkId = biomeNetworkId;
    }

    private static long[] bits(final int count) {
        final long[] words = new long[(count + 63) / 64];
        for (int i = 0; i < count; i++) words[i / 64] |= 1L << (i % 64);
        return words;
    }

    public void writeChunk(final PacketBuffer p, final ByteBufAllocator alloc, final ChunkColumn chunk) {
        p.writeInt(chunk.chunkX());
        p.writeInt(chunk.chunkZ());
        p.writeVarInt(0); // heightmaps : 0 → le client recalcule

        final byte[] sections = buildSections(alloc, chunk);
        p.writeByteArray(sections);

        p.writeVarInt(0); // block entities

        writeLightData(p, chunk);
    }

    private byte[] buildSections(final ByteBufAllocator alloc, final ChunkColumn chunk) {
        final ByteBuf sec = alloc.buffer();
        try {
            final PacketBuffer sp = new PacketBuffer(sec);
            for (final ChunkSection section : chunk.sections()) {
                writeSection(sp, section);
            }
            final byte[] out = new byte[sec.readableBytes()];
            sec.readBytes(out);
            return out;
        } finally {
            sec.release();
        }
    }

    private void writeSection(final PacketBuffer sp, final ChunkSection section) {
        final PalettedContainer<BlockState> blocks = section.blocks();

        sp.writeShort(section.nonAirCount());   // nonEmptyBlockCount
        sp.writeShort(0);                        // fluidCount (0 pour plat)

        if (blocks.isSingleValue()) {
            final int stateId = blockRegistry.networkId(blocks.palette().getFirst());
            sp.writeByte(0);           // bitsPerEntry = 0 (single value)
            sp.writeVarInt(stateId);   // valeur unique, pas de tableau de longs

            sp.writeByte(0);           // biomes single value
            sp.writeVarInt(biomeNetworkId);
        } else {
            writeIndirectSection(sp, blocks);
        }
    }

    private void writeIndirectSection(final PacketBuffer sp, final PalettedContainer<BlockState> blocks) {
        final int bits = Math.max(4, blocks.bitsPerEntry());
        sp.writeByte(bits);
        sp.writeVarInt(blocks.palette().size());
        for (final BlockState state : blocks.palette()) {
            sp.writeVarInt(blockRegistry.networkId(state));
        }

        final int entriesPerLong = 64 / bits;
        final int expectedLongs = (4096 + entriesPerLong - 1) / entriesPerLong;
        final long[] data = blocks.packedData();
        for (int i = 0; i < expectedLongs; i++) {
            sp.writeLong(data != null && i < data.length ? data[i] : 0L);
        }

        // biomes single value
        sp.writeByte(0);
        sp.writeVarInt(biomeNetworkId);
    }

    private void writeLight(final PacketBuffer p, final int sectionCount) {
        final int lightSections = sectionCount + 2;
        final long[] allSet = bits(lightSections);

        p.writeBitSet(allSet);
        p.writeBitSet(new long[0]);
        p.writeBitSet(new long[0]);
        p.writeBitSet(allSet);

        final byte[] full = new byte[2048];
        Arrays.fill(full, (byte) 0xFF);
        p.writeVarInt(lightSections);
        for (int i = 0; i < lightSections; i++) {
            p.writeVarInt(2048);
            p.writeRawBytes(full);
        }
        p.writeVarInt(0);
    }

    public void writeLightData(final PacketBuffer p, final ChunkColumn chunk) {
        final int worldSections = chunk.sectionCount();
        final int lightSections = worldSections + 2;
        final int topIndex = lightSections - 1;

        final ChunkLightData light = chunk.lightData();

        final long[] skyMask = new long[(lightSections + 63) >> 6];
        final long[] blockMask = new long[skyMask.length];
        final long[] emptySkyMask = new long[skyMask.length];
        final long[] emptyBlockMask = new long[skyMask.length];

        final List<byte[]> skyArrays = new ArrayList<>();
        final List<byte[]> blockArrays = new ArrayList<>();

        for (int i = 0; i < lightSections; i++) {
            final byte[] sky;
            if (i == topIndex) {
                sky = FULL_LIGHT;
            } else if (i == 0) {
                sky = null;
            } else {
                sky = light.sectionArray(LightType.SKY, i - 1);
            }
            if (sky != null) {
                setBit(skyMask, i);
                skyArrays.add(sky);
            } else {
                setBit(emptySkyMask, i);
            }

            final byte[] block = (i == 0 || i == topIndex) ? null : light.sectionArray(LightType.BLOCK, i - 1);
            if (block != null) {
                setBit(blockMask, i);
                blockArrays.add(block);
            } else {
                setBit(emptyBlockMask, i);
            }
        }

        p.writeBitSet(skyMask);
        p.writeBitSet(blockMask);
        p.writeBitSet(emptySkyMask);
        p.writeBitSet(emptyBlockMask);

        p.writeVarInt(skyArrays.size());
        for (final byte[] array : skyArrays) {
            p.writeVarInt(ChunkLightData.SECTION_BYTES);
            p.writeRawBytes(array);
        }

        p.writeVarInt(blockArrays.size());
        for (final byte[] array : blockArrays) {
            p.writeVarInt(ChunkLightData.SECTION_BYTES);
            p.writeRawBytes(array);
        }
    }

    private static void setBit(final long[] words, final int bit) {
        words[bit >> 6] |= 1L << (bit & 63);
    }

    private static byte[] fullLight() {
        final byte[] full = new byte[ChunkLightData.SECTION_BYTES];
        Arrays.fill(full, (byte) 0xFF);
        return full;
    }
}