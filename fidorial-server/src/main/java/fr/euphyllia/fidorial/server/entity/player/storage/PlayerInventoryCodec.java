package fr.euphyllia.fidorial.server.entity.player.storage;

import fr.fidorial.inventory.ItemStack;
import fr.fidorial.inventory.PlayerInventory;
import fr.euphyllia.fidorial.server.world.chunk.AnvilChunkSerializer;
import fr.euphyllia.fidorial.server.world.nbt.*;
import net.kyori.adventure.key.Key;
import org.jspecify.annotations.Nullable;

import java.io.IOException;

public final class PlayerInventoryCodec {

    static final String ROOT_NAME = "PlayerInventory";

    private PlayerInventoryCodec() {
    }

    public static NbtCompound itemToNbt(ItemStack stack) {
        NbtCompound tag = new NbtCompound();
        tag.putString("id", stack.id().asString());
        tag.putInt("count", stack.count());
        return tag;
    }

    @SuppressWarnings("PatternValidation")
    public static ItemStack itemFromNbt(@Nullable NbtCompound tag) {
        if (tag == null) {
            return ItemStack.EMPTY;
        }
        String id = tag.getString("id");
        int count = tag.getInt("count");
        if (id.isBlank() || count <= 0) {
            return ItemStack.EMPTY;
        }
        return new ItemStack(Key.key(id), count);
    }

    public static NbtList inventoryToNbt(PlayerInventory inventory) {
        NbtList list = new NbtList(NbtType.COMPOUND);
        for (int slot = 0; slot < inventory.size(); slot++) {
            ItemStack stack = inventory.get(slot);
            if (stack.isEmpty()) {
                continue;
            }
            NbtCompound entry = itemToNbt(stack);
            entry.putByte("Slot", slot);
            list.add(entry);
        }
        return list;
    }

    public static void loadInventoryFromNbt(PlayerInventory inventory, @Nullable NbtList list) {
        inventory.clear();
        if (list == null) {
            return;
        }
        for (Nbt element : list.items()) {
            if (!(element instanceof NbtCompound entry)) {
                continue;
            }
            int slot = entry.getByte("Slot") & 0xFF;
            if (slot >= inventory.size()) {
                continue;
            }
            inventory.set(slot, itemFromNbt(entry));
        }
    }

    public static byte[] encode(PlayerInventory inventory) throws IOException {
        NbtCompound root = new NbtCompound();
        root.putInt("DataVersion", AnvilChunkSerializer.DATA_VERSION_26_2);
        root.put("Inventory", inventoryToNbt(inventory));
        return NbtIo.writeToBytes(ROOT_NAME, root);
    }

    public static PlayerInventory decode(byte[] payload) throws IOException {
        PlayerInventory inventory = new PlayerInventory();
        NbtIo.Named named = NbtIo.readFromBytes(payload);
        NbtCompound root = named.compound();
        loadInventoryFromNbt(inventory, root.getList("Inventory"));
        return inventory;
    }
}
