package fr.fidorial.item;

import fr.fidorial.attribute.AttributeModifier;
import fr.fidorial.inventory.EquipmentSlotGroup;
import fr.fidorial.item.component.AttackRange;
import fr.fidorial.item.component.ItemAttributeModifiers;
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
     * How far this item reaches when swung.
     *
     * <p>{@code null} rather than {@link AttackRange#DEFAULT} when unset, because
     * the two differ: an item with no component has no hitbox margin, while
     * {@link AttackRange#DEFAULT} carries
     * {@value AttackRange#DEFAULT_HITBOX_MARGIN}.
     *
     * @return the reach, or {@code null}
     * @since 0.1.0
     */
    default @Nullable AttackRange attackRange() {
        return get(DataComponentTypes.ATTACK_RANGE);
    }

    /**
     * @return {@code true} when {@link #attackRange()} is set
     * @since 0.1.0
     */
    default boolean hasAttackRange() {
        return has(DataComponentTypes.ATTACK_RANGE);
    }

    /**
     * @param creative whether the attacker is a player in creative mode
     * @return the furthest a target may be and still be hit, falling back to
     *         {@value AttackRange#DEFAULT_MAX_REACH} when the component is unset
     * @since 0.1.0
     */
    default float maxReach(final boolean creative) {
        final AttackRange range = attackRange();
        return range != null ? range.maxReachFor(creative) : AttackRange.DEFAULT_MAX_REACH;
    }

    /**
     * @return how far outside its hitbox a target can still be hit; {@code 0} when
     *         the component is unset
     * @since 0.1.0
     */
    default float hitboxMargin() {
        final AttackRange range = attackRange();
        return range != null ? range.hitboxMargin() : 0.0F;
    }

    /**
     * The attribute modifiers this holder sets.
     *
     * <p>{@link ItemAttributeModifiers#EMPTY} both when the component is unset and
     * when it is set to nothing, which are different things: the first leaves an
     * item's built-in modifiers alone, the second strips them. Use
     * {@link #hasAttributeModifiers()} to tell them apart.
     *
     * @return the modifiers, {@link ItemAttributeModifiers#EMPTY} when unset
     * @since 0.1.0
     */
    default ItemAttributeModifiers attributeModifiers() {
        return getOrDefault(DataComponentTypes.ATTRIBUTE_MODIFIERS, ItemAttributeModifiers.EMPTY);
    }

    /**
     * @return {@code true} when this holder sets the component at all, including to
     *         no modifiers
     * @since 0.1.0
     */
    default boolean hasAttributeModifiers() {
        return has(DataComponentTypes.ATTRIBUTE_MODIFIERS);
    }

    /**
     * @param slot the slot the item is sitting in
     * @return the modifiers that apply there, in order
     * @since 0.1.0
     */
    default List<AttributeModifier> attributeModifiers(final EquipmentSlotGroup slot) {
        return attributeModifiers().forSlot(slot);
    }

    /**
     * @return durability already spent; {@code 0} is pristine
     * @since 0.1.0
     */
    default int damage() {
        return getOrDefault(DataComponentTypes.DAMAGE, 0);
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
