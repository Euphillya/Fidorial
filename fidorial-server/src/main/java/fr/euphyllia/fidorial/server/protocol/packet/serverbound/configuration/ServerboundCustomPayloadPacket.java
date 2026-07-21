package fr.euphyllia.fidorial.server.protocol.packet.serverbound.configuration;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.protocol.packet.PacketListener;
import fr.euphyllia.fidorial.server.protocol.packet.ServerboundPacket;

/**
 * <p>Mods and plugins can use this to send their data. Minecraft itself uses some plugin channels . These internal channels are in the minecraft namespace. More documentation on this: https://dinnerbone.com/blog/2012/01/13/minecraft-plugin-channels-messaging/</p>
 *
 * <p><b>Packet ID:</b> Configuration = 2 (0x02), Play = 22 (0x16)</p>
 * <p><b>Source:</b> <a href="https://minecraft.wiki/w/Java_Edition_protocol/Packets#Plugin_Message_(serverbound)">Plugin Message (serverbound)</a></p>
 *
 * <h4>Packet structure</h4>
 * <table>
 *   <caption>Fields, in write/read order</caption>
 *   <thead>
 *     <tr><th>#</th><th>Field</th><th>Type</th><th>Description</th></tr>
 *   </thead>
 *   <tbody>
 *     <tr><td>0</td><td>Channel</td><td>Identifier</td><td>Name of the plugin channel used to send the data.</td></tr>
 *     <tr><td>1</td><td>Data</td><td>Varies</td><td>Any data, depending on the channel. Typically this would be a sequence of fields using standard data types, but some unofficial channels have unusual formats. There is no length prefix that applies to all channel types, but the format specific to the channel may or may not include one or more length prefixes (such as the string length prefix in the standard minecraft:brand channel). The vanilla server enforces a length limit of 32767 bytes on this data, but only if the channel type is unrecognized.</td></tr>
 *   </tbody>
 * </table>
 */
public record ServerboundCustomPayloadPacket(String channel, Object data) implements ServerboundPacket {

    public static ServerboundCustomPayloadPacket read(PacketBuffer buf) {
        String channel = null;
        channel = buf.readIdentifier();
        Object data = null; // TODO: read data (Varies)
        return new ServerboundCustomPayloadPacket(channel, data);
    }

    @Override
    public void handle(PacketListener listener) {
        // TODO: route to the appropriate listener once the packet is implemented and registered.
    }
}
