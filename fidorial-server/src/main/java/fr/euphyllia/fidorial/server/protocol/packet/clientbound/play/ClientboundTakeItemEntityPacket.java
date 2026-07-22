package fr.euphyllia.fidorial.server.protocol.packet.clientbound.play;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.protocol.catalog.PlayClientboundPackets;
import fr.euphyllia.fidorial.server.protocol.packet.ClientboundPacket;

/**
 * Automatically generated from the wiki — verify the serialisation.
 *
 * <p>Sent by the server when someone picks up an item lying on the ground — its sole purpose appears to be the animation of the item flying towards you. It doesn't destroy the entity in the client memory, and it doesn't add it to your inventory. The server only checks for items to be picked up after each Set Player Position (and Set Player Position And Rotation ) packet sent by the client. The collector entity can be any entity; it does not have to be a player. The collected entity can also be any entity, but the vanilla server only uses this for items, experience orbs, the different varieties of arrows and tridents.</p>
 *
 * <p><b>Packet ID:</b> Play = 124 (0x7C)</p>
 * <p><b>Source:</b> <a href="https://minecraft.wiki/w/Java_Edition_protocol/Packets#Pickup_Item">Pickup Item</a></p>
 *
 * <h4>Packet structure</h4>
 * <table>
 *   <caption>Fields, in write/read order</caption>
 *   <thead>
 *     <tr><th>#</th><th>Field</th><th>Type</th><th>Description</th></tr>
 *   </thead>
 *   <tbody>
 *     <tr><td>0</td><td>Collected Entity ID</td><td>VarInt</td><td>&nbsp;</td></tr>
 *     <tr><td>1</td><td>Collector Entity ID</td><td>VarInt</td><td>&nbsp;</td></tr>
 *     <tr><td>2</td><td>Pickup Item Count</td><td>VarInt</td><td>Seems to be 1 for XP orbs, otherwise the number of items in the stack.</td></tr>
 *   </tbody>
 * </table>
 */
public record ClientboundTakeItemEntityPacket(int collectedEntityId, int collectorEntityId,
                                              int pickupItemCount) implements ClientboundPacket {

    @Override
    public String name() {
        return PlayClientboundPackets.TAKE_ITEM_ENTITY;
    }

    @Override
    public void write(PacketBuffer buf) {
        buf.writeVarInt(collectedEntityId);
        buf.writeVarInt(collectorEntityId);
        buf.writeVarInt(pickupItemCount);
    }
}
