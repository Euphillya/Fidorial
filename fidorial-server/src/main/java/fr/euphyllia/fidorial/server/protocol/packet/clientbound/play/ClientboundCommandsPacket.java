package fr.euphyllia.fidorial.server.protocol.packet.clientbound.play;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.protocol.catalog.PlayClientboundPackets;
import fr.euphyllia.fidorial.server.protocol.packet.ClientboundPacket;

/**
 * <p>Lists all of the commands on the server, and how they are parsed. This is a directed graph, with one root node.</p>
 * <p>Each redirect or child node must refer only to nodes that have already been declared. For more information on this packet, see the Java Edition protocol/Command data article.</p>
 *
 * <p><b>Packet ID:</b> Play = 16 (0x10)</p>
 * <p><b>Source:</b> <a href="https://minecraft.wiki/w/Java_Edition_protocol/Packets#Commands">Commands</a></p>
 *
 * <h4>Packet structure</h4>
 * <table>
 *   <caption>Fields, in write/read order</caption>
 *   <thead>
 *     <tr><th>#</th><th>Field</th><th>Type</th><th>Description</th></tr>
 *   </thead>
 *   <tbody>
 *     <tr><td>0</td><td>Nodes</td><td>Prefixed Array of Node</td><td>An array of nodes.</td></tr>
 *     <tr><td>1</td><td>Root index</td><td>VarInt</td><td>Index of the root node in the previous array.</td></tr>
 *   </tbody>
 * </table>
 */
public record ClientboundCommandsPacket(Object nodes, int rootIndex) implements ClientboundPacket {

    @Override
    public String name() {
        return PlayClientboundPackets.COMMANDS;
    }

    @Override
    public void write(PacketBuffer buf) {
        // TODO: write nodes (Prefixed Array of Node)
        buf.writeVarInt(rootIndex);
    }
}
