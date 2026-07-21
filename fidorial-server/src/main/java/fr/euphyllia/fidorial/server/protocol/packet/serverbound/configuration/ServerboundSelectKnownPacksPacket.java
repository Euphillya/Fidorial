package fr.euphyllia.fidorial.server.protocol.packet.serverbound.configuration;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.protocol.packet.PacketListener;
import fr.euphyllia.fidorial.server.protocol.packet.ServerboundPacket;
import fr.euphyllia.fidorial.server.protocol.packet.listener.ConfigurationPacketListener;

/**
 * <p>Informs the server of which of the data packs it knows are also present on the client. The client sends this in response to Known Packs (clientbound) . If the client specifies a pack in this packet, the server may omit its contained NBT data (but not entry listings) from the Registry Data packet.</p>
 *
 * <p><b>Packet ID:</b> Configuration = 7 (0x07)</p>
 * <p><b>Source:</b> <a href="https://minecraft.wiki/w/Java_Edition_protocol/Packets#Known_Packs_(serverbound)">Known Packs (serverbound)</a></p>
 *
 * <h4>Packet structure</h4>
 * <table>
 *   <caption>Fields, in write/read order</caption>
 *   <thead>
 *     <tr><th>#</th><th>Field</th><th>Type</th><th>Description</th></tr>
 *   </thead>
 *   <tbody>
 *     <tr><td>0</td><td>Known Packs</td><td>Namespace</td><td>Prefixed Array</td></tr>
 *     <tr><td>1</td><td>ID</td><td>String (32767)</td><td>Pathname part of the name of the pack, such as core .</td></tr>
 *     <tr><td>2</td><td>Version</td><td>String (32767)</td><td>Version of the pack. For minecraft:core this corresponds to Minecraft version numbers, such as 1.21.10 . Note that the vanilla data pack can change without a protocol version bump, and even a completely vanilla server talking to a vanilla client may fall back to sending all registry data over the wire, if the two are on different protocol-compatible patch versions.</td></tr>
 *   </tbody>
 * </table>
 */
public record ServerboundSelectKnownPacksPacket() implements ServerboundPacket {

    public static ServerboundSelectKnownPacksPacket read(PacketBuffer buf) {
        return new ServerboundSelectKnownPacksPacket();
    }

    @Override
    public void handle(PacketListener listener) {
        ((ConfigurationPacketListener) listener).handleSelectKnownPacks(this);
    }
}
