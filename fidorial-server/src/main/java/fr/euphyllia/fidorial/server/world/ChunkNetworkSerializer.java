package fr.euphyllia.fidorial.server.world;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.world.block.blockentity.BlockEntity;
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

    public void writeChunk(final PacketBuffer p, final ByteBufAllocator alloc, final ChunkColumn chunk) {
        p.writeInt(chunk.chunkX());
        p.writeInt(chunk.chunkZ());
        p.writeVarInt(0); // heightmaps : 0 → le client recalcule

        final byte[] sections = buildSections(alloc, chunk);
        p.writeByteArray(sections);

        writeBlockEntities(p, chunk);

        writeLightData(p, chunk);
    }

    /**
     * Writes the {@code Block Entities} prefixed array of
     * {@code level_chunk_with_light}.
     *
     * <p>Each entry is a packed XZ byte, the absolute height as a short, the
     * {@code minecraft:block_entity_type} protocol ID as a VarInt, and the block
     * entity NBT stripped of its X, Y and Z values.</p>
     */
    private void writeBlockEntities(final PacketBuffer p, final ChunkColumn chunk) {

        final List<BlockEntity> encodable = new ArrayList<>();

        for (final BlockEntity blockEntity : chunk.blockEntities()) {
            if (blockEntity.isKnown()) {
                encodable.add(blockEntity);
            } else {
                LOGGER.warn("Skipping block entity with unknown type '{}' at {},{},{} in chunk {},{}.",
                        blockEntity.type(),
                        blockEntity.localX(),
                        blockEntity.y(),
                        blockEntity.localZ(),
                        chunk.chunkX(),
                        chunk.chunkZ());
            }
        }

        p.writeVarInt(encodable.size());

        for (final BlockEntity blockEntity : encodable) {
            p.writeByte(blockEntity.packedXz());
            p.writeShort(blockEntity.y());
            p.writeVarInt(blockEntity.protocolId());
            p.writeNbt(blockEntity.data());
            LOGGER.info("BlockEntity : " + blockEntity.type() + " protocolId : " + blockEntity.protocolId());
        }
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
        sp.writeShort(section.fluidCount());    // fluidCount

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
                sky = light.materializeSkySection(i - 1);
            }

            if (sky == null) {
                // neither mask
            } else if (isAllZero(sky)) {
                setBit(emptySkyMask, i);
            } else {
                setBit(skyMask, i);
                skyArrays.add(sky);
            }

            final byte[] block = (i == 0 || i == topIndex) ? null : light.sectionArray(LightType.BLOCK, i - 1);

            if (block == null) {
                // neither mask
            } else if (isAllZero(block)) {
                setBit(emptyBlockMask, i);
            } else {
                setBit(blockMask, i);
                blockArrays.add(block);
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

    private static boolean isAllZero(final byte[] a) {
        for (final byte b : a) if (b != 0) return false;
        return true;
    }

    private static byte[] fullLight() {
        final byte[] full = new byte[ChunkLightData.SECTION_BYTES];
        Arrays.fill(full, (byte) 0xFF);
        return full;
    }
}