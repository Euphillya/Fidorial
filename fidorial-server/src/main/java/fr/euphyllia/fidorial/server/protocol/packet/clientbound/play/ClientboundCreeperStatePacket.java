package fr.euphyllia.fidorial.server.protocol.packet.clientbound.play;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.protocol.catalog.PlayClientboundPackets;
import fr.euphyllia.fidorial.server.protocol.packet.ClientboundPacket;

public record ClientboundCreeperStatePacket(int entityId, boolean primed) implements ClientboundPacket {

    private static final int CREEPER_STATE_INDEX = 16;
    private static final int METADATA_TYPE_VARINT = 1;
    private static final int METADATA_END = 0xFF;

    @Override
    public String name() {
        return PlayClientboundPackets.SET_ENTITY_DATA;
    }

    @Override
    public void write(PacketBuffer buf) {
        buf.writeVarInt(entityId);
        buf.writeByte(CREEPER_STATE_INDEX);
        buf.writeVarInt(METADATA_TYPE_VARINT);
        buf.writeVarInt(primed ? 1 : -1);
        buf.writeByte(METADATA_END);
    }
}
