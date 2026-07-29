package fr.euphyllia.fidorial.server.network.protocol.packet.clientbound.play;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.network.protocol.catalog.PlayClientboundPackets;
import fr.euphyllia.fidorial.server.network.protocol.packet.ClientboundPacket;

public record ClientboundSetHealthPacket(float health, int food, float saturation)
        implements ClientboundPacket {

    @Override
    public String name() {
        return PlayClientboundPackets.SET_HEALTH;
    }

    @Override
    public void write(PacketBuffer buf) {
        buf.writeFloat(health);
        buf.writeVarInt(food);
        buf.writeFloat(saturation);
    }
}
