package fr.euphyllia.fidorial.server.protocol.packet.clientbound.configuration;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.protocol.catalog.ConfigurationClientboundPackets;
import fr.euphyllia.fidorial.server.protocol.packet.ClientboundPacket;

/**
 * <p>Show the client the server Code of Conduct. Server will not continue with the configuration stage until the client has accepted the Code of Conduct via the Accept Code of Conduct packet.</p>
 *
 * <p><b>Packet ID:</b> Configuration = 19 (0x13)</p>
 * <p><b>Source:</b> <a href="https://minecraft.wiki/w/Java_Edition_protocol/Packets#Code_of_Conduct">Code of Conduct</a></p>
 *
 * <h4>Packet structure</h4>
 * <table>
 *   <caption>Fields, in write/read order</caption>
 *   <thead>
 *     <tr><th>#</th><th>Field</th><th>Type</th><th>Description</th></tr>
 *   </thead>
 *   <tbody>
 *     <tr><td>0</td><td>Code of Conduct</td><td>String</td><td>Code of Conduct of the server.</td></tr>
 *   </tbody>
 * </table>
 */
public record ClientboundCodeOfConductPacket(String codeOfConduct) implements ClientboundPacket {

    @Override
    public String name() {
        return ConfigurationClientboundPackets.CODE_OF_CONDUCT;
    }

    @Override
    public void write(PacketBuffer buf) {
        buf.writeString(codeOfConduct);
    }
}
