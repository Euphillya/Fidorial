package fr.euphyllia.fidorial.server.protocol.packet.serverbound.play;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.protocol.packet.PacketListener;
import fr.euphyllia.fidorial.server.protocol.packet.ServerboundPacket;
import fr.euphyllia.fidorial.server.protocol.packet.listener.PlayPacketListener;

/**
 * <p>Sent by the client to indicate that it is ready to start simulating the player. The vanilla client sends this when the "Loading terrain..." screen is closed . (But see the caveat below.) The vanilla client skips ticking the player entity until the tick on which this packet is sent (the first tick will happen between this packet and the next Client Tick End ). Other entities and objects will still be ticked. Once 60 ticks have elapsed since the last Login or Respawn packet, the vanilla client will start ticking the player and skip sending this packet completely , even after the usual conditions for it have been met. This can happen even before the "Start waiting for level chunks" Game Event is received. The loading screen is not affected in any way by this timer (except indirectly by the player falling into the void after ticking has started). Likewise, the vanilla server will assume that the client has loaded if it takes longer than 60 server ticks to send this packet. A more robust way to detect this condition is to count the number of Client Tick End packets sent by the client. The first player tick will occur after 60 Client Tick End packets have been sent. To determine when this counter should be restarted following a respawn, the Respawn packet can be sent in a bundle together with a Ping packet.</p>
 *
 * <p><b>Packet ID:</b> Play = 44 (0x2C)</p>
 * <p><b>Source:</b> <a href="https://minecraft.wiki/w/Java_Edition_protocol/Packets#Player_Loaded">Player Loaded</a></p>
 *
 * <h4>Packet structure</h4>
 * <table>
 *   <caption>Fields, in write/read order</caption>
 *   <thead>
 *     <tr><th>#</th><th>Field</th><th>Type</th><th>Description</th></tr>
 *   </thead>
 *   <tbody>
 *     <tr><td>0</td><td>no fields</td><td>&nbsp;</td><td>&nbsp;</td></tr>
 *   </tbody>
 * </table>
 */
public record ServerboundPlayerLoadedPacket() implements ServerboundPacket {

    public static ServerboundPlayerLoadedPacket read(PacketBuffer buf) {
        return new ServerboundPlayerLoadedPacket();
    }

    @Override
    public void handle(PacketListener listener) {
        ((PlayPacketListener) listener).handlePlayerLoaded(this);
    }
}
