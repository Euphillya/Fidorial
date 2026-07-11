package fr.euphyllia.fidorial.server.protocol.packet.clientbound.play;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.protocol.catalog.PlayClientboundPackets;
import fr.euphyllia.fidorial.server.protocol.packet.ClientboundPacket;

public record ClientboundLoginPacket(int entityId, String dimensionName, int dimensionTypeId,
                                     int viewDistance) implements ClientboundPacket {

    @Override
    public String name() {
        return PlayClientboundPackets.LOGIN;
    }

    @Override
    public void write(PacketBuffer buf) {
        buf.writeInt(entityId);
        buf.writeBoolean(false);              // hardcore
        buf.writeVarInt(1);                   // nombre de dimensions
        buf.writeIdentifier(dimensionName);   // liste des dimensions
        buf.writeVarInt(0);                   // maxPlayers (obsolete)
        buf.writeVarInt(viewDistance);
        buf.writeVarInt(viewDistance);        // simulationDistance
        buf.writeBoolean(false);              // reducedDebugInfo
        buf.writeBoolean(true);               // enableRespawnScreen
        buf.writeBoolean(false);              // doLimitedCrafting
        buf.writeVarInt(dimensionTypeId);
        buf.writeIdentifier(dimensionName);
        buf.writeLong(0L);                    // hashedSeed
        buf.writeByte(1);                     // gameMode (survie)
        buf.writeByte(-1);                    // previousGameMode
        buf.writeBoolean(false);              // isDebug
        buf.writeBoolean(true);               // isFlat
        buf.writeBoolean(false);              // hasDeathLocation
        buf.writeVarInt(0);                   // portalCooldown
        buf.writeVarInt(63);                  // seaLevel
        buf.writeBoolean(false);
        buf.writeBoolean(false);
    }
}
