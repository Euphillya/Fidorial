package fr.euphyllia.fidorial.server.protocol.packet.clientbound.play;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.protocol.catalog.PlayClientboundPackets;
import fr.euphyllia.fidorial.server.protocol.packet.ClientboundPacket;

import java.util.List;
import java.util.function.Consumer;

// https://minecraft.wiki/w/Java_Edition_protocol/Packets#Set_Entity_Metadata
public record ClientboundSetEntityMetadataPacket(int entityId, List<Entry> metadata) implements ClientboundPacket {

    private static final int METADATA_END = 0xFF;

    public static ClientboundSetEntityMetadataPacket of(final int entityId, final Entry... entries) {
        return new ClientboundSetEntityMetadataPacket(entityId, List.of(entries));
    }

    @Override
    public String name() {
        return PlayClientboundPackets.SET_ENTITY_DATA;
    }

    @Override
    public void write(final PacketBuffer buf) {
        buf.writeVarInt(entityId);
        for (final Entry entry : metadata) {
            entry.write(buf);
        }
        buf.writeByte(METADATA_END);
    }

    public record Entry(int index, int typeId, Consumer<PacketBuffer> valueWriter) {
        private static final int TYPE_BYTE = 0;
        private static final int TYPE_VARINT = 1;
        private static final int TYPE_FLOAT = 3;
        private static final int TYPE_BOOLEAN = 8;

        public static Entry ofByte(final int index, final int value) {
            return new Entry(index, TYPE_BYTE, b -> b.writeByte(value & 0xFF));
        }

        public static Entry varInt(final int index, final int value) {
            return new Entry(index, TYPE_VARINT, b -> b.writeVarInt(value));
        }

        public static Entry ofFloat(final int index, final float value) {
            return new Entry(index, TYPE_FLOAT, b -> b.writeFloat(value));
        }

        public static Entry ofBoolean(final int index, final boolean value) {
            return new Entry(index, TYPE_BOOLEAN, b -> b.writeBoolean(value));
        }

        public static Entry raw(final int index, final int typeId, final Consumer<PacketBuffer> valueWriter) {
            return new Entry(index, typeId, valueWriter);
        }

        void write(final PacketBuffer buf) {
            buf.writeByte(index & 0xFF);
            buf.writeVarInt(typeId);
            valueWriter.accept(buf);
        }
    }
}
