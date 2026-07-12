package fr.euphyllia.fidorial.server.world;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.world.chunk.BlockState;
import fr.euphyllia.fidorial.server.world.chunk.ChunkColumn;
import fr.euphyllia.fidorial.server.world.chunk.ChunkSection;
import fr.euphyllia.fidorial.server.world.chunk.PalettedContainer;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

public final class ChunkNetworkSerializer {

    private static final Logger LOGGER = LoggerFactory.getLogger(ChunkNetworkSerializer.class);

    private final BlockStateRegistry blockRegistry;
    private final int biomeNetworkId;

    public ChunkNetworkSerializer(BlockStateRegistry blockRegistry, int biomeNetworkId) {
        this.blockRegistry = blockRegistry;
        this.biomeNetworkId = biomeNetworkId;
    }

    private static long[] bits(int count) {
        long[] words = new long[(count + 63) / 64];
        for (int i = 0; i < count; i++) words[i / 64] |= 1L << (i % 64);
        return words;
    }

    public void writeChunk(PacketBuffer p, ByteBufAllocator alloc, ChunkColumn chunk) {
        p.writeInt(chunk.chunkX());
        p.writeInt(chunk.chunkZ());
        p.writeVarInt(0); // heightmaps : 0 → le client recalcule

        if (chunk.chunkX() == 0 && chunk.chunkZ() == 0) {
            int idx = 0;
            for (ChunkSection s : chunk.sections()) {
                var pal = s.blocks().palette();
                LOGGER.info("sec {} nonAir={} single={} palette={} -> ids={}",
                        idx++, s.nonAirCount(), s.blocks().isSingleValue(),
                        pal, pal.stream().map(blockRegistry::networkId).toList());
            }
        }

        byte[] sections = buildSections(alloc, chunk);
        p.writeByteArray(sections);

        p.writeVarInt(0); // block entities

        writeLight(p, chunk.sectionCount());
    }

    private byte[] buildSections(ByteBufAllocator alloc, ChunkColumn chunk) {
        ByteBuf sec = alloc.buffer();
        try {
            PacketBuffer sp = new PacketBuffer(sec);
            for (ChunkSection section : chunk.sections()) {
                writeSection(sp, section);
            }
            byte[] out = new byte[sec.readableBytes()];
            sec.readBytes(out);
            return out;
        } finally {
            sec.release();
        }
    }

    private void writeSection(PacketBuffer sp, ChunkSection section) {
        PalettedContainer<BlockState> blocks = section.blocks();

        sp.writeShort(section.nonAirCount());   // nonEmptyBlockCount
        sp.writeShort(0);                        // fluidCount (0 pour plat)

        if (blocks.isSingleValue()) {
            int stateId = blockRegistry.networkId(blocks.palette().getFirst());
            sp.writeByte(0);           // bitsPerEntry = 0 (single value)
            sp.writeVarInt(stateId);   // valeur unique, pas de tableau de longs

            sp.writeByte(0);           // biomes single value
            sp.writeVarInt(biomeNetworkId);
        } else {
            writeIndirectSection(sp, blocks);
        }
    }

    private void writeIndirectSection(PacketBuffer sp, PalettedContainer<BlockState> blocks) {
        int bits = Math.max(4, blocks.bitsPerEntry());
        sp.writeByte(bits);
        sp.writeVarInt(blocks.palette().size());
        for (BlockState state : blocks.palette()) {
            sp.writeVarInt(blockRegistry.networkId(state));
        }

        int entriesPerLong = 64 / bits;
        int expectedLongs = (4096 + entriesPerLong - 1) / entriesPerLong;
        long[] data = blocks.packedData();
        for (int i = 0; i < expectedLongs; i++) {
            sp.writeLong(data != null && i < data.length ? data[i] : 0L);
        }

        // biomes single value
        sp.writeByte(0);
        sp.writeVarInt(biomeNetworkId);
    }

    private void writeLight(PacketBuffer p, int sectionCount) {
        int lightSections = sectionCount + 2;
        long[] allSet = bits(lightSections);

        p.writeBitSet(allSet);
        p.writeBitSet(new long[0]);
        p.writeBitSet(new long[0]);
        p.writeBitSet(allSet);

        byte[] full = new byte[2048];
        Arrays.fill(full, (byte) 0xFF);
        p.writeVarInt(lightSections);
        for (int i = 0; i < lightSections; i++) {
            p.writeVarInt(2048);
            p.writeRawBytes(full);
        }
        p.writeVarInt(0);
    }
}