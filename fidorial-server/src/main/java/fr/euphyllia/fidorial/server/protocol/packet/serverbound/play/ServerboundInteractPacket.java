package fr.euphyllia.fidorial.server.protocol.packet.serverbound.play;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.protocol.packet.PacketListener;
import fr.euphyllia.fidorial.server.protocol.packet.ServerboundPacket;

/**
 * <p>This packet is sent from the client to the server when the client right-clicks another entity (a player, minecart, etc). A vanilla server only accepts this packet if the entity being clicked is visible without obstruction and within a 4-unit radius of the player's position. The target offset field represents the difference between the vector location of the cursor at the time of the packet and the entity's position. Note that middle-click in creative mode is interpreted by the client and sent as a Set Creative Mode Slot packet instead. Interaction with the ender dragon is an odd special case characteristic of release deadline–driven design. 8 consecutive entity IDs following the dragon's ID ( id + 1, id + 2, ..., id + 8) are reserved for the 8 hitboxes that make up the dragon:</p>
 *
 * <p><b>Packet ID:</b> Play = 26 (0x1A)</p>
 * <p><b>Source:</b> <a href="https://minecraft.wiki/w/Java_Edition_protocol/Packets#Interact">Interact</a></p>
 *
 * <h4>Packet structure</h4>
 * <table>
 *   <caption>Fields, in write/read order</caption>
 *   <thead>
 *     <tr><th>#</th><th>Field</th><th>Type</th><th>Description</th></tr>
 *   </thead>
 *   <tbody>
 *     <tr><td>0</td><td>Entity ID</td><td>VarInt</td><td>The ID of the entity to interact. Note the special case described below.</td></tr>
 *     <tr><td>1</td><td>Hand</td><td>VarInt Enum</td><td>Only if Type is interact or interact at; 0: main hand, 1: off hand.</td></tr>
 *     <tr><td>2</td><td>Target offset</td><td>LpVec3</td><td>&nbsp;</td></tr>
 *     <tr><td>3</td><td>Sneak Key Pressed</td><td>Boolean</td><td>If the client is pressing the sneak key. Has the same effect as a Player Command Press/Release sneak key preceding the interaction, and the state is permanently changed.</td></tr>
 *   </tbody>
 * </table>
 */
public record ServerboundInteractPacket(int entityId, Object hand, Object targetOffset,
                                        boolean sneakKeyPressed) implements ServerboundPacket {

    public static ServerboundInteractPacket read(PacketBuffer buf) {
        int entityId = 0;
        entityId = buf.readVarInt();
        Object hand = null; // TODO: read hand (VarInt Enum)
        Object targetOffset = null; // TODO: read targetOffset (LpVec3)
        boolean sneakKeyPressed = false;
        sneakKeyPressed = buf.readBoolean();
        return new ServerboundInteractPacket(entityId, hand, targetOffset, sneakKeyPressed);
    }

    @Override
    public void handle(PacketListener listener) {
        // TODO: route to the appropriate listener once the packet is implemented and registered.
    }
}
