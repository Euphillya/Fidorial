package fr.euphyllia.fidorial.server.protocol.packet.serverbound.play;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.protocol.packet.PacketListener;
import fr.euphyllia.fidorial.server.protocol.packet.ServerboundPacket;

/**
 * Automatically generated from the wiki — verify the serialisation.
 *
 * <p>When a player selects a specific trade offered by a villager NPC.</p>
 *
 * <p><b>Packet ID:</b> Play = 51 (0x33)</p>
 * <p><b>Source:</b> <a href="https://minecraft.wiki/w/Java_Edition_protocol/Packets#Select_Trade">Select Trade</a></p>
 *
 * <h4>Packet structure</h4>
 * <table>
 *   <caption>Fields, in write/read order</caption>
 *   <thead>
 *     <tr><th>#</th><th>Field</th><th>Type</th><th>Description</th></tr>
 *   </thead>
 *   <tbody>
 *     <tr><td>0</td><td>Selected slot</td><td>VarInt</td><td>The selected slot in the player's current (trading) inventory.</td></tr>
 *   </tbody>
 * </table>
 */
public record ServerboundSelectTradePacket(int selectedSlot) implements ServerboundPacket {

    public static ServerboundSelectTradePacket read(PacketBuffer buf) {
        int selectedSlot = 0;
        selectedSlot = buf.readVarInt();
        return new ServerboundSelectTradePacket(selectedSlot);
    }

    @Override
    public void handle(PacketListener listener) {
        // TODO: route to the appropriate listener once the packet is implemented and registered.
    }
}
