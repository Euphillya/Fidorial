package fr.fidorial.item;

import fr.fidorial.item.component.ItemLore;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * The writing half of the component model: a mutable, chainable editor over one
 * holder's components.
 *
 * @since 0.1.0
 */
public final class DataComponentEditor implements DataComponentHolder {

    private DataComponentMap components;

    private DataComponentEditor(final DataComponentMap components) {
        this.components = Objects.requireNonNull(components, "components");
    }

    /**
     * @return an editor patching nothing
     * @since 0.1.0
     */
    public static DataComponentEditor empty() {
        return new DataComponentEditor(DataComponentMap.EMPTY);
    }

    /**
     * @param components the patch to start from
     * @return an editor over that patch
     * @since 0.1.0
     */
    public static DataComponentEditor of(final DataComponentMap components) {
        return new DataComponentEditor(components);
    }

    @Override
    public DataComponentMap components() {
        return components;
    }

    /**
     * @return an independent editor holding the same components
     * @since 0.1.0
     */
    public DataComponentEditor copy() {
        return new DataComponentEditor(components);
    }

    /**
     * @return {@code true} when this editor patches nothing
     * @since 0.1.0
     */
    public boolean isEmpty() {
        return components.isEmpty();
    }

    /**
     * @param type  the component to set
     * @param value the value
     * @param <T>   the component's value type
     * @return this editor
     * @since 0.1.0
     */
    public <T> DataComponentEditor set(final DataComponentType<T> type, final T value) {
        components = components.with(type, value);
        return this;
    }

    /**
     * @param type  the component to set
     * @param value the value, or {@code null} to stop patching the component
     * @param <T>   the component's value type
     * @return this editor
     * @since 0.1.0
     */
    public <T> DataComponentEditor setOrReset(final DataComponentType<T> type, final @Nullable T value) {
        return value == null ? reset(type) : set(type, value);
    }

    /**
     * Takes the item's own default away, so the component ends up absent from the
     * item rather than back at its default.
     *
     * @param type the component to remove
     * @return this editor
     * @since 0.1.0
     */
    public DataComponentEditor remove(final DataComponentType<?> type) {
        components = components.without(type);
        return this;
    }

    /**
     * Drops this editor's opinion on a component, letting the item's default show
     * through again.
     *
     * @param type the component to stop patching
     * @return this editor
     * @since 0.1.0
     */
    public DataComponentEditor reset(final DataComponentType<?> type) {
        components = components.reset(type);
        return this;
    }

    /**
     * The name an anvil sets, drawn in italics. An item carrying one counts as
     * renamed, which an anvil charges extra for.
     *
     * @param name the name, or {@code null} to clear it
     * @return this editor
     * @since 0.1.0
     */
    public DataComponentEditor customName(final @Nullable Component name) {
        return setOrReset(DataComponentTypes.CUSTOM_NAME, name);
    }

    /**
     * The item's own name, drawn upright. Unlike {@link #customName(Component)} it
     * survives a rename and does not mark the item as user-named, which is what a
     * declared item wants.
     *
     * @param name the name, or {@code null} to clear it
     * @return this editor
     * @since 0.1.0
     */
    public DataComponentEditor itemName(final @Nullable Component name) {
        return setOrReset(DataComponentTypes.ITEM_NAME, name);
    }

    /**
     * @param lore the tooltip lines, or {@code null} to clear them
     * @return this editor
     * @since 0.1.0
     */
    public DataComponentEditor lore(final @Nullable ItemLore lore) {
        return setOrReset(DataComponentTypes.LORE, lore);
    }

    /**
     * @param lines the tooltip lines, in the order they are drawn; {@code null} clears them
     * @return this editor
     * @since 0.1.0
     */
    public DataComponentEditor lore(final @Nullable List<Component> lines) {
        return lore(lines == null ? null : ItemLore.of(lines));
    }

    /**
     * @param lines the tooltip lines, in the order they are drawn
     * @return this editor
     * @since 0.1.0
     */
    public DataComponentEditor lore(final Component... lines) {
        return lore(ItemLore.of(lines));
    }

    /**
     * @param lines the tooltip lines to append to whatever is already there
     * @return this editor
     * @since 0.1.0
     */
    public DataComponentEditor addLore(final Component... lines) {
        final List<Component> merged = new ArrayList<>(lore().lines());
        merged.addAll(List.of(lines));
        return lore(ItemLore.of(merged));
    }

    /**
     * @param itemModel the model a resource pack draws, or {@code null} to clear it
     * @return this editor
     * @since 0.1.0
     */
    public DataComponentEditor itemModel(final @Nullable Key itemModel) {
        return setOrReset(DataComponentTypes.ITEM_MODEL, itemModel);
    }

    /**
     * @return durability already spent; {@code 0} is pristine
     * @since 0.1.0
     */
    public int damage() {
        return getOrDefault(DataComponentTypes.DAMAGE, 0);
    }

    /**
     * @param damage the spent durability
     * @return this editor
     * @since 0.1.0
     */
    public DataComponentEditor damage(final int damage) {
        return set(DataComponentTypes.DAMAGE, damage);
    }

    /**
     * @param maxDamage the total durability, or {@code null} to fall back to the item's own
     * @return this editor
     * @since 0.1.0
     */
    public DataComponentEditor maxDamage(final @Nullable Integer maxDamage) {
        return setOrReset(DataComponentTypes.MAX_DAMAGE, maxDamage);
    }

    /**
     * @param maxStackSize how many fit in one slot, or {@code null} to fall back to
     *                     the item's own
     * @return this editor
     * @since 0.1.0
     */
    public DataComponentEditor maxStackSize(final @Nullable Integer maxStackSize) {
        return setOrReset(DataComponentTypes.MAX_STACK_SIZE, maxStackSize);
    }

    /**
     * @param glint {@code true} forces the enchantment shimmer on, {@code false}
     *              forces it <em>off</em> even on an enchanted item, {@code null}
     *              leaves the client to work it out
     * @return this editor
     * @since 0.1.0
     */
    public DataComponentEditor glint(final @Nullable Boolean glint) {
        return setOrReset(DataComponentTypes.ENCHANTMENT_GLINT_OVERRIDE, glint);
    }

    @Override
    public boolean equals(final Object o) {
        return this == o || (o instanceof final DataComponentEditor other && components.equals(other.components));
    }

    @Override
    public int hashCode() {
        return components.hashCode();
    }

    @Override
    public String toString() {
        return "DataComponentEditor{" + components + "}";
    }
}
