package fr.euphyllia.fidorial.server.protocol.packet.clientbound.play;

import fr.euphyllia.fidorial.server.entity.AbstractEntity;
import fr.euphyllia.fidorial.server.entity.EntityTypes;
import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.protocol.catalog.PlayClientboundPackets;
import fr.euphyllia.fidorial.server.protocol.packet.ClientboundPacket;
import fr.fidorial.world.Location;

import java.util.UUID;

/**
 * <p>Sent by the server to create an entity on the client, normally upon the entity spawning within or entering the player's view range. The local player entity is automatically created by the client, and must not be created explicitly using this packet. Doing so on the vanilla client will have strange consequences. When in online mode , the UUIDs must be valid and have valid skin blobs.
 In offline mode, the vanilla server uses UUID v3 and chooses the player's UUID by using the String OfflinePlayer:&lt;player name&gt; , encoding it in UTF-8 (and case-sensitive), then processes it with UUID.nameUUIDFromBytes . For NPCs UUID v2 should be used. Note: In an example UUID, xxxxxxxx-xxxx-Yxxx-xxxx-xxxxxxxxxxxx , the UUID version is specified by Y . So, for UUID v3, Y will always be 3 , and for UUID v2, Y will always be 2 .</p>
 *
 * <p><b>Packet ID:</b> Play = 1 (0x01)</p>
 * <p><b>Source:</b> <a href="https://minecraft.wiki/w/Java_Edition_protocol/Packets#Spawn_Entity">Spawn Entity</a></p>
 *
 * <h4>Packet structure</h4>
 * <table>
 *   <caption>Fields, in write/read order</caption>
 *   <thead>
 *     <tr><th>#</th><th>Field</th><th>Type</th><th>Description</th></tr>
 *   </thead>
 *   <tbody>
 *     <tr><td>0</td><td>Entity ID</td><td>VarInt</td><td>A unique integer ID mostly used in the protocol to identify the entity. If an entity with the same ID already exists on the client, it is automatically deleted and replaced by the new entity. On the vanilla server entity IDs are globally unique across all dimensions and never reused while the server is running, but not preserved across server restarts.</td></tr>
 *     <tr><td>1</td><td>Entity UUID</td><td>UUID</td><td>A unique identifier that is mostly used in persistence and places where the uniqueness matters more. It is possible to create multiple entities with the same UUID on the vanilla client, but a warning will be logged, and functionality dependent on UUIDs may ignore the entity or otherwise misbehave.</td></tr>
 *     <tr><td>2</td><td>Type</td><td>VarInt</td><td>ID in the minecraft:entity_type registry (see "type" field in Java Edition protocol/Entity metadata#Entities ).</td></tr>
 *     <tr><td>3</td><td>X</td><td>Double</td><td>&nbsp;</td></tr>
 *     <tr><td>4</td><td>Y</td><td>Double</td><td>&nbsp;</td></tr>
 *     <tr><td>5</td><td>Z</td><td>Double</td><td>&nbsp;</td></tr>
 *     <tr><td>6</td><td>Velocity</td><td>LpVec3</td><td>&nbsp;</td></tr>
 *     <tr><td>7</td><td>Pitch</td><td>Angle</td><td>&nbsp;</td></tr>
 *     <tr><td>8</td><td>Yaw</td><td>Angle</td><td>&nbsp;</td></tr>
 *     <tr><td>9</td><td>Head Yaw</td><td>Angle</td><td>Only used by living entities, where the head of the entity may differ from the general body rotation.</td></tr>
 *     <tr><td>10</td><td>Data</td><td>VarInt</td><td>Meaning dependent on the value of the Type field, see Java Edition protocol/Object data for details.</td></tr>
 *   </tbody>
 * </table>
 */
public record ClientboundAddEntityPacket(int entityId, UUID uuid, int typeNetworkId,
                                         double x, double y, double z,
                                         double velocityX, double velocityY, double velocityZ,
                                         float pitch, float yaw, float headYaw,
                                         int data)
        implements ClientboundPacket {

    public static ClientboundAddEntityPacket of(AbstractEntity entity) {
        Location location = entity.location();
        return new ClientboundAddEntityPacket(
                entity.entityId(), entity.uuid(), EntityTypes.networkId(entity.type()),
                location.x(), location.y(), location.z(),
                0.0, 0.0, 0.0,
                location.pitch(), location.yaw(), location.yaw(),
                0);
    }

    @Override
    public String name() {
        return PlayClientboundPackets.ADD_ENTITY;
    }

    @Override
    public void write(PacketBuffer buf) {
        buf.writeVarInt(entityId);
        buf.writeUuid(uuid);
        buf.writeVarInt(typeNetworkId);
        buf.writeDouble(x);
        buf.writeDouble(y);
        buf.writeDouble(z);
        buf.writeLpVec3(velocityX, velocityY, velocityZ);
        buf.writeAngle(pitch);
        buf.writeAngle(yaw);
        buf.writeAngle(headYaw);
        buf.writeVarInt(data);
    }
}
