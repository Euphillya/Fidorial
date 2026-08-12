package fr.euphyllia.fidorial.server.entity.player.storage;

import fr.euphyllia.fidorial.server.world.chunk.AnvilChunkSerializer;
import fr.fidorial.inventory.EnderChestInventory;
import fr.fidorial.inventory.ItemStack;
import net.kyori.adventure.nbt.BinaryTag;
import net.kyori.adventure.nbt.BinaryTagIO;
import net.kyori.adventure.nbt.CompoundBinaryTag;
import net.kyori.adventure.nbt.ListBinaryTag;
import org.jspecify.annotations.Nullable;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Map;

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

    public static ListBinaryTag toNbt(final EnderChestInventory enderChest) {
        final ListBinaryTag.Builder<BinaryTag> list = ListBinaryTag.builder();
        for (int slot = 0; slot < enderChest.size(); slot++) {
            final ItemStack stack = enderChest.get(slot);
            if (stack.isEmpty()) {
                continue;
            }
            final CompoundBinaryTag entry = PlayerInventoryCodec.itemToNbt(stack).putByte("Slot", (byte) slot);
            list.add(entry);
        }
        return list.build();
    }

    public static void loadFromNbt(final EnderChestInventory enderChest, @Nullable final ListBinaryTag list) {
        enderChest.clear();
        if (list == null) {
            return;
        }
        for (final BinaryTag element : list) {
            if (!(element instanceof final CompoundBinaryTag entry)) {
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
        final CompoundBinaryTag root = CompoundBinaryTag.builder()
                .putInt("DataVersion", AnvilChunkSerializer.DATA_VERSION_26_2)
                .put(LIST_NAME, toNbt(enderChest))
                .build();
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        BinaryTagIO.writer().writeNamed(Map.entry(ROOT_NAME, root), baos);
        return baos.toByteArray();
    }

    public static EnderChestInventory decode(final byte[] payload) throws IOException {
        final EnderChestInventory enderChest = new EnderChestInventory();
        final CompoundBinaryTag root = BinaryTagIO.reader().readNamed(new ByteArrayInputStream(payload)).getValue();
        loadFromNbt(enderChest, root.getList(LIST_NAME));
        return enderChest;
    }
}
