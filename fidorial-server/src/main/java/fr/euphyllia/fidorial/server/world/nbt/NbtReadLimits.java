package fr.euphyllia.fidorial.server.world.nbt;

import io.netty.handler.codec.DecoderException;

public final class NbtReadLimits {

    public static final int MAX_NESTING = 512;

    private final long byteBudget;
    private final int nestingLimit;
    private long spent;

    private NbtReadLimits(final long byteBudget, final int nestingLimit) {
        this.byteBudget = byteBudget;
        this.nestingLimit = nestingLimit;
    }

    public static NbtReadLimits withBudget(final long bytes) {
        return new NbtReadLimits(bytes, MAX_NESTING);
    }

    public static NbtReadLimits withBudget(final long bytes, final int nestingLimit) {
        return new NbtReadLimits(bytes, nestingLimit);
    }

    public static NbtReadLimits noBudget() {
        return new NbtReadLimits(Long.MAX_VALUE, MAX_NESTING);
    }

    public void spend(final long amount) {
        if (amount < 0) {
            throw new DecoderException("Refusing to spend a negative NBT byte amount: " + amount);
        }
        spent += amount;
        if (spent > byteBudget) {
            throw new DecoderException("NBT decode exceeded its byte budget (" + spent + "/" + byteBudget + ")");
        }
    }

    public void spendFor(final NbtType type) {
        spend(switch (type) {
            case END -> 0L;
            case BYTE -> 9L;
            case SHORT -> 10L;
            case INT, FLOAT -> 12L;
            case LONG, DOUBLE -> 16L;
            case BYTE_ARRAY, INT_ARRAY, LONG_ARRAY -> 20L;
            case STRING -> 14L;
            case LIST -> 24L;
            case COMPOUND -> 40L;
        });
    }

    public void spendEntry() {
        spend(28L);
    }

    public void checkDepth(final int depth) {
        if (depth > nestingLimit) {
            throw new DecoderException("NBT payload nesting exceeds limit of " + nestingLimit);
        }
    }
}
