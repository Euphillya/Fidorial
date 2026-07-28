package fr.euphyllia.fidorial.server.network.nbt;

import fr.euphyllia.fidorial.server.world.nbt.Nbt;
import fr.euphyllia.fidorial.server.world.nbt.NbtIo;
import fr.euphyllia.fidorial.server.world.nbt.NbtReadLimits;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;
import io.netty.handler.codec.DecoderException;
import io.netty.handler.codec.EncoderException;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public final class NetworkNbtHelper {

    private NetworkNbtHelper() {
    }

    public static void writeNbt(ByteBuf buf, Nbt nbt) {
        try {
            NbtIo.writeNetwork(
                    new DataOutputStream(new ByteBufOutputStream(buf)),
                    nbt
            );
        } catch (IOException e) {
            throw new EncoderException("Failed writing NBT", e);
        }
    }

    public static Nbt readNbt(ByteBuf buf, long maxBytes) {
        try {
            return NbtIo.readNetwork(
                    new DataInputStream(new ByteBufInputStream(buf)),
                    NbtReadLimits.withBudget(maxBytes)
            );
        } catch (IOException e) {
            throw new DecoderException("Failed reading NBT", e);
        }
    }
}
