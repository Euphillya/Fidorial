package fr.euphyllia.fidorial.server.network.protocol.packet.clientbound.play;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.network.protocol.catalog.PlayClientboundPackets;
import fr.euphyllia.fidorial.server.network.protocol.packet.ClientboundPacket;
import net.kyori.adventure.text.Component;

public record ClientboundPlayerCombatKillPacket(int playerId, Component message) implements ClientboundPacket {
    @Override
    public String name() {
        return PlayClientboundPackets.PLAYER_COMBAT_KILL;
    }

    @Override
    public void write(final PacketBuffer buf) {
        buf.writeVarInt(playerId);
        buf.writeComponent(message);
    }
}
