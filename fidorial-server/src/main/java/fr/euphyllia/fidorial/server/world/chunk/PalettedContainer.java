package fr.euphyllia.fidorial.server.world.chunk;

import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.StampedLock;

public final class PalettedContainer<T> {

    private final List<T> palette = new ArrayList<>();
    private final Map<T, Integer> lookup = new HashMap<>();
    private final int[] data;
    private final int minBits;

    private final StampedLock lock = new StampedLock();

    private volatile Object[] paletteArray;

    public PalettedContainer(final int size, final int minBits, final T fill) {
        this.data = new int[size];
        this.minBits = minBits;
        indexOf(fill);
    }

    public static <T> PalettedContainer<T> fromNbt(final int size, final int minBits, final List<T> palette, final long @Nullable [] data) {
        final PalettedContainer<T> c = new PalettedContainer<>(size, minBits, palette.getFirst());
        for (int i = 1; i < palette.size(); i++) {
            c.indexOf(palette.get(i));
        }
        if (data != null && data.length > 0 && palette.size() > 1) {
            final int bits = BitPacking.bitsFor(palette.size(), minBits);
            final int[] indices = BitPacking.unpack(data, bits, size);
            System.arraycopy(indices, 0, c.data, 0, size);
        }
        return c;
    }

    private int indexOf(final T value) {
        final Integer i = lookup.get(value);
        if (i != null) return i;
        final int next = palette.size();
        palette.add(value);
        lookup.put(value, next);
        paletteArray = palette.toArray();
        return next;
    }

    public void set(final int index, final T value) {
        final long stamp = lock.writeLock();
        try {
            data[index] = indexOf(value);
        } finally {
            lock.unlockWrite(stamp);
        }
    }

    public T get(final int index) {
        final long stamp = lock.tryOptimisticRead();
        final Object[] pal = paletteArray;
        final int di = data[index];
        if (stamp != 0L && lock.validate(stamp) && di >= 0 && di < pal.length) {
            @SuppressWarnings("unchecked")
            final T v = (T) pal[di];
            return v;
        }

        final long rs = lock.readLock();
        try {
            return palette.get(data[index]);
        } finally {
            lock.unlockRead(rs);
        }
    }

    public List<T> palette() {
        final long stamp = lock.readLock();
        try {
            return List.copyOf(palette);
        } finally {
            lock.unlockRead(stamp);
        }
    }

    public boolean isSingleValue() {
        final long stamp = lock.readLock();
        try {
            return palette.size() == 1;
        } finally {
            lock.unlockRead(stamp);
        }
    }

    public int bitsPerEntry() {
        final long stamp = lock.readLock();
        try {
            return BitPacking.bitsFor(palette.size(), minBits);
        } finally {
            lock.unlockRead(stamp);
        }
    }

    public long @Nullable [] packedData() {
        final long stamp = lock.readLock();
        try {
            if (palette.size() == 1) return null;
            return BitPacking.pack(data, BitPacking.bitsFor(palette.size(), minBits));
        } finally {
            lock.unlockRead(stamp);
        }
    }
}
