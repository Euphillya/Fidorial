package fr.euphyllia.fidorial.server.command.brigadier.argument.primitive;

import com.google.gson.JsonObject;
import com.mojang.brigadier.arguments.FloatArgumentType;
import fr.euphyllia.fidorial.server.command.brigadier.packet.registry.ArgumentTypeRegistrar;
import fr.euphyllia.fidorial.server.command.brigadier.packet.registry.NumberFlags;
import fr.euphyllia.fidorial.server.network.PacketBuffer;

public final class FloatArgumentRegistrar
        implements ArgumentTypeRegistrar<FloatArgumentType, FloatArgumentRegistrar.Spec> {

    @Override
    public void serialize(Spec spec, PacketBuffer buf) {
        boolean hasMin = spec.min() != -Float.MAX_VALUE;
        boolean hasMax = spec.max() != Float.MAX_VALUE;
        buf.writeByte(NumberFlags.create(hasMin, hasMax));
        if (hasMin) buf.writeFloat(spec.min());
        if (hasMax) buf.writeFloat(spec.max());
    }

    @Override
    public Spec deserialize(PacketBuffer buf) {
        byte flags = buf.readByte();
        float min = NumberFlags.hasMin(flags) ? buf.readFloat() : -Float.MAX_VALUE;
        float max = NumberFlags.hasMax(flags) ? buf.readFloat() : Float.MAX_VALUE;
        return new Spec(min, max);
    }

    @Override
    public void serializeJson(Spec spec, JsonObject json) {
        if (spec.min() != -Float.MAX_VALUE) json.addProperty("min", spec.min());
        if (spec.max() != Float.MAX_VALUE) json.addProperty("max", spec.max());
    }

    @Override
    public Spec access(FloatArgumentType argument) {
        return new Spec(argument.getMinimum(), argument.getMaximum());
    }

    public record Spec(float min, float max) implements ArgumentTypeRegistrar.Spec<FloatArgumentType> {
        @Override
        public FloatArgumentType instantiate() {
            return FloatArgumentType.floatArg(min, max);
        }

        @Override
        public ArgumentTypeRegistrar<FloatArgumentType, ?> type() {
            return new FloatArgumentRegistrar();
        }
    }
}
