package fr.euphyllia.fidorial.server.protocol.packet.clientbound.configuration;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.protocol.catalog.ConfigurationClientboundPackets;
import fr.euphyllia.fidorial.server.protocol.packet.ClientboundPacket;

/**
 * <p>Mods and plugins can use this to send their data. Minecraft itself uses several plugin channels . These internal channels are in the minecraft namespace. More information on how it works on Dinnerbone's blog . More documentation about internal and popular registered channels are here .</p>
 *
 * <p><b>Packet ID:</b> Configuration = 1 (0x01), Play = 24 (0x18)</p>
 * <p><b>Source:</b> <a href="https://minecraft.wiki/w/Java_Edition_protocol/Packets#Plugin_Message_(clientbound)">Plugin Message (clientbound)</a></p>
 *
 * <h4>Packet structure</h4>
 * <table>
 *   <caption>Fields, in write/read order</caption>
 *   <thead>
 *     <tr><th>#</th><th>Field</th><th>Type</th><th>Description</th></tr>
 *   </thead>
 *   <tbody>
 *     <tr><td>0</td><td>Channel</td><td>Identifier</td><td>Name of the plugin channel used to send the data.</td></tr>
 *     <tr><td>1</td><td>Data</td><td>Varies</td><td>Any data, depending on the channel. Typically this would be a sequence of fields using standard data types, but some unofficial channels have unusual formats. There is no length prefix that applies to all channel types, but the format specific to the channel may or may not include one or more length prefixes (such as the string length prefix in the standard minecraft:brand channel). The vanilla client enforces a length limit of 1048576 bytes on this data, but only if the channel type is unrecognized.</td></tr>
 *   </tbody>
 * </table>
 */
public record ClientboundBrandPacket(String brand) implements ClientboundPacket {

    private static final String BRAND_CHANNEL = "minecraft:brand";

    @Override
    public String name() {
        return ConfigurationClientboundPackets.CUSTOM_PAYLOAD;
    }

    @Override
    public void write(PacketBuffer buf) {
        buf.writeIdentifier(BRAND_CHANNEL).writeString(brand);
    }
}
