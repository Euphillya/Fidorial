package fr.euphyllia.fidorial.server.protocol.packet.clientbound.play;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.protocol.catalog.PlayClientboundPackets;
import fr.euphyllia.fidorial.server.protocol.packet.ClientboundPacket;

/**
 * <p>Displays the named particle</p>
 *
 * <p><b>Packet ID:</b> Play = 47 (0x2F)</p>
 * <p><b>Source:</b> <a href="https://minecraft.wiki/w/Java_Edition_protocol/Packets#Particle">Particle</a></p>
 *
 * <h4>Packet structure</h4>
 * <table>
 *   <caption>Fields, in write/read order</caption>
 *   <thead>
 *     <tr><th>#</th><th>Field</th><th>Type</th><th>Description</th></tr>
 *   </thead>
 *   <tbody>
 *     <tr><td>0</td><td>Long Distance</td><td>Boolean</td><td>If true, particle distance increases from 256 to 65536.</td></tr>
 *     <tr><td>1</td><td>Always Visible</td><td>Boolean</td><td>Whether this particle should always be visible.</td></tr>
 *     <tr><td>2</td><td>X</td><td>Double</td><td>X position of the particle.</td></tr>
 *     <tr><td>3</td><td>Y</td><td>Double</td><td>Y position of the particle.</td></tr>
 *     <tr><td>4</td><td>Z</td><td>Double</td><td>Z position of the particle.</td></tr>
 *     <tr><td>5</td><td>Offset X</td><td>Float</td><td>This is added to the X position after being multiplied by random.nextGaussian() .</td></tr>
 *     <tr><td>6</td><td>Offset Y</td><td>Float</td><td>This is added to the Y position after being multiplied by random.nextGaussian() .</td></tr>
 *     <tr><td>7</td><td>Offset Z</td><td>Float</td><td>This is added to the Z position after being multiplied by random.nextGaussian() .</td></tr>
 *     <tr><td>8</td><td>Max Speed</td><td>Float</td><td>&nbsp;</td></tr>
 *     <tr><td>9</td><td>Particle Count</td><td>Int</td><td>The number of particles to create.</td></tr>
 *     <tr><td>10</td><td>Particle ID</td><td>VarInt</td><td>ID in the minecraft:particle_type registry.</td></tr>
 *     <tr><td>11</td><td>Data</td><td>Varies</td><td>Particle data as specified in Java Edition protocol/Particles .</td></tr>
 *   </tbody>
 * </table>
 */
public record ClientboundLevelParticlesPacket(boolean longDistance, boolean alwaysVisible, double x, double y, double z,
                                              float offsetX, float offsetY, float offsetZ, float maxSpeed,
                                              int particleCount, int particleId,
                                              Object data) implements ClientboundPacket {

    @Override
    public String name() {
        return PlayClientboundPackets.LEVEL_PARTICLES;
    }

    @Override
    public void write(PacketBuffer buf) {
        buf.writeBoolean(longDistance);
        buf.writeBoolean(alwaysVisible);
        buf.writeDouble(x);
        buf.writeDouble(y);
        buf.writeDouble(z);
        buf.writeFloat(offsetX);
        buf.writeFloat(offsetY);
        buf.writeFloat(offsetZ);
        buf.writeFloat(maxSpeed);
        buf.writeInt(particleCount);
        buf.writeVarInt(particleId);
        // TODO: write data (Varies)
    }
}
