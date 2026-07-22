package fr.euphyllia.fidorial.server.protocol.packet.serverbound.play;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.protocol.packet.PacketListener;
import fr.euphyllia.fidorial.server.protocol.packet.ServerboundPacket;

/**
 * <p><b>Packet ID:</b> Play = 57 (0x39)</p>
 * <p><b>Source:</b> <a href="https://minecraft.wiki/w/Java_Edition_protocol/Packets#Set_Game_Rules">Set Game Rules</a></p>
 *
 * <h4>Packet structure</h4>
 * <table>
 *   <caption>Fields, in write/read order</caption>
 *   <thead>
 *     <tr><th>#</th><th>Field</th><th>Type</th><th>Description</th></tr>
 *   </thead>
 *   <tbody>
 *     <tr><td>0</td><td>Rules</td><td>Name</td><td>Prefixed Array</td></tr>
 *     <tr><td>1</td><td>Value</td><td>String (32767)</td><td>&nbsp;</td></tr>
 *   </tbody>
 * </table>
 */
public record ServerboundSetGameRulePacket(Object rules, String value) implements ServerboundPacket {

    public static ServerboundSetGameRulePacket read(PacketBuffer buf) {
        Object rules = null; // TODO: read rules (Name)
        String value = null;
        value = buf.readString(32767);
        return new ServerboundSetGameRulePacket(rules, value);
    }

    @Override
    public void handle(PacketListener listener) {
        // TODO: route to the appropriate listener once the packet is implemented and registered.
    }
}
