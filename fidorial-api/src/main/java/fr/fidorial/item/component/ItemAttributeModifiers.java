package fr.fidorial.item.component;

import fr.fidorial.attribute.AttributeModifier;
import fr.fidorial.inventory.EquipmentSlotGroup;
import net.kyori.adventure.key.Key;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * The attribute modifiers a stack carries, under {@code minecraft:attribute_modifiers}.
 *
 * @param modifiers the modifiers, in the order they are drawn
 * @since 0.1.0
 */
public record ItemAttributeModifiers(List<AttributeModifier> modifiers) {

    /**
     * No modifiers at all. On an item that has built-in ones, this is what strips
     * them.
     */
    public static final ItemAttributeModifiers EMPTY = new ItemAttributeModifiers(List.of());

    public ItemAttributeModifiers {
        Objects.requireNonNull(modifiers, "modifiers");
        modifiers = List.copyOf(modifiers);
    }

    /**
     * @param modifiers the modifiers, in the order they are drawn
     * @return those modifiers
     * @since 0.1.0
     */
    public static ItemAttributeModifiers of(final AttributeModifier... modifiers) {
        return new ItemAttributeModifiers(List.of(modifiers));
    }

    /**
     * @param modifiers the modifiers, in the order they are drawn
     * @return those modifiers
     * @since 0.1.0
     */
    public static ItemAttributeModifiers of(final List<AttributeModifier> modifiers) {
        return new ItemAttributeModifiers(modifiers);
    }

    /**
     * @param modifier the modifier to append
     * @return a new set, one modifier longer
     * @since 0.1.0
     */
    public ItemAttributeModifiers plus(final AttributeModifier modifier) {
        Objects.requireNonNull(modifier, "modifier");

        final List<AttributeModifier> copy = new ArrayList<>(modifiers);
        copy.add(modifier);

        return new ItemAttributeModifiers(copy);
    }

    /**
     * @param attribute the attribute to filter on
     * @return the modifiers acting on it, in order
     * @since 0.1.0
     */
    public List<AttributeModifier> forAttribute(final Key attribute) {
        Objects.requireNonNull(attribute, "attribute");

        final List<AttributeModifier> matching = new ArrayList<>();
        for (final AttributeModifier modifier : modifiers) {
            if (modifier.attribute().equals(attribute)) {
                matching.add(modifier);
            }
        }
        return List.copyOf(matching);
    }

    /**
     * Modifiers whose slot is {@link EquipmentSlotGroup#ANY} match every slot, so
     * they come back for any argument.
     *
     * @param slot the slot the item is sitting in
     * @return the modifiers that apply there, in order
     * @since 0.1.0
     */
    public List<AttributeModifier> forSlot(final EquipmentSlotGroup slot) {
        Objects.requireNonNull(slot, "slot");

        final List<AttributeModifier> matching = new ArrayList<>();
        for (final AttributeModifier modifier : modifiers) {
            if (modifier.slot() == slot || modifier.slot() == EquipmentSlotGroup.ANY) {
                matching.add(modifier);
            }
        }
        return List.copyOf(matching);
    }

    /**
     * @return {@code true} when this carries no modifier
     * @since 0.1.0
     */
    public boolean isEmpty() {
        return modifiers.isEmpty();
    }

    /**
     * @return how many modifiers this carries
     * @since 0.1.0
     */
    public int size() {
        return modifiers.size();
    }
}
