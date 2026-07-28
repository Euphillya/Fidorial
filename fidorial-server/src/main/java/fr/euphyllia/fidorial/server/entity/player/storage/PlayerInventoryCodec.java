package fr.euphyllia.fidorial.server.entity.player.storage;

import fr.euphyllia.fidorial.server.world.chunk.AnvilChunkSerializer;
import fr.euphyllia.fidorial.server.world.nbt.Nbt;
import fr.euphyllia.fidorial.server.world.nbt.NbtCompound;
import fr.euphyllia.fidorial.server.world.nbt.NbtIo;
import fr.euphyllia.fidorial.server.world.nbt.NbtList;
import fr.euphyllia.fidorial.server.world.nbt.NbtType;
import fr.fidorial.inventory.ItemStack;
import fr.fidorial.inventory.PlayerInventory;
import net.kyori.adventure.key.Key;
import org.jspecify.annotations.Nullable;

import java.io.IOException;

public final class PlayerInventoryCodec {

    static final String ROOT_NAME = "PlayerInventory";

    private PlayerInventoryCodec() {
    }

    public static NbtCompound itemToNbt(final ItemStack stack) {
        final NbtCompound tag = new NbtCompound();
        tag.putString("id", stack.id().asString());
        tag.putInt("count", stack.count());
        return tag;
    }

    @SuppressWarnings("PatternValidation")
    public static ItemStack itemFromNbt(@Nullable final NbtCompound tag) {
        if (tag == null) {
            return ItemStack.EMPTY;
        }
        final String id = tag.getString("id");
        final int count = tag.getInt("count");
        if (id.isBlank() || count <= 0) {
            return ItemStack.EMPTY;
        }
        return new ItemStack(Key.key(id), count);
    }

    public static NbtList inventoryToNbt(final PlayerInventory inventory) {
        final NbtList list = new NbtList(NbtType.COMPOUND);
        for (int slot = 0; slot < inventory.size(); slot++) {
            final ItemStack stack = inventory.get(slot);
            if (stack.isEmpty()) {
                continue;
            }
            final NbtCompound entry = itemToNbt(stack);
            entry.putByte("Slot", slot);
            list.add(entry);
        }
        return list;
    }

    public static void loadInventoryFromNbt(final PlayerInventory inventory, @Nullable final NbtList list) {
        inventory.clear();
        if (list == null) {
            return;
        }
        for (final Nbt element : list.items()) {
            if (!(element instanceof final NbtCompound entry)) {
                continue;
            }
            final int slot = entry.getByte("Slot") & 0xFF;
            if (slot >= inventory.size()) {
                continue;
            }
            inventory.set(slot, itemFromNbt(entry));
        }
    }

    public static byte[] encode(final PlayerInventory inventory) throws IOException {
        final NbtCompound root = new NbtCompound();
        root.putInt("DataVersion", AnvilChunkSerializer.DATA_VERSION_26_2);
        root.put("Inventory", inventoryToNbt(inventory));
        return NbtIo.writeToBytes(ROOT_NAME, root);
    }

    public static PlayerInventory decode(final byte[] payload) throws IOException {
        final PlayerInventory inventory = new PlayerInventory();
        final NbtIo.Named named = NbtIo.readFromBytes(payload);
        final NbtCompound root = named.compound();
        loadInventoryFromNbt(inventory, root.getList("Inventory"));
        return inventory;
    }
}
