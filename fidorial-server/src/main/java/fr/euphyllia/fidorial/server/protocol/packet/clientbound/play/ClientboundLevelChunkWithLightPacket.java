package fr.euphyllia.fidorial.server.protocol.packet.clientbound.play;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.protocol.catalog.PlayClientboundPackets;
import fr.euphyllia.fidorial.server.protocol.packet.ClientboundPacket;
import fr.euphyllia.fidorial.server.world.ChunkNetworkSerializer;
import fr.euphyllia.fidorial.server.world.chunk.ChunkColumn;

public record ClientboundLevelChunkWithLightPacket(ChunkNetworkSerializer serializer,
                                                   ChunkColumn column) implements ClientboundPacket {

    @Override
    public String name() {
        return PlayClientboundPackets.LEVEL_CHUNK_WITH_LIGHT;
    }

    @Override
    public void write(PacketBuffer buf) {
        serializer.writeChunk(buf, buf.nettyBuf().alloc(), column);
    }
}
