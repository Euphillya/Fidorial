package fr.euphyllia.fidorial.server.entity.player.storage;

import fr.euphyllia.fidorial.server.world.chunk.AnvilChunkSerializer;
import fr.euphyllia.fidorial.server.world.nbt.Nbt;
import fr.euphyllia.fidorial.server.world.nbt.NbtCompound;
import fr.euphyllia.fidorial.server.world.nbt.NbtIo;
import fr.euphyllia.fidorial.server.world.nbt.NbtList;
import fr.euphyllia.fidorial.server.world.nbt.NbtType;
import fr.fidorial.inventory.EnderChestInventory;
import fr.fidorial.inventory.ItemStack;
import org.jspecify.annotations.Nullable;

import java.io.IOException;

/**
 * NBT serialization of the ender chest, modelled on {@link PlayerInventoryCodec}.
 *
 * <p>The list is named {@code EnderItems}, as with Mojang, to stay readable with the usual NBT
 * tools.
 */
public final class PlayerEnderChestCodec {

    static final String ROOT_NAME = "PlayerEnderChest";
    static final String LIST_NAME = "EnderItems";

    private PlayerEnderChestCodec() {
    }

    public static NbtList toNbt(final EnderChestInventory enderChest) {
        final NbtList list = new NbtList(NbtType.COMPOUND);
        for (int slot = 0; slot < enderChest.size(); slot++) {
            final ItemStack stack = enderChest.get(slot);
            if (stack.isEmpty()) {
                continue;
            }
            final NbtCompound entry = PlayerInventoryCodec.itemToNbt(stack);
            entry.putByte("Slot", slot);
            list.add(entry);
        }
        return list;
    }

    public static void loadFromNbt(final EnderChestInventory enderChest, @Nullable final NbtList list) {
        enderChest.clear();
        if (list == null) {
            return;
        }
        for (final Nbt element : list.items()) {
            if (!(element instanceof final NbtCompound entry)) {
                continue;
            }
            final int slot = entry.getByte("Slot") & 0xFF;
            if (slot >= enderChest.size()) {
                continue;
            }
            enderChest.set(slot, PlayerInventoryCodec.itemFromNbt(entry));
        }
    }

    public static byte[] encode(final EnderChestInventory enderChest) throws IOException {
        final NbtCompound root = new NbtCompound();
        root.putInt("DataVersion", AnvilChunkSerializer.DATA_VERSION_26_2);
        root.put(LIST_NAME, toNbt(enderChest));
        return NbtIo.writeToBytes(ROOT_NAME, root);
    }

    public static EnderChestInventory decode(final byte[] payload) throws IOException {
        final EnderChestInventory enderChest = new EnderChestInventory();
        final NbtIo.Named named = NbtIo.readFromBytes(payload);
        loadFromNbt(enderChest, named.compound().getList(LIST_NAME));
        return enderChest;
    }
}