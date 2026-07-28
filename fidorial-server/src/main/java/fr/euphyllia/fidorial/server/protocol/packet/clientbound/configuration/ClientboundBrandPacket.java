package fr.euphyllia.fidorial.server.protocol.packet.clientbound.configuration;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.protocol.catalog.ConfigurationClientboundPackets;
import fr.euphyllia.fidorial.server.protocol.packet.ClientboundPacket;
import net.kyori.adventure.key.Key;

public record ClientboundBrandPacket(String brand) implements ClientboundPacket {

    private static final Key BRAND_CHANNEL = Key.key("minecraft", "brand");

    @Override
    public String name() {
        return ConfigurationClientboundPackets.CUSTOM_PAYLOAD;
    }

    @Override
    public void write(PacketBuffer buf) {
        buf.writeKey(BRAND_CHANNEL).writeString(brand);
    }
}
