package fr.euphyllia.fidorial.server.network.protocol.packet.clientbound.play;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.network.protocol.catalog.PlayClientboundPackets;
import fr.euphyllia.fidorial.server.network.protocol.packet.ClientboundPacket;
import net.kyori.adventure.key.Key;

public record ClientboundRespawnPacket(Key dimensionKey, int dimensionTypeId, int gameMode, int dataToKeep)
        implements ClientboundPacket {

    /**
     * Keep nothing across the dimension change.
     */
    public static final int KEEP_NOTHING = 0;
    /**
     * Keep the player's attributes across the dimension change.
     */
    public static final int KEEP_ATTRIBUTES = 1;
    /**
     * Keep the player's entity metadata across the dimension change.
     */
    public static final int KEEP_METADATA = 2;
    /**
     * Keep both attributes and metadata across the dimension change.
     */
    public static final int KEEP_ALL = KEEP_ATTRIBUTES | KEEP_METADATA;

    public ClientboundRespawnPacket(final Key dimensionKey, final int dimensionTypeId, final int gameMode) {
        this(dimensionKey, dimensionTypeId, gameMode, KEEP_NOTHING);
    }

    @Override
    public Key name() {
        return PlayClientboundPackets.RESPAWN;
    }

    @Override
    public void write(final PacketBuffer buf) {
        buf.writeVarInt(dimensionTypeId); // dimension type (minecraft:dimension_type registry id)
        buf.writeKey(dimensionKey); // dimension name
        buf.writeLong(0L); // hashed seed (biome noise only)
        buf.writeByte(gameMode); // game mode (unsigned byte)
        buf.writeByte(-1); // previous game mode (-1 = undefined)
        buf.writeBoolean(false); // is debug
        buf.writeBoolean(false); // is flat
        buf.writeBoolean(false); // has death location
        buf.writeVarInt(0); // portal cooldown ticks
        buf.writeVarInt(63); // sea level
        buf.writeByte(dataToKeep);
    }
}
