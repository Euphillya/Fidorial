package fr.euphyllia.fidorial.server.entity;

import fr.euphyllia.fidorial.server.world.nbt.NbtCompound;
import net.kyori.adventure.key.Key;
import org.jspecify.annotations.Nullable;

public class ItemStack {

    private static final Key AIR = Key.key("air");
    public static final ItemStack EMPTY = new ItemStack(AIR, 0);
    private final Key id;
    private final Integer count;

    public ItemStack(final Key id, final int stack) {
        this.id = id;
        this.count = stack;
    }

    public static ItemStack of(final Key key, final int stack) {
        return new ItemStack(key, stack);
    }

    @SuppressWarnings("PatternValidation")
    public static ItemStack fromNbt(@Nullable final NbtCompound tag) {
        if (tag == null) {
            return EMPTY;
        }
        final String id = tag.getString("id");
        final int count = tag.getInt("count");
        if (id.isBlank() || count <= 0) {
            return EMPTY;
        }
        return new ItemStack(Key.key(id), count);
    }

    public boolean isEmpty() {
        return count <= 0 || id.equals(AIR);
    }

    public NbtCompound toNbt() {
        final NbtCompound tag = new NbtCompound();
        tag.putString("id", id.asString());
        tag.putInt("count", count);
        return tag;
    }

    public Key id() {
        return id;
    }

    public int count() {
        return count;
    }
}
