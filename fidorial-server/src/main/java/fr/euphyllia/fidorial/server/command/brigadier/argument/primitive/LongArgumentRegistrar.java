package fr.euphyllia.fidorial.server.command.brigadier.argument.primitive;

import com.google.gson.JsonObject;
import com.mojang.brigadier.arguments.LongArgumentType;
import fr.euphyllia.fidorial.server.command.brigadier.packet.registry.ArgumentTypeRegistrar;
import fr.euphyllia.fidorial.server.command.brigadier.packet.registry.NumberFlags;
import fr.euphyllia.fidorial.server.network.PacketBuffer;

public final class LongArgumentRegistrar implements ArgumentTypeRegistrar<LongArgumentType, LongArgumentRegistrar.Spec> {

    @Override
    public void serialize(Spec spec, PacketBuffer buf) {
        boolean hasMin = spec.min() != Long.MIN_VALUE;
        boolean hasMax = spec.max() != Long.MAX_VALUE;
        buf.writeByte(NumberFlags.create(hasMin, hasMax));
        if (hasMin) buf.writeLong(spec.min());
        if (hasMax) buf.writeLong(spec.max());
    }

    @Override
    public Spec deserialize(PacketBuffer buf) {
        byte flags = buf.readByte();
        long min = NumberFlags.hasMin(flags) ? buf.readLong() : Long.MIN_VALUE;
        long max = NumberFlags.hasMax(flags) ? buf.readLong() : Long.MAX_VALUE;
        return new Spec(min, max);
    }

    @Override
    public void serializeJson(Spec spec, JsonObject json) {
        if (spec.min() != Long.MIN_VALUE) json.addProperty("min", spec.min());
        if (spec.max() != Long.MAX_VALUE) json.addProperty("max", spec.max());
    }

    @Override
    public Spec access(LongArgumentType argument) {
        return new Spec(argument.getMinimum(), argument.getMaximum());
    }

    public record Spec(long min, long max) implements ArgumentTypeRegistrar.Spec<LongArgumentType> {
        @Override
        public LongArgumentType instantiate() {
            return LongArgumentType.longArg(min, max);
        }

        @Override
        public ArgumentTypeRegistrar<LongArgumentType, ?> type() {
            return new LongArgumentRegistrar();
        }
    }
}
