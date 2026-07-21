package fr.euphyllia.fidorial.server.protocol.packet.clientbound.configuration;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.protocol.catalog.ConfigurationClientboundPackets;
import fr.euphyllia.fidorial.server.protocol.packet.ClientboundPacket;

/**
 * <p>Informs the client of which data packs are present on the server.
 The client is expected to respond with its own Known Packs (serverbound) packet containing the subset of packs also known to the client, in the same order as they were listed by the server.
 The vanilla server does not continue with Configuration until it receives a response.</p>
 *
 * <p><b>Packet ID:</b> Configuration = 14 (0x0E)</p>
 * <p><b>Source:</b> <a href="https://minecraft.wiki/w/Java_Edition_protocol/Packets#Known_Packs_(clientbound)">Known Packs (clientbound)</a></p>
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
public record ClientboundSelectKnownPacksPacket(String namespace, String id, String version)
        implements ClientboundPacket {

    @Override
    public String name() {
        return ConfigurationClientboundPackets.SELECT_KNOWN_PACKS;
    }

    @Override
    public void write(PacketBuffer buf) {
        buf.writeVarInt(1);
        buf.writeString(namespace);
        buf.writeString(id);
        buf.writeString(version);
    }
}
