package fr.fidorial.item;

import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import org.jspecify.annotations.Nullable;

import java.util.Objects;

/**
 * Everything the server needs to build an item a plugin invented.
 *
 * @param key          the identifier the item is created and saved under
 * @param networkType  the vanilla item the client renders
 * @param maxStackSize how many fit in one slot
 * @param maxDamage    total durability, {@code 0} when the item cannot break
 * @param itemName     the name drawn upright, {@code null} to keep the vanilla one
 * @param itemModel    the model a resource pack draws, {@code null} to use {@link #key()}
 * @param glint        whether the enchantment shimmer is forced on
 * @since 0.1.0
 */
public record ItemDefinition(
        Key key,
        Key networkType,
        int maxStackSize,
        int maxDamage,
        @Nullable Component itemName,
        @Nullable Key itemModel,
        boolean glint) {

    public ItemDefinition {
        Objects.requireNonNull(key, "key");
        Objects.requireNonNull(networkType, "networkType");

        if (maxStackSize < 1) {
            throw new IllegalArgumentException("maxStackSize must be at least 1, got " + maxStackSize);
        }

        if (maxDamage < 0) {
            throw new IllegalArgumentException("maxDamage cannot be negative, got " + maxDamage);
        }

        if (maxStackSize > 1 && maxDamage > 0) {
            throw new IllegalArgumentException(
                    "An item that stacks cannot have durability: " + key + " stacks to " + maxStackSize);
        }
    }

    /**
     * The model the client is told to draw. Defaults to {@link #key()}, which doubles
     * as the marker the server reads the custom identity back from.
     *
     * @return the resource-pack model identifier
     * @since 0.1.0
     */
    public Key resolvedItemModel() {
        return itemModel != null ? itemModel : key;
    }

    /**
     * @param key         the identifier the item is created and saved under
     * @param networkType the vanilla item the client renders
     * @return a builder pre-filled with the defaults of a plain, unstackable tool
     * @since 0.1.0
     */
    public static Builder builder(final Key key, final Key networkType) {
        return new Builder(key, networkType);
    }

    /**
     * Assembles an {@link ItemDefinition}.
     *
     * @since 0.1.0
     */
    public static final class Builder {

        private final Key key;
        private final Key networkType;

        private int maxStackSize = 1;
        private int maxDamage;
        private @Nullable Component itemName;
        private @Nullable Key itemModel;
        private boolean glint;

        private Builder(final Key key, final Key networkType) {
            this.key = key;
            this.networkType = networkType;
        }

        /**
         * @param maxStackSize how many fit in one slot
         * @return this builder
         * @since 0.1.0
         */
        public Builder maxStackSize(final int maxStackSize) {
            this.maxStackSize = maxStackSize;
            return this;
        }

        /**
         * @param maxDamage total durability, {@code 0} when the item cannot break
         * @return this builder
         * @since 0.1.0
         */
        public Builder maxDamage(final int maxDamage) {
            this.maxDamage = maxDamage;
            return this;
        }

        /**
         * @param itemName the name drawn upright
         * @return this builder
         * @since 0.1.0
         */
        public Builder itemName(final Component itemName) {
            this.itemName = itemName;
            return this;
        }

        /**
         * @param itemModel the model a resource pack draws; defaults to the item's own key
         * @return this builder
         * @since 0.1.0
         */
        public Builder itemModel(final Key itemModel) {
            this.itemModel = itemModel;
            return this;
        }

        /**
         * @param glint whether the enchantment shimmer is forced on
         * @return this builder
         * @since 0.1.0
         */
        public Builder glint(final boolean glint) {
            this.glint = glint;
            return this;
        }

        /**
         * @return the assembled definition
         * @since 0.1.0
         */
        public ItemDefinition build() {
            return new ItemDefinition(key, networkType, maxStackSize, maxDamage, itemName, itemModel, glint);
        }
    }
}
