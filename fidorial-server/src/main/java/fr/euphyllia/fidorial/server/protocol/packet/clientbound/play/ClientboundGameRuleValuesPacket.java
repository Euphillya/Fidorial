package fr.euphyllia.fidorial.server.protocol.packet.clientbound.play;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.protocol.catalog.PlayClientboundPackets;
import fr.euphyllia.fidorial.server.protocol.packet.ClientboundPacket;


public record ClientboundGameRuleValuesPacket(Object rules, String value) implements ClientboundPacket {

    @Override
    public String name() {
        return PlayClientboundPackets.GAME_RULE_VALUES;
    }

    @Override
    public void write(PacketBuffer buf) {
        // TODO: write rules (Name)
        buf.writeString(value);
    }
}
