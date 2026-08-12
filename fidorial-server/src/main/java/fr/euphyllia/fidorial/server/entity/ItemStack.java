package fr.euphyllia.fidorial.server.entity;

import net.kyori.adventure.key.Key;
import net.kyori.adventure.nbt.CompoundBinaryTag;
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
    public static ItemStack fromNbt(@Nullable final CompoundBinaryTag tag) {
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

    public CompoundBinaryTag toNbt() {
        return CompoundBinaryTag.builder()
                .putString("id", id.asString())
                .putInt("count", count)
                .build();
    }

    public Key id() {
        return id;
    }

    public int count() {
        return count;
    }
}
