package fr.euphyllia.fidorial.server.protocol.packet.clientbound.play;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.protocol.catalog.PlayClientboundPackets;
import fr.euphyllia.fidorial.server.protocol.packet.ClientboundPacket;

/**
 * <p>To change the player's dimension (overworld/nether/end), send them a respawn packet with the appropriate dimension, followed by prechunks/chunks for the new dimension, and finally a position and look packet. You do not need to unload chunks; the client will do it automatically. The background of the loading screen is determined based on the Dimension Name specified in this packet and the one specified in the previous Login or Respawn packet. If either the current or the previous dimension is minecraft:nether , the Nether portal background is used. Otherwise, if the current or the previous dimension is minecraft:the_end , the End portal background is used. If the player is dead (health is 0), the default background is always used.</p>
 *
 * <p><b>Packet ID:</b> Play = 82 (0x52)</p>
 * <p><b>Source:</b> <a href="https://minecraft.wiki/w/Java_Edition_protocol/Packets#Respawn">Respawn</a></p>
 *
 * <h4>Packet structure</h4>
 * <table>
 *   <caption>Fields, in write/read order</caption>
 *   <thead>
 *     <tr><th>#</th><th>Field</th><th>Type</th><th>Description</th></tr>
 *   </thead>
 *   <tbody>
 *     <tr><td>0</td><td>Dimension Type</td><td>VarInt</td><td>The ID of type of dimension in the minecraft:dimension_type registry, defined by the Registry Data packet.</td></tr>
 *     <tr><td>1</td><td>Dimension Name</td><td>Identifier</td><td>Name of the dimension being spawned into.</td></tr>
 *     <tr><td>2</td><td>Hashed seed</td><td>Long</td><td>First 8 bytes of the SHA-256 hash of the world's seed. Used client-side for biome noise</td></tr>
 *     <tr><td>3</td><td>Game mode</td><td>Unsigned Byte</td><td>0: Survival, 1: Creative, 2: Adventure, 3: Spectator.</td></tr>
 *     <tr><td>4</td><td>Previous Game mode</td><td>Byte</td><td>-1: Undefined (null), 0: Survival, 1: Creative, 2: Adventure, 3: Spectator. The previous game mode. Vanilla client uses this for the debug (F3 + N &amp; F3 + F4) game mode switch. (More information needed)</td></tr>
 *     <tr><td>5</td><td>Is Debug</td><td>Boolean</td><td>True if the world is a debug mode world; debug mode worlds cannot be modified and have predefined blocks.</td></tr>
 *     <tr><td>6</td><td>Is Flat</td><td>Boolean</td><td>True if the world is a superflat world; flat worlds have different void fog and a horizon at y=0 instead of y=63.</td></tr>
 *     <tr><td>7</td><td>Has death location</td><td>Boolean</td><td>If true, then the next two fields are present.</td></tr>
 *     <tr><td>8</td><td>Death dimension Name</td><td>Optional Identifier</td><td>Name of the dimension the player died in.</td></tr>
 *     <tr><td>9</td><td>Death location</td><td>Optional Position</td><td>The location that the player died at.</td></tr>
 *     <tr><td>10</td><td>Portal cooldown</td><td>VarInt</td><td>The number of ticks until the player can use the portal again.</td></tr>
 *     <tr><td>11</td><td>Sea level</td><td>VarInt</td><td>&nbsp;</td></tr>
 *     <tr><td>12</td><td>Data kept</td><td>Byte</td><td>Bit mask. 0x01: Keep attributes, 0x02: Keep metadata. Tells which data should be kept on the client side once the player has respawned. In the vanilla implementation, this is context-dependent: normal respawns (after death) keep no data; exiting the end poem/credits keeps the attributes; other dimension changes (portals or teleports) keep all data.</td></tr>
 *   </tbody>
 * </table>
 */
public record ClientboundRespawnPacket(int dimensionType, String dimensionName, long hashedSeed, int gameMode,
                                       byte previousGameMode, boolean isDebug, boolean isFlat, boolean hasDeathLocation,
                                       Object deathDimensionName, Object deathLocation, int portalCooldown,
                                       int seaLevel, byte dataKept) implements ClientboundPacket {

    @Override
    public String name() {
        return PlayClientboundPackets.RESPAWN;
    }

    @Override
    public void write(PacketBuffer buf) {
        buf.writeVarInt(dimensionType);
        buf.writeIdentifier(dimensionName);
        buf.writeLong(hashedSeed);
        buf.writeByte(gameMode);
        buf.writeByte(previousGameMode);
        buf.writeBoolean(isDebug);
        buf.writeBoolean(isFlat);
        buf.writeBoolean(hasDeathLocation);
        // TODO: write deathDimensionName (Optional Identifier)
        // TODO: write deathLocation (Optional Position)
        buf.writeVarInt(portalCooldown);
        buf.writeVarInt(seaLevel);
        buf.writeByte(dataKept);
    }
}
