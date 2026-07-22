package fr.euphyllia.fidorial.server.protocol.packet.clientbound.play;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.protocol.catalog.PlayClientboundPackets;
import fr.euphyllia.fidorial.server.protocol.packet.ClientboundPacket;

/**
 * <p>Advancement structure: Advancement display: Advancement progress:</p>
 *
 * <p><b>Packet ID:</b> Play = 130 (0x82)</p>
 * <p><b>Source:</b> <a href="https://minecraft.wiki/w/Java_Edition_protocol/Packets#Update_Advancements">Update Advancements</a></p>
 *
 * <h4>Packet structure</h4>
 * <table>
 *   <caption>Fields, in write/read order</caption>
 *   <thead>
 *     <tr><th>#</th><th>Field</th><th>Type</th><th>Description</th></tr>
 *   </thead>
 *   <tbody>
 *     <tr><td>0</td><td>Reset/Clear</td><td>Boolean</td><td>Whether to reset/clear the current advancements.</td></tr>
 *     <tr><td>1</td><td>Advancement mapping</td><td>Key</td><td>Prefixed Array</td></tr>
 *     <tr><td>2</td><td>Value</td><td>Advancement</td><td>See below</td></tr>
 *     <tr><td>3</td><td>Identifiers</td><td>Prefixed Array of Identifier</td><td>The identifiers of the advancements that should be removed.</td></tr>
 *     <tr><td>4</td><td>Progress mapping</td><td>Key</td><td>Prefixed Array</td></tr>
 *     <tr><td>5</td><td>Value</td><td>Advancement progress</td><td>See below.</td></tr>
 *     <tr><td>6</td><td>Show advancements</td><td>Boolean</td><td>&nbsp;</td></tr>
 *   </tbody>
 * </table>
 */
public record ClientboundUpdateAdvancementsPacket(boolean resetClear, Object advancementMapping, Object value,
                                                  Object identifiers, Object progressMapping, Object value2,
                                                  boolean showAdvancements) implements ClientboundPacket {

    @Override
    public String name() {
        return PlayClientboundPackets.UPDATE_ADVANCEMENTS;
    }

    @Override
    public void write(PacketBuffer buf) {
        buf.writeBoolean(resetClear);
        // TODO: write advancementMapping (Key)
        // TODO: write value (Advancement)
        // TODO: write identifiers (Prefixed Array of Identifier)
        // TODO: write progressMapping (Key)
        // TODO: write value2 (Advancement progress)
        buf.writeBoolean(showAdvancements);
    }
}
