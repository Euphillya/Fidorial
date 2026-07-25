package fr.euphyllia.fidorial.server.protocol.packet.clientbound.play;

import com.mojang.brigadier.Message;
import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.protocol.catalog.PlayClientboundPackets;
import fr.euphyllia.fidorial.server.protocol.packet.ClientboundPacket;

import java.util.List;

public record ClientboundCommandSuggestionsPacket(int id, int start, int length, List<Entry> entries)
        implements ClientboundPacket {

    @Override
    public String name() {
        return PlayClientboundPackets.COMMAND_SUGGESTIONS;
    }

    @Override
    public void write(PacketBuffer buf) {
        buf.writeVarInt(id);
        buf.writeVarInt(start);
        buf.writeVarInt(length);

        buf.writeVarInt(entries.size());

        for (Entry entry : entries) {
            buf.writeString(entry.text());

            if (entry.tooltip() != null) {
                buf.writeBoolean(true);
                buf.writeString(entry.tooltip().toString());

            } else {
                buf.writeBoolean(false);
            }
        }
    }

    public record Entry(String text, Message tooltip) {
    }
}
