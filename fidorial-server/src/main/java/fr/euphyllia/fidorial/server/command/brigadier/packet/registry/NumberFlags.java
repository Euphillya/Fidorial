package fr.euphyllia.fidorial.server.command.brigadier.packet.registry;

public final class NumberFlags {
    private static final byte HAS_MIN = 0x01;
    private static final byte HAS_MAX = 0x02;

    private NumberFlags() {}

    public static byte create(boolean hasMin, boolean hasMax) {
        byte flags = 0;
        if (hasMin) flags |= HAS_MIN;
        if (hasMax) flags |= HAS_MAX;
        return flags;
    }

    public static boolean hasMin(byte flags) {
        return (flags & HAS_MIN) != 0;
    }

    public static boolean hasMax(byte flags) {
        return (flags & HAS_MAX) != 0;
    }
}
