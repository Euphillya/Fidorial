package fr.euphyllia.fidorial.server.entity.player;

import fr.euphyllia.fidorial.api.registry.Key;
import fr.euphyllia.fidorial.server.entity.ItemStack;
import fr.euphyllia.fidorial.server.world.nbt.Nbt;
import fr.euphyllia.fidorial.server.world.nbt.NbtCompound;
import fr.euphyllia.fidorial.server.world.nbt.NbtList;
import fr.euphyllia.fidorial.server.world.nbt.NbtType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

public class PlayerInventory {

    public static final int SIZE = 46;
    private static final Logger LOGGER = LoggerFactory.getLogger(PlayerInventory.class);

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

    public void set(int slot, ItemStack stack) {
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

    public NbtList toNbt() {
        NbtList list = new NbtList(NbtType.COMPOUND);
        for (int slot = 0; slot < SIZE; slot++) {
            ItemStack stack = slots[slot];
            if (stack.isEmpty()) {
                continue;
            }
            NbtCompound entry = stack.toNbt();
            entry.putByte("Slot", slot);
            list.add(entry);
        }
        return list;
    }

    public NbtList fakeTest() {
        NbtList list = new NbtList(NbtType.COMPOUND);
        ItemStack stack = new ItemStack(Key.minecraft("paper"), 2);
        if (stack.isEmpty()) {
            LOGGER.error("EMPTY");
            return list;
        }
        NbtCompound entry = stack.toNbt();
        entry.putByte("Slot", 0);
        list.add(entry);
        return list;
    }

    public void loadFromNbt(NbtList list) {
        clear();
        if (list == null) {
            return;
        }
        for (Nbt element : list.items()) {
            if (!(element instanceof NbtCompound entry)) {
                continue;
            }
            int slot = entry.getByte("Slot") & 0xFF;
            if (slot < 0 || slot >= SIZE) {
                continue;
            }
            slots[slot] = ItemStack.fromNbt(entry);
        }
    }

}
