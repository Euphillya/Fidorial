package fr.euphyllia.fidorial.server.protocol.packet.clientbound.play;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.protocol.catalog.PlayClientboundPackets;
import fr.euphyllia.fidorial.server.protocol.packet.ClientboundPacket;
import fr.euphyllia.fidorial.server.world.nbt.NbtIo;
import fr.euphyllia.fidorial.server.world.nbt.NbtString;

public record ClientboundSystemChatPacket(String message, boolean overlay)
        implements ClientboundPacket {

    @Override
    public String name() {
        return PlayClientboundPackets.SYSTEM_CHAT;
    }

    @Override
    public void write(PacketBuffer buf) {
        buf.writeRawBytes(NbtIo.writeNetworkToBytes(new NbtString(message)));
        buf.writeBoolean(overlay);
    }
}
