package fr.euphyllia.fidorial.server.protocol.packet.clientbound.play;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.protocol.catalog.PlayClientboundPackets;
import fr.euphyllia.fidorial.server.protocol.packet.ClientboundPacket;

/**
 * <p>Show a custom dialog screen to the client.</p>
 *
 * <p><b>Packet ID:</b> Play = 140 (0x8C)</p>
 * <p><b>Source:</b> <a href="https://minecraft.wiki/w/Java_Edition_protocol/Packets#Show_Dialog_(play)">Show Dialog (play)</a></p>
 *
 * <h4>Packet structure</h4>
 * <table>
 *   <caption>Fields, in write/read order</caption>
 *   <thead>
 *     <tr><th>#</th><th>Field</th><th>Type</th><th>Description</th></tr>
 *   </thead>
 *   <tbody>
 *     <tr><td>0</td><td>Dialog</td><td>ID or NBT</td><td>Inline definition as described at Dialog#Dialog format .</td></tr>
 *   </tbody>
 * </table>
 */
public record ClientboundShowDialogPacket(Object dialog) implements ClientboundPacket {

    @Override
    public String name() {
        return PlayClientboundPackets.SHOW_DIALOG;
    }

    @Override
    public void write(PacketBuffer buf) {
        // TODO: write dialog (ID or NBT)
    }
}
