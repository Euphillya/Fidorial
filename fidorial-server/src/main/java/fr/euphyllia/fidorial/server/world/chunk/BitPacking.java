package fr.euphyllia.fidorial.server.world.chunk;

public final class BitPacking {

    private BitPacking() {
    }

    public static long[] pack(int[] values, int bits) {
        if (bits < 1) bits = 1;
        int valuesPerLong = 64 / bits;
        int longCount = (values.length + valuesPerLong - 1) / valuesPerLong;
        long[] out = new long[longCount];
        long mask = (1L << bits) - 1L;

        for (int i = 0; i < values.length; i++) {
            int longIndex = i / valuesPerLong;
            int offset = (i % valuesPerLong) * bits;
            out[longIndex] |= (values[i] & mask) << offset;
        }
        return out;
    }

    public static int[] unpack(long[] data, int bits, int count) {
        if (bits < 1) bits = 1;
        int valuesPerLong = 64 / bits;
        long mask = (1L << bits) - 1L;
        int[] out = new int[count];

        for (int i = 0; i < count; i++) {
            int longIndex = i / valuesPerLong;
            int offset = (i % valuesPerLong) * bits;
            out[i] = (int) ((data[longIndex] >>> offset) & mask);
        }
        return out;
    }

    public static int bitsFor(int paletteSize, int minimum) {
        int bits = 32 - Integer.numberOfLeadingZeros(Math.max(1, paletteSize - 1));
        return Math.max(minimum, bits);
    }
}
