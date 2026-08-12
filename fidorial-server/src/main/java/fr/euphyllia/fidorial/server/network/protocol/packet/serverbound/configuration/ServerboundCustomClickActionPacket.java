package fr.euphyllia.fidorial.server.network.protocol.packet.serverbound.configuration;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.network.protocol.packet.listener.ConfigurationPacketListener;
import fr.fidorial.protocol.PacketListener;
import fr.fidorial.protocol.ServerboundPacket;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.nbt.BinaryTag;

// https://minecraft.wiki/w/Java_Edition_protocol/Packets#Custom_Click_Action
public record ServerboundCustomClickActionPacket(Key id, BinaryTag payload) implements ServerboundPacket {

    private static final int MAX_PAYLOAD_BYTES = 65536;

    public static ServerboundCustomClickActionPacket read(final PacketBuffer buf) {
        final Key id = buf.readKey();
        final BinaryTag payload = buf.readSizedNbt(MAX_PAYLOAD_BYTES);
        return new ServerboundCustomClickActionPacket(id, payload);
    }

    @Override
    public void handle(final PacketListener listener) {
        ((ConfigurationPacketListener) listener).handleCustomClickAction(this);
    }
}
