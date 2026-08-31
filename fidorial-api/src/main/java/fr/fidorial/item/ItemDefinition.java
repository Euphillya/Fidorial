package fr.fidorial.item;

import fr.fidorial.item.component.ItemLore;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;

import java.util.List;
import java.util.Objects;

/**
 * Everything the server needs to build an item a plugin invented.
 *
 * @param key         the identifier the item is created and saved under
 * @param networkType the vanilla item the client renders
 * @param components  the components every stack of this item starts with
 * @since 0.1.0
 */
public record ItemDefinition(Key key, Key networkType, DataComponentMap components)
        implements DataComponentHolder {

    /**
     * What an item stacks to when it says nothing about it.
     */
    public static final int DEFAULT_MAX_STACK_SIZE = 1;

    public ItemDefinition {
        Objects.requireNonNull(key, "key");
        Objects.requireNonNull(networkType, "networkType");
        Objects.requireNonNull(components, "components");

        final int stackSize = components.getOrDefault(DataComponentTypes.MAX_STACK_SIZE, DEFAULT_MAX_STACK_SIZE);
        final int damage = components.getOrDefault(DataComponentTypes.MAX_DAMAGE, 0);

        if (stackSize < 1) {
            throw new IllegalArgumentException("maxStackSize must be at least 1, got " + stackSize);
        }

        if (damage < 0) {
            throw new IllegalArgumentException("maxDamage cannot be negative, got " + damage);
        }

        if (stackSize > 1 && damage > 0) {
            throw new IllegalArgumentException(
                    "An item that stacks cannot have durability: " + key + " stacks to " + stackSize);
        }
    }

    /**
     * @return how many fit in one slot
     * @since 0.1.0
     */
    public int maxStackSize() {
        return components.getOrDefault(DataComponentTypes.MAX_STACK_SIZE, DEFAULT_MAX_STACK_SIZE);
    }

    /**
     * @return total durability, {@code 0} when the item cannot break
     * @since 0.1.0
     */
    public int maxDamage() {
        return components.getOrDefault(DataComponentTypes.MAX_DAMAGE, 0);
    }

    /**
     * The model the client is told to draw. {@link Builder} seeds this with
     * {@link #key()}, which doubles as the marker the server reads the custom
     * identity back from.
     *
     * @return the resource-pack model identifier
     * @since 0.1.0
     */
    public Key resolvedItemModel() {
        final Key model = itemModel();
        return model != null ? model : key;
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
     * @return a builder pre-populated with this definition
     * @since 0.1.0
     */
    public Builder toBuilder() {
        return new Builder(key, networkType, components.toBuilder());
    }

    /**
     * Assembles an {@link ItemDefinition}.
     *
     * @since 0.1.0
     */
    public static final class Builder {

        private final Key key;
        private final Key networkType;
        private final DataComponentMap.Builder components;

        private Builder(final Key key, final Key networkType) {
            this(key, networkType, DataComponentMap.builder()
                    .set(DataComponentTypes.MAX_STACK_SIZE, DEFAULT_MAX_STACK_SIZE)
                    .set(DataComponentTypes.ITEM_MODEL, key));
        }

        private Builder(final Key key, final Key networkType, final DataComponentMap.Builder components) {
            this.key = Objects.requireNonNull(key, "key");
            this.networkType = Objects.requireNonNull(networkType, "networkType");
            this.components = components;
        }

        /**
         * @param type  the component to set
         * @param value the value
         * @param <T>   the component's value type
         * @return this builder
         * @since 0.1.0
         */
        public <T> Builder set(final DataComponentType<T> type, final T value) {
            components.set(type, value);
            return this;
        }

        /**
         * @param type the component to stop declaring
         * @return this builder
         * @since 0.1.0
         */
        public Builder reset(final DataComponentType<?> type) {
            components.reset(type);
            return this;
        }

        /**
         * @param maxStackSize how many fit in one slot
         * @return this builder
         * @since 0.1.0
         */
        public Builder maxStackSize(final int maxStackSize) {
            return set(DataComponentTypes.MAX_STACK_SIZE, maxStackSize);
        }

        /**
         * @param maxDamage total durability, {@code 0} when the item cannot break
         * @return this builder
         * @since 0.1.0
         */
        public Builder maxDamage(final int maxDamage) {
            return maxDamage == 0
                    ? reset(DataComponentTypes.MAX_DAMAGE)
                    : set(DataComponentTypes.MAX_DAMAGE, maxDamage);
        }

        /**
         * @param itemName the name drawn upright
         * @return this builder
         * @since 0.1.0
         */
        public Builder itemName(final Component itemName) {
            return set(DataComponentTypes.ITEM_NAME, itemName);
        }

        /**
         * @param itemModel the model a resource pack draws; defaults to the item's own key
         * @return this builder
         * @since 0.1.0
         */
        public Builder itemModel(final Key itemModel) {
            return set(DataComponentTypes.ITEM_MODEL, itemModel);
        }

        /**
         * @param lines the tooltip lines every stack of this item starts with
         * @return this builder
         * @since 0.1.0
         */
        public Builder lore(final List<Component> lines) {
            final ItemLore lore = ItemLore.of(lines);
            return lore.isEmpty() ? reset(DataComponentTypes.LORE) : set(DataComponentTypes.LORE, lore);
        }

        /**
         * @param lines the tooltip lines every stack of this item starts with
         * @return this builder
         * @since 0.1.0
         */
        public Builder lore(final Component... lines) {
            return lore(List.of(lines));
        }

        /**
         * @param glint whether the enchantment shimmer is forced on; {@code false}
         *              forces it <em>off</em>, even on an enchanted item
         * @return this builder
         * @since 0.1.0
         */
        public Builder glint(final boolean glint) {
            return set(DataComponentTypes.ENCHANTMENT_GLINT_OVERRIDE, glint);
        }

        /**
         * @return the assembled definition
         * @since 0.1.0
         */
        public ItemDefinition build() {
            return new ItemDefinition(key, networkType, components.build());
        }
    }
}
