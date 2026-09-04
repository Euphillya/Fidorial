package fr.fidorial.attribute;

import fr.fidorial.inventory.EquipmentSlotGroup;
import net.kyori.adventure.key.Key;
import org.jspecify.annotations.Nullable;

import java.util.Locale;
import java.util.Objects;

/**
 * One change to one attribute, as an item carries it.
 *
 * @param attribute the attribute changed, e.g. {@link Attribute#ATTACK_DAMAGE}
 * @param id        identifies this modifier; must be unique among the modifiers of
 *                  the same attribute on the same item
 * @param amount    the value {@code operation} works with
 * @param operation how {@code amount} is folded into the attribute
 * @param slot      where the item must sit for this to apply
 * @param display   how this modifier is drawn in the tooltip
 */
public record AttributeModifier(Key attribute, Key id, double amount, Operation operation, EquipmentSlotGroup slot,
                                AttributeModifierDisplay display) {

    public AttributeModifier {
        Objects.requireNonNull(attribute, "attribute");
        Objects.requireNonNull(id, "id");
        Objects.requireNonNull(operation, "operation");
        Objects.requireNonNull(slot, "slot");
        Objects.requireNonNull(display, "display");
    }

    public static AttributeModifier of(
            final Key attribute,
            final Key id,
            final double amount,
            final Operation operation,
            final EquipmentSlotGroup slot
    ) {
        return new AttributeModifier(attribute, id, amount, operation, slot);
    }

    /**
     * @param attribute the attribute changed
     * @param id        identifies this modifier
     * @param amount    the value {@code operation} works with
     * @param operation how {@code amount} is folded into the attribute
     * @param slot      where the item must sit for this to apply
     */
    public AttributeModifier(
            final Key attribute,
            final Key id,
            final double amount,
            final Operation operation,
            final EquipmentSlotGroup slot
    ) {
        this(attribute, id, amount, operation, slot, AttributeModifierDisplay.DEFAULT);
    }

    public static AttributeModifier of(final Key attribute, final Key id, final double amount, final Operation operation) {
        return new AttributeModifier(attribute, id, amount, operation, EquipmentSlotGroup.ANY);
    }

    /**
     * @param display how this modifier is drawn in the tooltip
     * @return a copy displayed that way
     * @since 0.1.0
     */
    public AttributeModifier withDisplay(final AttributeModifierDisplay display) {
        return new AttributeModifier(attribute, id, amount, operation, slot, display);
    }

    /**
     * Applies but is not drawn. The tooltip keeps whatever other modifiers say.
     *
     * @return a copy drawn nowhere
     * @since 0.1.0
     */
    public AttributeModifier hidden() {
        return withDisplay(AttributeModifierDisplay.HIDDEN);
    }

    public enum Operation {
        /**
         * Adds all of the modifiers' amounts to the base attribute.
         */
        ADD_VALUE(0, "add_value"),
        /**
         * Multiplies the base attribute by (1 + sum of modifiers' amounts).
         */
        ADD_MULTIPLIED_BASE(1, "add_multiplied_base"),
        /**
         * Multiplies the base attribute by (1 + modifiers' amounts) for every modifier.
         */
        ADD_MULTIPLIED_TOTAL(2, "add_multiplied_total");

        private final int networkId;
        private final String serializedName;

        Operation(final int networkId, final String serializedName) {
            this.networkId = networkId;
            this.serializedName = serializedName;
        }

        public int networkId() {
            return networkId;
        }

        public String serializedName() {
            return serializedName;
        }

        public static Operation byName(@Nullable final String name) {
            if (name == null) {
                return ADD_VALUE;
            }
            final String lower = name.toLowerCase(Locale.ROOT);
            for (final Operation operation : values()) {
                if (operation.serializedName.equals(lower)) {
                    return operation;
                }
            }
            return ADD_VALUE;
        }
    }
}
