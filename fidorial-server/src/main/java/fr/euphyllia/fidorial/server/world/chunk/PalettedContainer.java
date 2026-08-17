package fr.euphyllia.fidorial.server.world.chunk;

import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.locks.StampedLock;
import java.util.function.ToIntFunction;

public final class PalettedContainer<T> {

    private final List<T> palette = new ArrayList<>();
    private final Object2IntOpenHashMap<T> lookup = new Object2IntOpenHashMap<>();
    private final int[] data;
    private final int minBits;
    private @Nullable T lastValue = null;
    private int lastIndex = -1;

    private final StampedLock lock = new StampedLock();

    private volatile Object[] paletteArray = new Object[0];

    public PalettedContainer(final int size, final int minBits, final T fill) {
        this.data = new int[size];
        this.minBits = minBits;
        this.lookup.defaultReturnValue(-1);
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
        if (value == lastValue) {
            return lastIndex;
        }
        final int i = lookup.getInt(value);
        if (i != -1) {
            lastValue = value;
            lastIndex = i;
            return i;
        }
        final int next = palette.size();
        palette.add(value);
        lookup.put(value, next);
        paletteArray = palette.toArray();
        lastValue = value;
        lastIndex = next;
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

    public long[] packedGlobal(final int bits, final ToIntFunction<T> mapper) {
        final long stamp = lock.readLock();
        try {
            final int[] global = new int[data.length];
            for (int i = 0; i < data.length; i++) {
                global[i] = mapper.applyAsInt(palette.get(data[i]));
            }
            return BitPacking.pack(global, bits);
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

    public PalettedContainerSnapshot<T> snapshot() {
        final long stamp = lock.readLock();
        try {
            @SuppressWarnings("unchecked")
            final T[] pal = (T[]) paletteArray;
            return new PalettedContainerSnapshot<>(Arrays.asList(pal), data.clone(), minBits);
        } finally {
            lock.unlockRead(stamp);
        }
    }

    public record PalettedContainerSnapshot<T>(List<T> palette, int[] data, int minBits) {
        public T get(final int index) {
            return palette.get(data[index]);
        }
    }
}
