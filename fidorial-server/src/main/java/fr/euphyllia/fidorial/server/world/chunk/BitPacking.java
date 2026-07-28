package fr.euphyllia.fidorial.server.world.chunk;

public final class BitPacking {

    private BitPacking() {
    }

    public static long[] pack(final int[] values, int bits) {
        if (bits < 1) bits = 1;
        final int valuesPerLong = 64 / bits;
        final int longCount = (values.length + valuesPerLong - 1) / valuesPerLong;
        final long[] out = new long[longCount];
        final long mask = (1L << bits) - 1L;

        for (int i = 0; i < values.length; i++) {
            final int longIndex = i / valuesPerLong;
            final int offset = (i % valuesPerLong) * bits;
            out[longIndex] |= (values[i] & mask) << offset;
        }
        return out;
    }

    public static int[] unpack(final long[] data, int bits, final int count) {
        if (bits < 1) bits = 1;
        final int valuesPerLong = 64 / bits;
        final long mask = (1L << bits) - 1L;
        final int[] out = new int[count];

        for (int i = 0; i < count; i++) {
            final int longIndex = i / valuesPerLong;
            final int offset = (i % valuesPerLong) * bits;
            out[i] = (int) ((data[longIndex] >>> offset) & mask);
        }
        return out;
    }

    public static int bitsFor(final int paletteSize, final int minimum) {
        final int bits = 32 - Integer.numberOfLeadingZeros(Math.max(1, paletteSize - 1));
        return Math.max(minimum, bits);
    }
}
