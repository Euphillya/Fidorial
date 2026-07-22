package fr.euphyllia.fidorial.server.protocol.packet.clientbound.play;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.protocol.catalog.PlayClientboundPackets;
import fr.euphyllia.fidorial.server.protocol.packet.ClientboundPacket;
import net.kyori.adventure.text.Component;

/**
 * <p><b>Packet ID:</b> Configuration = 2 (0x02), Play = 32 (0x20)</p>
 * <p><b>Source:</b> <a href="https://minecraft.wiki/w/Java_Edition_protocol/Packets#Disconnect">Disconnect</a></p>
 *
 * <h4>Packet structure</h4>
 * <table>
 *   <caption>Fields, in write/read order</caption>
 *   <thead>
 *     <tr><th>#</th><th>Field</th><th>Type</th><th>Description</th></tr>
 *   </thead>
 *   <tbody>
 *     <tr><td>0</td><td>Reason</td><td>Text Component</td><td>The reason why the player was disconnected.</td></tr>
 *   </tbody>
 * </table>
 */
public record ClientboundDisconnectPacket(Component reason) implements ClientboundPacket {

    @Override
    public String name() {
        return PlayClientboundPackets.DISCONNECT;
    }

    @Override
    public void write(PacketBuffer buf) {
        buf.writeComponent(reason);
    }
}
