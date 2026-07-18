package fr.euphyllia.fidorial.server.world;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;

import java.util.Arrays;

public final class FlatWorld {

    public static final int MIN_Y = -64;
    public static final int HEIGHT = 384;
    public static final int SECTION_COUNT = HEIGHT / 16;
    public static final int COBBLESTONE_STATE_ID = 14;
    public static final int AIR_STATE_ID = 0;

    public static final double SPAWN_X = 8.5;
    public static final double SPAWN_Y = -48.0;
    public static final double SPAWN_Z = 8.5;

    private FlatWorld() {
    }

    public static void writeChunk(PacketBuffer p, ByteBufAllocator alloc,
                                  int chunkX, int chunkZ, int biomeId) {
        p.writeInt(chunkX);
        p.writeInt(chunkZ);
        p.writeVarInt(0);

        byte[] sections = buildSections(alloc, biomeId);
        p.writeByteArray(sections);

        p.writeVarInt(0);

        writeLight(p);
    }

    private static byte[] buildSections(ByteBufAllocator alloc, int biomeId) {
        ByteBuf sec = alloc.buffer();
        try {
            PacketBuffer sp = new PacketBuffer(sec);
            for (int i = 0; i < SECTION_COUNT; i++) {
                boolean solid = i == 0;
                sp.writeShort(solid ? 4096 : 0);
                sp.writeShort(0);
                sp.writeByte(0);
                sp.writeVarInt(solid ? COBBLESTONE_STATE_ID : AIR_STATE_ID);
                sp.writeByte(0);
                sp.writeVarInt(biomeId);
            }
            byte[] out = new byte[sec.readableBytes()];
            sec.readBytes(out);
            return out;
        } finally {
            sec.release();
        }
    }

    private static void writeLight(PacketBuffer p) {
        int lightSections = SECTION_COUNT + 2;
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

    private static long[] bits(int count) {
        long[] words = new long[(count + 63) / 64];
        for (int i = 0; i < count; i++) words[i / 64] |= 1L << (i % 64);
        return words;
    }
}
