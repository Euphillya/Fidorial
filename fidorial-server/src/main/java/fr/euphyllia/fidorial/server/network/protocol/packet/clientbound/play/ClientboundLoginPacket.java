package fr.euphyllia.fidorial.server.network.protocol.packet.clientbound.play;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.network.protocol.catalog.PlayClientboundPackets;
import fr.euphyllia.fidorial.server.network.protocol.packet.ClientboundPacket;
import net.kyori.adventure.key.Key;

// https://minecraft.wiki/w/Java_Edition_protocol/Packets#Login_(play)
public record ClientboundLoginPacket(
        int entityId, Key dimensionKey, int dimensionTypeId, int viewDistance, int gameMode)
        implements ClientboundPacket {

    @Override
    public String name() {
        return PlayClientboundPackets.LOGIN;
    }

    @Override
    public void write(PacketBuffer buf) {
        buf.writeInt(entityId);
        buf.writeBoolean(false); // hardcore
        buf.writeVarInt(1); // nombre de dimensions
        buf.writeKey(dimensionKey); // liste des dimensions
        buf.writeVarInt(0); // maxPlayers (obsolete)
        buf.writeVarInt(viewDistance);
        buf.writeVarInt(viewDistance); // simulationDistance
        buf.writeBoolean(false); // reducedDebugInfo
        buf.writeBoolean(true); // enableRespawnScreen
        buf.writeBoolean(false); // doLimitedCrafting
        buf.writeVarInt(dimensionTypeId);
        buf.writeKey(dimensionKey);
        buf.writeLong(0L); // hashedSeed
        buf.writeByte(gameMode); // gameMode (survie)
        buf.writeByte(-1); // previousGameMode
        buf.writeBoolean(false); // isDebug
        buf.writeBoolean(true); // isFlat
        buf.writeBoolean(false); // hasDeathLocation
        buf.writeVarInt(0); // portalCooldown
        buf.writeVarInt(63); // seaLevel
        buf.writeBoolean(false);
        buf.writeBoolean(false);
    }
}
