package fr.euphyllia.fidorial.server.entity.player.storage;

import fr.euphyllia.fidorial.server.world.chunk.AnvilChunkSerializer;
import fr.fidorial.inventory.ItemStack;
import fr.fidorial.inventory.PlayerInventory;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.nbt.BinaryTag;
import net.kyori.adventure.nbt.BinaryTagIO;
import net.kyori.adventure.nbt.CompoundBinaryTag;
import net.kyori.adventure.nbt.ListBinaryTag;
import org.jspecify.annotations.Nullable;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Map;

public final class PlayerInventoryCodec {

    static final String ROOT_NAME = "PlayerInventory";

    private PlayerInventoryCodec() {
    }

    public static CompoundBinaryTag itemToNbt(final ItemStack stack) {
        return CompoundBinaryTag.builder()
                .putString("id", stack.id().asString())
                .putInt("count", stack.count())
                .build();
    }

    @SuppressWarnings("PatternValidation")
    public static ItemStack itemFromNbt(@Nullable final CompoundBinaryTag tag) {
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

    public static ListBinaryTag inventoryToNbt(final PlayerInventory inventory) {
        final ListBinaryTag.Builder<BinaryTag> list = ListBinaryTag.builder();
        for (int slot = 0; slot < inventory.size(); slot++) {
            final ItemStack stack = inventory.get(slot);
            if (stack.isEmpty()) {
                continue;
            }
            final CompoundBinaryTag entry = itemToNbt(stack).putByte("Slot", (byte) slot);
            list.add(entry);
        }
        return list.build();
    }

    public static void loadInventoryFromNbt(final PlayerInventory inventory, @Nullable final ListBinaryTag list) {
        inventory.clear();
        if (list == null) {
            return;
        }
        for (final BinaryTag element : list) {
            if (!(element instanceof final CompoundBinaryTag entry)) {
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
        final CompoundBinaryTag root = CompoundBinaryTag.builder()
                .putInt("DataVersion", AnvilChunkSerializer.DATA_VERSION_26_2)
                .put("Inventory", inventoryToNbt(inventory))
                .build();
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        BinaryTagIO.writer().writeNamed(Map.entry(ROOT_NAME, root), baos);
        return baos.toByteArray();
    }

    public static PlayerInventory decode(final byte[] payload) throws IOException {
        final PlayerInventory inventory = new PlayerInventory();
        final CompoundBinaryTag root = BinaryTagIO.reader().readNamed(new ByteArrayInputStream(payload)).getValue();
        loadInventoryFromNbt(inventory, root.getList("Inventory"));
        return inventory;
    }
}
