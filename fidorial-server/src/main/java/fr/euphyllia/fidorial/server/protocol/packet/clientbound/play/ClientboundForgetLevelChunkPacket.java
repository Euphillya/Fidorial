package fr.euphyllia.fidorial.server.protocol.packet.clientbound.play;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.protocol.catalog.PlayClientboundPackets;
import fr.euphyllia.fidorial.server.protocol.packet.ClientboundPacket;

public record ClientboundForgetLevelChunkPacket(int chunkX, int chunkZ) implements ClientboundPacket {

    @Override
    public String name() {
        return PlayClientboundPackets.FORGET_LEVEL_CHUNK;
    }

    @Override
    public void write(PacketBuffer buf) {
        buf.writeLong(((long) chunkZ << 32) | (chunkX & 0xFFFFFFFFL));
    }
}
