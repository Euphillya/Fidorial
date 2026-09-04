package fr.euphyllia.fidorial.server.inventory;

import fr.fidorial.inventory.Container;
import fr.fidorial.item.ItemStack;
import org.jspecify.annotations.Nullable;

import java.util.Arrays;

public final class SimpleContainer implements Container {

    private final ItemStack[] slots;

    public SimpleContainer(final int size) {
        if (size < 0) {
            throw new IllegalArgumentException("Negative size: " + size);
        }
        this.slots = new ItemStack[size];
        Arrays.fill(slots, ItemStack.EMPTY);
    }

    private void checkSlot(final int slot) {
        if (slot < 0 || slot >= slots.length) {
            throw new IndexOutOfBoundsException("Invalid slot: " + slot);
        }
    }

    @Override
    public int size() {
        return slots.length;
    }

    @Override
    public ItemStack get(final int slot) {
        checkSlot(slot);
        return slots[slot];
    }

    @Override
    public void set(final int slot, @Nullable final ItemStack stack) {
        checkSlot(slot);
        slots[slot] = stack == null ? ItemStack.EMPTY : stack;
    }

    @Override
    public void clear() {
        Arrays.fill(slots, ItemStack.EMPTY);
    }

    @Override
    public boolean isEmpty() {
        for (final ItemStack stack : slots) {
            if (!stack.isEmpty()) {
                return false;
            }
        }
        return true;
    }
}
