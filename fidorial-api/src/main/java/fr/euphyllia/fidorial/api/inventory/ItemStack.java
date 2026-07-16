package fr.euphyllia.fidorial.api.inventory;

import fr.euphyllia.fidorial.api.registry.Key;

import java.util.Objects;

public class ItemStack {

    private static final Key AIR = Key.minecraft("air");
    public static final ItemStack EMPTY = new ItemStack(AIR, 0);

    private final Key id;
    private final int count;

    public ItemStack(Key id, int count) {
        this.id = Objects.requireNonNull(id, "id");
        this.count = count;
    }

    public static ItemStack of(Key key, int count) {
        return new ItemStack(key, count);
    }

    public boolean isEmpty() {
        return count <= 0 || id.equals(AIR);
    }

    public Key id() {
        return id;
    }

    public int count() {
        return count;
    }

    /**
     * @return une copie de cette pile avec la quantite donnee
     */
    public ItemStack withCount(int newCount) {
        return newCount == count ? this : new ItemStack(id, newCount);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ItemStack other)) return false;
        return count == other.count && id.equals(other.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, count);
    }

    @Override
    public String toString() {
        return "ItemStack{" + id.asString() + " x" + count + "}";
    }
}
