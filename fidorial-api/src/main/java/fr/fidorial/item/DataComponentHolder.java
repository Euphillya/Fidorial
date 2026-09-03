package fr.fidorial.item;

import fr.fidorial.item.component.ItemLore;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import org.jspecify.annotations.Nullable;

import java.util.List;

/**
 * Something that carries a patch over an item's default components.
 *
 * @since 0.1.0
 */
public interface DataComponentHolder {

    /**
     * @return the patch this holder carries
     * @since 0.1.0
     */
    DataComponentMap components();

    /**
     * Reads a component off this holder.
     *
     * @param type the component to read
     * @param <T>  the component's value type
     * @return the value, or {@code null}
     * @since 0.1.0
     */
    default <T> @Nullable T get(final DataComponentType<T> type) {
        return components().get(type);
    }

    /**
     * @param type     the component to read
     * @param fallback returned when the component is unset
     * @param <T>      the component's value type
     * @return the value, or {@code fallback}
     * @since 0.1.0
     */
    default <T> T getOrDefault(final DataComponentType<T> type, final T fallback) {
        return components().getOrDefault(type, fallback);
    }

    /**
     * @param type the component to test
     * @return {@code true} when this holder patches it
     * @since 0.1.0
     */
    default boolean has(final DataComponentType<?> type) {
        return components().has(type);
    }

    /**
     * The name an anvil sets, drawn in italics.
     *
     * @return the player-assigned name, or {@code null}
     * @since 0.1.0
     */
    default @Nullable Component customName() {
        return get(DataComponentTypes.CUSTOM_NAME);
    }

    /**
     * @return the name drawn upright, or {@code null}
     * @since 0.1.0
     */
    default @Nullable Component itemName() {
        return get(DataComponentTypes.ITEM_NAME);
    }

    /**
     * @return the model a resource pack draws, or {@code null}
     * @since 0.1.0
     */
    default @Nullable Key itemModel() {
        return get(DataComponentTypes.ITEM_MODEL);
    }

    /**
     * @return the tooltip lines, {@link ItemLore#EMPTY} when unset
     * @since 0.1.0
     */
    default ItemLore lore() {
        return getOrDefault(DataComponentTypes.LORE, ItemLore.EMPTY);
    }

    /**
     * Shorthand for {@code lore().lines()}.
     *
     * @return the tooltip lines, empty when unset
     * @since 0.1.0
     */
    default List<Component> loreLines() {
        return lore().lines();
    }

    /**
     * @return whether the enchantment shimmer is forced on
     * @since 0.1.0
     */
    default boolean glint() {
        return getOrDefault(DataComponentTypes.ENCHANTMENT_GLINT_OVERRIDE, false);
    }

    /**
     * @return {@code true} when {@link #customName()} is set
     * @since 0.1.0
     */
    default boolean hasCustomName() {
        return has(DataComponentTypes.CUSTOM_NAME);
    }

    /**
     * @return {@code true} when {@link #itemName()} is set
     * @since 0.1.0
     */
    default boolean hasItemName() {
        return has(DataComponentTypes.ITEM_NAME);
    }

    /**
     * @return {@code true} when there is at least one lore line
     * @since 0.1.0
     */
    default boolean hasLore() {
        return !lore().isEmpty();
    }
}
