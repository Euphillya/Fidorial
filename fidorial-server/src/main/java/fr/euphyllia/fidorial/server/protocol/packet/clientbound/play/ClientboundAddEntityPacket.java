package fr.euphyllia.fidorial.server.protocol.packet.clientbound.play;

import fr.euphyllia.fidorial.server.entity.AbstractEntity;
import fr.euphyllia.fidorial.server.entity.EntityTypes;
import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.protocol.catalog.PlayClientboundPackets;
import fr.euphyllia.fidorial.server.protocol.packet.ClientboundPacket;
import fr.fidorial.world.Location;

import java.util.UUID;

// https://minecraft.wiki/w/Java_Edition_protocol/Packets#Spawn_Entity
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
