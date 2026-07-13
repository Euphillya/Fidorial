package fr.euphyllia.fidorial.server.protocol.packet.clientbound.play;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.protocol.catalog.PlayClientboundPackets;
import fr.euphyllia.fidorial.server.protocol.packet.ClientboundPacket;


public record ClientboundSetEntityDataPacket(int entityId, int displayedSkinParts)
        implements ClientboundPacket {

    private static final int PLAYER_SKIN_PARTS_INDEX = 16;
    private static final int METADATA_TYPE_BYTE = 0;
    private static final int METADATA_END = 0xFF;

    @Override
    public String name() {
        return PlayClientboundPackets.SET_ENTITY_DATA;
    }

    @Override
    public void write(PacketBuffer buf) {
        buf.writeVarInt(entityId);

        buf.writeByte(PLAYER_SKIN_PARTS_INDEX);
        buf.writeVarInt(METADATA_TYPE_BYTE);
        buf.writeByte(displayedSkinParts & 0xFF);

        buf.writeByte(METADATA_END);
    }
}
