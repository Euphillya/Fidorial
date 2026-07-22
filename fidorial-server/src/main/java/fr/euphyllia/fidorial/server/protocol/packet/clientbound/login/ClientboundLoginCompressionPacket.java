package fr.euphyllia.fidorial.server.protocol.packet.clientbound.login;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.protocol.catalog.LoginClientboundPackets;
import fr.euphyllia.fidorial.server.protocol.packet.ClientboundPacket;

/**
 * <p>Enables compression.</p>
 * <p>If compression is enabled, all following packets are encoded in the compressed packet format .</p>
 * <p>Negative values will disable compression, meaning the packet format should remain in the uncompressed packet format .</p>
 * <p>However, this packet is entirely optional, and if not sent, compression will also not be enabled (the vanilla server does not send the packet when compression is disabled).</p>
 *
 * <p><b>Packet ID:</b> Login = 3 (0x03)</p>
 * <p><b>Source:</b> <a href="https://minecraft.wiki/w/Java_Edition_protocol/Packets#Set_Compression">Set Compression</a></p>
 *
 * <h4>Packet structure</h4>
 * <table>
 *   <caption>Fields, in write/read order</caption>
 *   <thead>
 *     <tr><th>#</th><th>Field</th><th>Type</th><th>Description</th></tr>
 *   </thead>
 *   <tbody>
 *     <tr><td>0</td><td>Threshold</td><td>VarInt</td><td>Maximum size of a packet before it is compressed.</td></tr>
 *   </tbody>
 * </table>
 */
public record ClientboundLoginCompressionPacket(int threshold) implements ClientboundPacket {

    @Override
    public String name() {
        return LoginClientboundPackets.LOGIN_COMPRESSION;
    }

    @Override
    public void write(PacketBuffer buf) {
        buf.writeVarInt(threshold);
    }
}
