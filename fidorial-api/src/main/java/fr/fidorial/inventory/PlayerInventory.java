package fr.fidorial.inventory;

import org.jspecify.annotations.Nullable;

import java.util.Arrays;

public class PlayerInventory {

    public static final int SIZE = 46;

    private final ItemStack[] slots = new ItemStack[SIZE];

    public PlayerInventory() {
        Arrays.fill(slots, ItemStack.EMPTY);
    }

    private static void checkSlot(int slot) {
        if (slot < 0 || slot >= SIZE) {
            throw new IndexOutOfBoundsException("Emplacement invalide : " + slot);
        }
    }

    public int size() {
        return SIZE;
    }

    public ItemStack get(int slot) {
        checkSlot(slot);
        return slots[slot];
    }

    public void set(int slot, @Nullable ItemStack stack) {
        checkSlot(slot);
        slots[slot] = stack == null ? ItemStack.EMPTY : stack;
    }

    public void clear() {
        Arrays.fill(slots, ItemStack.EMPTY);
    }

    public boolean isEmpty() {
        for (ItemStack stack : slots) {
            if (!stack.isEmpty()) {
                return false;
            }
        }
        return true;
    }

    public ItemStack[] getAllItems() {
        return Arrays.copyOf(slots, SIZE);
    }


    public void setAllItems(ItemStack @Nullable [] contents) {
        if (contents == null) {
            return;
        }
        clear();
        int limit = Math.min(contents.length, SIZE);
        for (int slot = 0; slot < limit; slot++) {
            set(slot, contents[slot]);
        }
    }

}
