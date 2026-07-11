package fr.euphyllia.fidorial.server.protocol.packet.clientbound.play;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.protocol.catalog.PlayClientboundPackets;
import fr.euphyllia.fidorial.server.protocol.packet.ClientboundPacket;

public record ClientboundSetChunkCacheCenterPacket(int chunkX, int chunkZ) implements ClientboundPacket {

    @Override
    public String name() {
        return PlayClientboundPackets.SET_CHUNK_CACHE_CENTER;
    }

    @Override
    public void write(PacketBuffer buf) {
        buf.writeVarInt(chunkX).writeVarInt(chunkZ);
    }
}
