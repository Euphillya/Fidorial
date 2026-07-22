package fr.euphyllia.fidorial.server.protocol.packet.serverbound.play;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.protocol.packet.PacketListener;
import fr.euphyllia.fidorial.server.protocol.packet.ServerboundPacket;

/**
 * <p>Sent by the client whenever debug subscriptions, used by debug graphs and renderers , are activated or deactivated. The list in the packet replaces the previous set of active subscriptions. Subscriptions not in the list are deactivated. If the client does not have permission to receive the requested debug information, the subscriptions are nonetheless retained by the server, and it is not necessary to send this packet again if the permissions change.</p>
 *
 * <p><b>Packet ID:</b> Play = 23 (0x17)</p>
 * <p><b>Source:</b> <a href="https://minecraft.wiki/w/Java_Edition_protocol/Packets#Debug_Subscription_Request">Debug Subscription Request</a></p>
 *
 * <h4>Packet structure</h4>
 * <table>
 *   <caption>Fields, in write/read order</caption>
 *   <thead>
 *     <tr><th>#</th><th>Field</th><th>Type</th><th>Description</th></tr>
 *   </thead>
 *   <tbody>
 *     <tr><td>0</td><td>Subscriptions</td><td>Prefixed Array of VarInt</td><td>List of active debug subscriptions. IDs in the minecraft:debug_subscription registry.</td></tr>
 *   </tbody>
 * </table>
 */
public record ServerboundDebugSubscriptionRequestPacket(Object subscriptions) implements ServerboundPacket {

    public static ServerboundDebugSubscriptionRequestPacket read(PacketBuffer buf) {
        Object subscriptions = null; // TODO: read subscriptions (Prefixed Array of VarInt)
        return new ServerboundDebugSubscriptionRequestPacket(subscriptions);
    }

    @Override
    public void handle(PacketListener listener) {
        // TODO: route to the appropriate listener once the packet is implemented and registered.
    }
}
