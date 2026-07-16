package fr.euphyllia.fidorial.server.protocol.packet.clientbound.play;

import fr.euphyllia.fidorial.server.chat.MiniText;
import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.protocol.catalog.PlayClientboundPackets;
import fr.euphyllia.fidorial.server.protocol.packet.ClientboundPacket;
import fr.euphyllia.fidorial.server.world.nbt.NbtIo;

public record ClientboundSystemChatPacket(String message, boolean overlay)
        implements ClientboundPacket {

    @Override
    public String name() {
        return PlayClientboundPackets.SYSTEM_CHAT;
    }

    @Override
    public void write(PacketBuffer buf) {
        buf.writeRawBytes(NbtIo.writeNetworkToBytes(MiniText.parse(message)));
        buf.writeBoolean(overlay);
    }
}
