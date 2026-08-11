package fr.euphyllia.fidorial.server.world.light;

import java.util.Arrays;

final class LongIntQueue {

    private int[] xs;
    private int[] ys;
    private int[] zs;
    private int[] levels;
    private int head;
    private int tail;

    LongIntQueue() {
        this(16);
    }

    LongIntQueue(final int initialCapacity) {
        final int cap = Math.max(4, initialCapacity);
        xs = new int[cap];
        ys = new int[cap];
        zs = new int[cap];
        levels = new int[cap];
    }

    void push(final int x, final int y, final int z, final int level) {
        if (tail == xs.length) {
            grow();
        }
        xs[tail] = x;
        ys[tail] = y;
        zs[tail] = z;
        levels[tail] = level;
        tail++;
    }

    int pollX() {
        return xs[head];
    }

    int pollY() {
        return ys[head];
    }

    int pollZ() {
        return zs[head];
    }

    int pollLevel() {
        return levels[head++];
    }

    boolean isEmpty() {
        return head == tail;
    }

    private void grow() {
        if (head > 0) {
            final int size = tail - head;
            System.arraycopy(xs, head, xs, 0, size);
            System.arraycopy(ys, head, ys, 0, size);
            System.arraycopy(zs, head, zs, 0, size);
            System.arraycopy(levels, head, levels, 0, size);
            head = 0;
            tail = size;
            if (tail < xs.length) {
                return;
            }
        }
        final int newCap = xs.length * 2;
        xs = Arrays.copyOf(xs, newCap);
        ys = Arrays.copyOf(ys, newCap);
        zs = Arrays.copyOf(zs, newCap);
        levels = Arrays.copyOf(levels, newCap);
    }
}
