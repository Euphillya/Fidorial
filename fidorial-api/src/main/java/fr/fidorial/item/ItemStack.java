package fr.fidorial.item;

import fr.fidorial.attribute.AttributeModifier;
import fr.fidorial.item.component.ItemLore;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.event.HoverEventSource;
import net.kyori.adventure.translation.Translatable;
import org.jspecify.annotations.Nullable;

import java.util.List;
import java.util.Objects;
import java.util.function.UnaryOperator;

/**
 * An item identifier, a count, and a patch over that item's default components.
 *
 * @param id         the item identifier, e.g. {@code minecraft:diamond_sword}
 * @param count      how many; {@code 0} or less means {@linkplain #isEmpty() empty}
 * @param components the patch over the item's defaults
 * @since 0.1.0
 */
public record ItemStack(Key id, int count, DataComponentMap components)
        implements DataComponentHolder, Translatable, HoverEventSource<HoverEvent.ShowItem> {

    private static final Key AIR = Key.key("air");

    /**
     * The absence of an item. Slots hold this rather than {@code null}.
     */
    public static final ItemStack EMPTY = new ItemStack(AIR, 0, DataComponentMap.EMPTY);

    public ItemStack {
        Objects.requireNonNull(id, "id");
        Objects.requireNonNull(components, "components");
    }

    /**
     * @param id    the item identifier
     * @param count how many
     */
    public ItemStack(final Key id, final int count) {
        this(id, count, DataComponentMap.EMPTY);
    }

    /**
     * @param key   the item identifier
     * @param count how many
     * @return a stack with no components patched
     */
    public static ItemStack of(final Key key, final int count) {
        return new ItemStack(key, count);
    }

    /**
     * @param key the item identifier
     * @return a stack of one, with no components patched
     */
    public static ItemStack of(final Key key) {
        return new ItemStack(key, 1);
    }

    /**
     * @param key the item identifier
     * @return a builder for a stack of one
     */
    public static Builder builder(final Key key) {
        return new Builder(key, 1, DataComponentMap.builder());
    }

    /**
     * @return a builder pre-populated with this stack
     */
    public Builder toBuilder() {
        return new Builder(id, count, components.toBuilder());
    }

    /**
     * @param type  the component to set
     * @param value the value
     * @param <T>   the component's value type
     * @return a new stack
     */
    public <T> ItemStack with(final DataComponentType<T> type, final T value) {
        return new ItemStack(id, count, components.with(type, value));
    }

    /**
     * Removes the item's default for a component. See
     * {@link DataComponentMap#without(DataComponentType)} for why this differs from
     * {@link #reset(DataComponentType)}.
     *
     * @param type the component to remove
     * @return a new stack
     */
    public ItemStack without(final DataComponentType<?> type) {
        return new ItemStack(id, count, components.without(type));
    }

    /**
     * @param type the component to stop patching
     * @return a new stack
     */
    public ItemStack reset(final DataComponentType<?> type) {
        return new ItemStack(id, count, components.reset(type));
    }

    /**
     * @return {@code true} when this stack holds nothing
     */
    public boolean isEmpty() {
        return count <= 0 || id.equals(AIR);
    }

    /**
     * Whether two stacks would merge into one slot — same item, same components,
     * count ignored.
     *
     * @param other the stack to compare against, may be {@code null}
     * @return {@code true} when the two are stackable together
     */
    public boolean isSimilar(final @Nullable ItemStack other) {
        if (other == null) {
            return false;
        }
        if (isEmpty() || other.isEmpty()) {
            return isEmpty() && other.isEmpty();
        }
        return id.equals(other.id) && components.equals(other.components);
    }

    /**
     * @return how many of this item fit in a slot, honouring a
     * {@code max_stack_size} override and falling back to the item's own limit
     */
    public int maxStackSize() {
        final Integer override = get(DataComponentTypes.MAX_STACK_SIZE);
        return override != null ? override : ItemDefaults.maxStackSize(id);
    }

    /**
     * @return total durability, honouring a {@code max_damage} override and falling
     * back to the item's own; {@code 0} when the item cannot break
     */
    public int maxDamage() {
        final Integer override = get(DataComponentTypes.MAX_DAMAGE);
        return override != null ? override : ItemDefaults.maxDamage(id);
    }

    /**
     * @return durability already spent; {@code 0} is pristine
     */
    public int damage() {
        return getOrDefault(DataComponentTypes.DAMAGE, 0);
    }

    /**
     * @return durability left before the item breaks
     */
    public int remainingDurability() {
        return Math.max(0, maxDamage() - damage());
    }

    /**
     * @return {@code true} when the item has durability and is not marked unbreakable
     */
    public boolean isDamageable() {
        return maxDamage() > 0 && !isUnbreakable();
    }

    /**
     * @return {@code true} when the item carries {@code minecraft:unbreakable}
     */
    public boolean isUnbreakable() {
        return false; //return has(DataComponentTypes.UNBREAKABLE);
    }

    /**
     * @param damage the new spent durability, clamped to {@code [0, maxDamage]}
     * @return a new stack
     */
    public ItemStack withDamage(final int damage) {
        return with(DataComponentTypes.DAMAGE, Math.clamp(damage, 0, Math.max(0, maxDamage())));
    }

    /**
     * Spends durability. Does nothing on an item that cannot break.
     *
     * @param amount how much durability to spend
     * @return a new stack, or this one when the item is not damageable
     */
    public ItemStack damaged(final int amount) {
        return isDamageable() ? withDamage(damage() + amount) : this;
    }

    /**
     * @return {@code true} when spending one more durability would destroy the item
     */
    public boolean willBreak() {
        return isDamageable() && damage() >= maxDamage() - 1;
    }

    /**
     * @return the anvil-style name, or {@code null}
     */
    public @Nullable Component customName() {
        return get(DataComponentTypes.CUSTOM_NAME);
    }

    /**
     * Shorthand for {@code lore().lines()}.
     *
     * @return the tooltip lines, empty when unset
     */
    public List<Component> loreLines() {
        return lore().lines();
    }

    /**
     * @return the attribute modifiers this stack sets, empty when unset
     */
    public List<AttributeModifier> attributeModifiers() {
        return List.of();
    }

    /**
     * @return the applied enchantments, empty when unset
     */
    public Object enchantments() {
        return null;
    }

    /**
     * @param enchantment the enchantment key
     * @return its level, or {@code 0}
     */
    public int enchantmentLevel(final Key enchantment) {
        return 0;
    }

    /**
     * @return {@code true} when {@link #customName()} is set
     */
    public boolean hasCustomName() {
        return has(DataComponentTypes.CUSTOM_NAME);
    }

    /**
     * @return {@code true} when this stack sets attribute modifiers
     */
    public boolean hasAttributeModifiers() {
        return !attributeModifiers().isEmpty();
    }

    /**
     * The name to show for this stack: the custom name if any, then the item name,
     * then the item's own translated name.
     *
     * @return the display name
     */
    public Component displayName() {
        final Component custom = customName();
        if (custom != null) {
            return custom;
        }
        final Component item = itemName();
        return item != null ? item : Component.translatable(this);
    }

    /**
     * @param newCount the new count
     * @return a new stack, or this one when the count is unchanged
     */
    public ItemStack withCount(final int newCount) {
        return newCount == count ? this : new ItemStack(id, newCount, components);
    }

    /**
     * @param delta how much to add to the count; may be negative
     * @return a new stack, clamped at {@code 0}
     */
    public ItemStack plus(final int delta) {
        return withCount(Math.max(0, count + delta));
    }

    /**
     * @param name the anvil-style name
     * @return a new stack
     */
    public ItemStack withCustomName(final Component name) {
        return with(DataComponentTypes.CUSTOM_NAME, name);
    }

    /**
     * @param name the intrinsic name
     * @return a new stack
     */
    public ItemStack withItemName(final Component name) {
        return with(DataComponentTypes.ITEM_NAME, name);
    }

    /**
     * @param lines the tooltip lines, in the order they are drawn
     * @return a new stack
     */
    public ItemStack withLore(final List<Component> lines) {
        return with(DataComponentTypes.LORE, ItemLore.of(lines));
    }

    /**
     * @param lines the tooltip lines, in the order they are drawn
     * @return a new stack
     */
    public ItemStack withLore(final Component... lines) {
        return with(DataComponentTypes.LORE, ItemLore.of(lines));
    }

    @Override
    public HoverEvent<HoverEvent.ShowItem> asHoverEvent(final UnaryOperator<HoverEvent.ShowItem> op) {
        return HoverEvent.showItem(op.apply(HoverEvent.ShowItem.showItem(id, count)));
    }

    @Override
    public String translationKey() {
        return "item." + id.asString().replace(":", ".");
    }

    @Override
    public String toString() {
        return "ItemStack{" + id.asString() + " x" + count
                + (components.isEmpty() ? "" : ", " + components)
                + "}";
    }

    /**
     * Builds a stack a component at a time.
     *
     * <p>The builder is mutable and is not safe to share between threads; the
     * {@link ItemStack} it produces is.
     *
     * @since 0.1.0
     */
    public static final class Builder {

        private final Key id;
        private final DataComponentMap.Builder components;
        private int count;

        private Builder(final Key id, final int count, final DataComponentMap.Builder components) {
            this.id = Objects.requireNonNull(id, "id");
            this.count = count;
            this.components = components;
        }

        /**
         * @param newCount how many
         * @return this builder
         */
        public Builder count(final int newCount) {
            this.count = newCount;
            return this;
        }

        /**
         * @param type  the component to set
         * @param value the value
         * @param <T>   the component's value type
         * @return this builder
         */
        public <T> Builder set(final DataComponentType<T> type, final T value) {
            components.set(type, value);
            return this;
        }

        /**
         * @param type the component whose default to remove
         * @return this builder
         */
        public Builder remove(final DataComponentType<?> type) {
            components.remove(type);
            return this;
        }

        /**
         * @param name the anvil-style name
         * @return this builder
         */
        public Builder customName(final Component name) {
            return set(DataComponentTypes.CUSTOM_NAME, name);
        }

        /**
         * @param name the intrinsic name
         * @return this builder
         */
        public Builder itemName(final Component name) {
            return set(DataComponentTypes.ITEM_NAME, name);
        }

        /**
         * @param lines the tooltip lines, in the order they are drawn
         * @return this builder
         */
        public Builder lore(final List<Component> lines) {
            return set(DataComponentTypes.LORE, ItemLore.of(lines));
        }

        /**
         * @param lines the tooltip lines, in the order they are drawn
         * @return this builder
         */
        public Builder lore(final Component... lines) {
            return set(DataComponentTypes.LORE, ItemLore.of(lines));
        }

        /**
         * @param damage spent durability
         * @return this builder
         */
        public Builder damage(final int damage) {
            return set(DataComponentTypes.DAMAGE, damage);
        }

        /**
         * @param glint {@code true} to force the enchantment shimmer on
         * @return this builder
         */
        public Builder glint(final boolean glint) {
            return set(DataComponentTypes.ENCHANTMENT_GLINT_OVERRIDE, glint);
        }

        /**
         * @return the built stack
         */
        public ItemStack build() {
            return new ItemStack(id, count, components.build());
        }

        public Builder unbreakable() {
            return this; // Todo set
        }
    }
}
