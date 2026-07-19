package fr.euphyllia.fidorial.server.world.chunk;

import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class PalettedContainer<T> {

    private final List<T> palette = new ArrayList<>();
    private final Map<T, Integer> lookup = new HashMap<>();
    private final int[] data;
    private final int minBits;


    public PalettedContainer(int size, int minBits, T fill) {
        this.data = new int[size];
        this.minBits = minBits;
        indexOf(fill);
    }

    public static <T> PalettedContainer<T> fromNbt(int size, int minBits, List<T> palette, long @Nullable [] data) {
        PalettedContainer<T> c = new PalettedContainer<>(size, minBits, palette.getFirst());
        for (int i = 1; i < palette.size(); i++) {
            c.indexOf(palette.get(i));
        }
        if (data != null && data.length > 0 && palette.size() > 1) {
            int bits = BitPacking.bitsFor(palette.size(), minBits);
            int[] indices = BitPacking.unpack(data, bits, size);
            System.arraycopy(indices, 0, c.data, 0, size);
        }
        return c;
    }

    private int indexOf(T value) {
        Integer i = lookup.get(value);
        if (i != null) return i;
        int next = palette.size();
        palette.add(value);
        lookup.put(value, next);
        return next;
    }

    public void set(int index, T value) {
        data[index] = indexOf(value);
    }

    public T get(int index) {
        return palette.get(data[index]);
    }

    public List<T> palette() {
        return palette;
    }

    public boolean isSingleValue() {
        return palette.size() == 1;
    }

    public int bitsPerEntry() {
        return BitPacking.bitsFor(palette.size(), minBits);
    }

    public long @Nullable [] packedData() {
        if (isSingleValue()) return null;
        return BitPacking.pack(data, bitsPerEntry());
    }
}
