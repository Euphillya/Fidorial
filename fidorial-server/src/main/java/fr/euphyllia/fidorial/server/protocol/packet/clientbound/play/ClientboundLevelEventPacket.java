package fr.euphyllia.fidorial.server.protocol.packet.clientbound.play;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.protocol.catalog.PlayClientboundPackets;
import fr.euphyllia.fidorial.server.protocol.packet.ClientboundPacket;
import fr.fidorial.world.BlockPos;

public record ClientboundLevelEventPacket(int event, BlockPos position, int data, boolean global)
        implements ClientboundPacket {

    public static final int BLOCK_BREAK = 2001;

    @Override
    public String name() {
        return PlayClientboundPackets.LEVEL_EVENT;
    }

    @Override
    public void write(PacketBuffer buf) {
        buf.writeInt(event);
        buf.writePosition(position.x(), position.y(), position.z());
        buf.writeInt(data);
        buf.writeBoolean(global);
    }
}
