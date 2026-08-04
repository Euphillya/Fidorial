package fr.euphyllia.fidorial.server.network.protocol.packet.clientbound.play;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.network.protocol.catalog.PlayClientboundPackets;
import fr.euphyllia.fidorial.server.network.protocol.packet.ClientboundPacket;
import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;

import java.util.UUID;

public sealed interface ClientboundBossEventPacket extends ClientboundPacket {

    UUID id();

    @Override
    default Key name() {
        return PlayClientboundPackets.BOSS_EVENT;
    }

    record Add(UUID id, Component title, float progress, BossBar.Color color,
               BossBar.Overlay overlay, int flags) implements ClientboundBossEventPacket {
        @Override
        public void write(final PacketBuffer buf) {
            buf.writeUuid(id).writeVarInt(0).writeComponent(title)
                    .writeFloat(progress).writeVarInt(color.ordinal())
                    .writeVarInt(overlay.ordinal()).writeByte((byte) flags);
        }
    }

    record Remove(UUID id) implements ClientboundBossEventPacket {
        @Override
        public void write(final PacketBuffer buf) {
            buf.writeUuid(id).writeVarInt(1);
        }
    }

    record UpdateProgress(UUID id, float progress) implements ClientboundBossEventPacket {
        @Override
        public void write(final PacketBuffer buf) {
            buf.writeUuid(id).writeVarInt(2).writeFloat(progress);
        }
    }

    record UpdateName(UUID id, Component title) implements ClientboundBossEventPacket {
        @Override
        public void write(final PacketBuffer buf) {
            buf.writeUuid(id).writeVarInt(3).writeComponent(title);
        }
    }

    record UpdateStyle(UUID id, BossBar.Color color, BossBar.Overlay overlay) implements ClientboundBossEventPacket {
        @Override
        public void write(final PacketBuffer buf) {
            buf.writeUuid(id).writeVarInt(4).writeVarInt(color.ordinal()).writeVarInt(overlay.ordinal());
        }
    }

    record UpdateProperties(UUID id, int flags) implements ClientboundBossEventPacket {
        @Override
        public void write(final PacketBuffer buf) {
            buf.writeUuid(id).writeVarInt(5).writeByte((byte) flags);
        }
    }
}
