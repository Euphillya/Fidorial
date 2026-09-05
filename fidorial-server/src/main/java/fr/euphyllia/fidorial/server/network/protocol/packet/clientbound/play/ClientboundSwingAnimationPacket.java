package fr.euphyllia.fidorial.server.network.protocol.packet.clientbound.play;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.network.protocol.catalog.PlayClientboundPackets;
import fr.euphyllia.fidorial.server.network.protocol.packet.ClientboundPacket;
import fr.fidorial.registry.data.DataComponentType;
import net.kyori.adventure.key.Key;
import org.jspecify.annotations.Nullable;

public record ClientboundSwingAnimationPacket(int entityId, boolean mainHand, @Nullable DataComponentType attackAnimation) implements ClientboundPacket {

    @Override
    public Key name() {
        return PlayClientboundPackets.SWING_ANIMATION;
    }

    @Override
    public void write(final PacketBuffer buf) {
        buf.writeVarInt(entityId);
        buf.writeVarInt(mainHand ? 0 : 1); // 0 = main_hand, 1 = off_hand
        final SwingAnimation animation = SwingAnimation.DEFAULT; // needs data components!!!!!!!
        buf.writeVarInt(animation.type());
        buf.writeVarInt(animation.duration());
    }

    public record SwingAnimation(int type, int duration) {
        public static final int NO_ANIMATION = 0;
        public static final int WHACK_ANIMATION = 1;
        public static final int STAB_ANIMATION = 2;

        public static final SwingAnimation DEFAULT = new SwingAnimation(WHACK_ANIMATION, 6);
    }
}
