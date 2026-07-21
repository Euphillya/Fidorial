package fr.euphyllia.fidorial.server.protocol.packet.clientbound.play;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.protocol.catalog.PlayClientboundPackets;
import fr.euphyllia.fidorial.server.protocol.packet.ClientboundPacket;

/**
 * <p>Sent when an explosion occurs (creepers, TNT, and ghast fireballs).</p>
 *
 * <p><b>Packet ID:</b> Play = 36 (0x24)</p>
 * <p><b>Source:</b> <a href="https://minecraft.wiki/w/Java_Edition_protocol/Packets#Explosion">Explosion</a></p>
 *
 * <h4>Packet structure</h4>
 * <table>
 *   <caption>Fields, in write/read order</caption>
 *   <thead>
 *     <tr><th>#</th><th>Field</th><th>Type</th><th>Description</th></tr>
 *   </thead>
 *   <tbody>
 *     <tr><td>0</td><td>X</td><td>Double</td><td>&nbsp;</td></tr>
 *     <tr><td>1</td><td>Y</td><td>Double</td><td>&nbsp;</td></tr>
 *     <tr><td>2</td><td>Z</td><td>Double</td><td>&nbsp;</td></tr>
 *     <tr><td>3</td><td>Radius</td><td>Float</td><td>&nbsp;</td></tr>
 *     <tr><td>4</td><td>Block Count</td><td>Int</td><td>&nbsp;</td></tr>
 *     <tr><td>5</td><td>Player Delta Velocity</td><td>X</td><td>Prefixed Optional</td></tr>
 *     <tr><td>6</td><td>Y</td><td>Double</td><td>&nbsp;</td></tr>
 *     <tr><td>7</td><td>Z</td><td>Double</td><td>&nbsp;</td></tr>
 *     <tr><td>8</td><td>Explosion Particle ID</td><td>VarInt</td><td>ID in the minecraft:particle_type registry.</td></tr>
 *     <tr><td>9</td><td>Explosion Particle Data</td><td>Varies</td><td>Particle data as specified in Java Edition protocol/Particles .</td></tr>
 *     <tr><td>10</td><td>Explosion Sound</td><td>ID or Sound Event</td><td>ID in the minecraft:sound_event registry, or an inline definition.</td></tr>
 *     <tr><td>11</td><td>Block Particle Alternatives</td><td>Particle ID</td><td>Prefixed Array</td></tr>
 *     <tr><td>12</td><td>Particle Data</td><td>Varies</td><td>Particle data as specified in Java Edition protocol/Particles .</td></tr>
 *     <tr><td>13</td><td>Scaling</td><td>Float</td><td>&nbsp;</td></tr>
 *     <tr><td>14</td><td>Speed</td><td>Float</td><td>&nbsp;</td></tr>
 *     <tr><td>15</td><td>Weight</td><td>VarInt</td><td>&nbsp;</td></tr>
 *   </tbody>
 * </table>
 */
public record ClientboundExplodePacket(double x, double y, double z, float radius, int blockCount,
                                       Object playerDeltaVelocity, double y2, double z2, int explosionParticleId,
                                       Object explosionParticleData, Object explosionSound,
                                       Object blockParticleAlternatives, Object particleData, float scaling,
                                       float speed, int weight) implements ClientboundPacket {

    @Override
    public String name() {
        return PlayClientboundPackets.EXPLODE;
    }

    @Override
    public void write(PacketBuffer buf) {
        buf.writeDouble(x);
        buf.writeDouble(y);
        buf.writeDouble(z);
        buf.writeFloat(radius);
        buf.writeInt(blockCount);
        // TODO: write playerDeltaVelocity (X)
        buf.writeDouble(y2);
        buf.writeDouble(z2);
        buf.writeVarInt(explosionParticleId);
        // TODO: write explosionParticleData (Varies)
        // TODO: write explosionSound (ID or Sound Event)
        // TODO: write blockParticleAlternatives (Particle ID)
        // TODO: write particleData (Varies)
        buf.writeFloat(scaling);
        buf.writeFloat(speed);
        buf.writeVarInt(weight);
    }
}
