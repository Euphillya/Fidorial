package fr.euphyllia.fidorial.server.protocol.packet.clientbound.play;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.protocol.catalog.PlayClientboundPackets;
import fr.euphyllia.fidorial.server.protocol.packet.ClientboundPacket;
import fr.fidorial.world.BlockPos;

public record ClientboundBlockUpdatePacket(BlockPos pos, int blockStateId) implements ClientboundPacket {

    @Override
    public String name() {
        return PlayClientboundPackets.BLOCK_UPDATE;
    }

    @Override
    public void write(PacketBuffer buf) {
        buf.writePosition(pos.x(), pos.y(), pos.z());
        buf.writeVarInt(blockStateId);
    }
}
