package fr.euphyllia.fidorial.server.protocol.packet.clientbound.play;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.protocol.catalog.PlayClientboundPackets;
import fr.euphyllia.fidorial.server.protocol.packet.ClientboundPacket;


public record ClientboundInitializeBorderPacket(double centerX, double centerZ, double oldDiameter, double newDiameter,
                                                Object speed, int portalTeleportBoundary, int warningBlocks,
                                                int warningTime) implements ClientboundPacket {

    @Override
    public String name() {
        return PlayClientboundPackets.INITIALIZE_BORDER;
    }

    @Override
    public void write(PacketBuffer buf) {
        buf.writeDouble(centerX);
        buf.writeDouble(centerZ);
        buf.writeDouble(oldDiameter);
        buf.writeDouble(newDiameter);
        // TODO: write speed (VarLong)
        buf.writeVarInt(portalTeleportBoundary);
        buf.writeVarInt(warningBlocks);
        buf.writeVarInt(warningTime);
    }
}
