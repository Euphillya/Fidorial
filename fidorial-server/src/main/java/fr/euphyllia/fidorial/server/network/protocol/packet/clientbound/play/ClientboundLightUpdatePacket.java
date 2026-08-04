package fr.euphyllia.fidorial.server.network.protocol.packet.clientbound.play;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.network.protocol.catalog.PlayClientboundPackets;
import fr.euphyllia.fidorial.server.network.protocol.packet.ClientboundPacket;
import fr.euphyllia.fidorial.server.world.ChunkNetworkSerializer;
import fr.euphyllia.fidorial.server.world.chunk.ChunkColumn;
import net.kyori.adventure.key.Key;

public record ClientboundLightUpdatePacket(ChunkNetworkSerializer serializer, ChunkColumn column)
        implements ClientboundPacket {

    @Override
    public Key name() {
        return PlayClientboundPackets.LIGHT_UPDATE;
    }

    @Override
    public void write(final PacketBuffer buf) {
        buf.writeVarInt(column.chunkX());
        buf.writeVarInt(column.chunkZ());
        serializer.writeLightData(buf, column);
    }
}
