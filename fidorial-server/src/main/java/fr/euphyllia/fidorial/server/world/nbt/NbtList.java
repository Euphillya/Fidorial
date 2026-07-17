package fr.euphyllia.fidorial.server.world.nbt;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public final class NbtList implements Nbt, Iterable<Nbt> {

    private final List<Nbt> items = new ArrayList<>();
    private NbtType elementType = NbtType.END;

    public NbtList() {
    }

    public NbtList(NbtType elementType) {
        this.elementType = elementType;
    }

    @Override
    public NbtType type() {
        return NbtType.LIST;
    }

    public NbtType elementType() {
        return elementType;
    }

    public List<Nbt> items() {
        return items;
    }

    public int size() {
        return items.size();
    }

    public Nbt get(int index) {
        return items.get(index);
    }

    public NbtList add(Nbt tag) {
        if (elementType == NbtType.END) {
            elementType = tag.type();
        } else if (elementType != tag.type()) {
            throw new IllegalArgumentException(
                    "Liste NBT hétérogène : attendu " + elementType + ", reçu " + tag.type());
        }
        items.add(tag);
        return this;
    }

    public NbtList addCompound(NbtCompound c) {
        return add(c);
    }

    public NbtList addString(String s) {
        return add(new NbtString(s));
    }

    public NbtList addInt(int v) {
        return add(new NbtInt(v));
    }

    @Override
    public Iterator<Nbt> iterator() {
        return items.iterator();
    }
}
