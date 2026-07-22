package fr.euphyllia.fidorial.server.protocol.packet.serverbound.configuration;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.protocol.packet.PacketListener;
import fr.euphyllia.fidorial.server.protocol.packet.ServerboundPacket;

import java.util.UUID;

/**
 * <p>Result can be one of the following values:</p>
 *
 * <p><b>Packet ID:</b> Configuration = 6 (0x06), Play = 49 (0x31)</p>
 * <p><b>Source:</b> <a href="https://minecraft.wiki/w/Java_Edition_protocol/Packets#Resource_Pack_Response">Resource Pack Response</a></p>
 *
 * <h4>Packet structure</h4>
 * <table>
 *   <caption>Fields, in write/read order</caption>
 *   <thead>
 *     <tr><th>#</th><th>Field</th><th>Type</th><th>Description</th></tr>
 *   </thead>
 *   <tbody>
 *     <tr><td>0</td><td>UUID</td><td>UUID</td><td>The unique identifier of the resource pack received in the Add Resource Pack request.</td></tr>
 *     <tr><td>1</td><td>Result</td><td>VarInt Enum</td><td>Result ID (see below).</td></tr>
 *   </tbody>
 * </table>
 */
public record ServerboundResourcePackPacket(UUID uuid, Object result) implements ServerboundPacket {

    public static ServerboundResourcePackPacket read(PacketBuffer buf) {
        UUID uuid = null;
        uuid = buf.readUuid();
        Object result = null; // TODO: read result (VarInt Enum)
        return new ServerboundResourcePackPacket(uuid, result);
    }

    @Override
    public void handle(PacketListener listener) {
        // TODO: route to the appropriate listener once the packet is implemented and registered.
    }
}
