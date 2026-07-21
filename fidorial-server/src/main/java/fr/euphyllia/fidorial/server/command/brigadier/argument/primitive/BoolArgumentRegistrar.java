package fr.euphyllia.fidorial.server.command.brigadier.argument.primitive;

import com.google.gson.JsonObject;
import com.mojang.brigadier.arguments.BoolArgumentType;
import fr.euphyllia.fidorial.server.command.brigadier.packet.registry.ArgumentTypeRegistrar;
import fr.euphyllia.fidorial.server.network.PacketBuffer;

public final class BoolArgumentRegistrar implements ArgumentTypeRegistrar<BoolArgumentType, BoolArgumentRegistrar.Spec> {

    @Override
    public void serialize(Spec spec, PacketBuffer buf) {}

    @Override
    public Spec deserialize(PacketBuffer buf) {
        return new Spec();
    }

    @Override
    public void serializeJson(Spec spec, JsonObject json) {}

    @Override
    public Spec access(BoolArgumentType argument) {
        return new Spec();
    }

    public record Spec() implements ArgumentTypeRegistrar.Spec<BoolArgumentType> {
        @Override
        public BoolArgumentType instantiate() {
            return BoolArgumentType.bool();
        }

        @Override
        public ArgumentTypeRegistrar<BoolArgumentType, ?> type() {
            return new BoolArgumentRegistrar();
        }
    }
}
