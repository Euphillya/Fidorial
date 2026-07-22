package fr.euphyllia.fidorial.server.protocol.packet.clientbound.play;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.protocol.catalog.PlayClientboundPackets;
import fr.euphyllia.fidorial.server.protocol.packet.ClientboundPacket;
import net.kyori.adventure.text.Component;

/**
 * <p><b>Packet ID:</b> Play = 86 (0x56)</p>
 * <p><b>Source:</b> <a href="https://minecraft.wiki/w/Java_Edition_protocol/Packets#Server_Data">Server Data</a></p>
 *
 * <h4>Packet structure</h4>
 * <table>
 *   <caption>Fields, in write/read order</caption>
 *   <thead>
 *     <tr><th>#</th><th>Field</th><th>Type</th><th>Description</th></tr>
 *   </thead>
 *   <tbody>
 *     <tr><td>0</td><td>MOTD</td><td>Text Component</td><td>&nbsp;</td></tr>
 *     <tr><td>1</td><td>Icon</td><td>Prefixed Optional Prefixed Array of Byte</td><td>Icon bytes in the PNG format.</td></tr>
 *   </tbody>
 * </table>
 */
public record ClientboundServerDataPacket(Component motd, Object icon) implements ClientboundPacket {

    @Override
    public String name() {
        return PlayClientboundPackets.SERVER_DATA;
    }

    @Override
    public void write(PacketBuffer buf) {
        buf.writeComponent(motd);
        // TODO: write icon (Prefixed Optional Prefixed Array of Byte)
    }
}
