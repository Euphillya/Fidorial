package fr.euphyllia.fidorial.server.world.nbt;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public final class NbtList implements Nbt, Iterable<Nbt> {

    private final List<Nbt> items = new ArrayList<>();
    private final NbtType declaredType;

    public NbtList(final NbtType type) {
        this.declaredType = type;
    }

    public NbtList() {
        this.declaredType = NbtType.END;
    }

    public NbtType elementType() {
        if (items.isEmpty()) {
            return declaredType;
        }

        final NbtType first = items.getFirst().type();

        for (final Nbt item : items) {
            if (item.type() != first) {
                return NbtType.COMPOUND;
            }
        }

        return first;
    }

    @Override
    public NbtType type() {
        return NbtType.LIST;
    }

    public List<Nbt> items() {
        return items;
    }

    public int size() {
        return items.size();
    }

    public Nbt get(final int index) {
        return items.get(index);
    }

    public NbtList add(final Nbt tag) {
        items.add(tag);
        return this;
    }

    public NbtList addCompound(final NbtCompound compound) {
        return add(compound);
    }

    public NbtList addString(final String value) {
        return add(new NbtString(value));
    }

    public NbtList addInt(final int value) {
        return add(new NbtInt(value));
    }

    @Override
    public Iterator<Nbt> iterator() {
        return items.iterator();
    }
}
