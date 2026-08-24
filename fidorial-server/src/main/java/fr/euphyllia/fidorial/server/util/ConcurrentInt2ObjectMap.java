package fr.euphyllia.fidorial.server.util;

import ca.spottedleaf.concurrentutil.collection.iterator.BaseIntIterator;
import ca.spottedleaf.concurrentutil.collection.iterator.BaseObjectIterator;
import ca.spottedleaf.concurrentutil.map.concurrent.ints.ConcurrentChainedInt2ReferenceHashTable;

import java.util.ArrayList;
import java.util.List;
import java.util.function.IntConsumer;

/**
 * A concurrent map keyed by primitive {@code int}, avoiding {@code Integer} boxing.
 */
public final class ConcurrentInt2ObjectMap<V> {

    private final ConcurrentChainedInt2ReferenceHashTable<V> table;

    public ConcurrentInt2ObjectMap() {
        this.table = new ConcurrentChainedInt2ReferenceHashTable<>();
    }

    public ConcurrentInt2ObjectMap(final int expected) {
        this.table = ConcurrentChainedInt2ReferenceHashTable.createWithExpected(expected);
    }

    public V get(final int key) {
        return table.get(key);
    }

    public V put(final int key, final V value) {
        return table.put(key, value);
    }

    public V remove(final int key) {
        return table.remove(key);
    }

    public boolean containsKey(final int key) {
        return table.containsKey(key);
    }

    public boolean isEmpty() {
        return table.isEmpty();
    }

    public int size() {
        return table.size();
    }

    public void clear() {
        table.clear();
    }

    public void forEachKey(final IntConsumer action) {
        final BaseIntIterator it = table.keyIterator();
        while (it.hasNext()) {
            action.accept(it.nextInt());
        }
    }

    public List<V> valuesSnapshot() {
        final List<V> out = new ArrayList<>(Math.max(8, size()));
        final BaseObjectIterator<V> it = table.valueIterator();
        while (it.hasNext()) {
            out.add(it.next());
        }
        return out;
    }

    @Override
    public String toString() {
        return "ConcurrentInt2ObjectMap(size=" + size() + ")";
    }
}
