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

        int idx = 0;
        for (int longIndex = 0; longIndex < longCount; longIndex++) {
            long word = 0L;
            final int limit = Math.min(valuesPerLong, values.length - idx);
            for (int offset = 0; offset < limit; offset++) {
                word |= (values[idx++] & mask) << (offset * bits);
            }
            out[longIndex] = word;
        }
        return out;
    }

    public static int[] unpack(final long[] data, int bits, final int count) {
        if (bits < 1) bits = 1;
        final int valuesPerLong = 64 / bits;
        final long mask = (1L << bits) - 1L;
        final int[] out = new int[count];

        int idx = 0;
        for (int longIndex = 0; idx < count; longIndex++) {
            final long word = data[longIndex];
            final int limit = Math.min(valuesPerLong, count - idx);
            for (int offset = 0; offset < limit; offset++) {
                out[idx++] = (int) ((word >>> (offset * bits)) & mask);
            }
        }
        return out;
    }

    public static int bitsFor(final int paletteSize, final int minimum) {
        final int bits = 32 - Integer.numberOfLeadingZeros(Math.max(1, paletteSize - 1));
        return Math.max(minimum, bits);
    }
}
