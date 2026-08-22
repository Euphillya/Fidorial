package fr.euphyllia.fidorial.server.network.protocol.packet.clientbound.configuration;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.network.protocol.catalog.ConfigurationClientboundPackets;
import fr.euphyllia.fidorial.server.network.protocol.packet.ClientboundPacket;
import net.kyori.adventure.key.Key;

/**
 * @see <a href="https://minecraft.wiki/w/Java_Edition_protocol/Packets#Code_of_Conduct">Code of Conduct</a>
 */
public record ClientboundCodeOfConductPacket(String contents) implements ClientboundPacket {

    @Override
    public Key name() {
        return ConfigurationClientboundPackets.CODE_OF_CONDUCT;
    }

    @Override
    public void write(final PacketBuffer buf) {
        buf.writeString(contents);
    }
}
