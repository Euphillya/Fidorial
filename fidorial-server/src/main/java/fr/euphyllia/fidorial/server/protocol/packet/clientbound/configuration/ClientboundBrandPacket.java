package fr.euphyllia.fidorial.server.protocol.packet.clientbound.configuration;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.protocol.catalog.ConfigurationClientboundPackets;
import fr.euphyllia.fidorial.server.protocol.packet.ClientboundPacket;

public record ClientboundBrandPacket(String brand) implements ClientboundPacket {

    private static final String BRAND_CHANNEL = "minecraft:brand";

    @Override
    public String name() {
        return ConfigurationClientboundPackets.CUSTOM_PAYLOAD;
    }

    @Override
    public void write(PacketBuffer buf) {
        buf.writeIdentifier(BRAND_CHANNEL).writeString(brand);
    }
}
