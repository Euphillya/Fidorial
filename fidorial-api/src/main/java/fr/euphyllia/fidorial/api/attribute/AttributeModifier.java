package fr.euphyllia.fidorial.api.attribute;

import fr.euphyllia.fidorial.api.registry.Key;

import java.util.Locale;
import java.util.Objects;

public record AttributeModifier(
        Key attribute, Key id, double amount, Operation operation, EquipmentSlotGroup slot) {

    public AttributeModifier {
        Objects.requireNonNull(attribute, "attribute");
        Objects.requireNonNull(id, "id");
        Objects.requireNonNull(operation, "operation");
        Objects.requireNonNull(slot, "slot");
    }

    public static AttributeModifier of(Key attribute, Key id, double amount,
                                       Operation operation, EquipmentSlotGroup slot) {
        return new AttributeModifier(attribute, id, amount, operation, slot);
    }

    public static AttributeModifier of(Key attribute, Key id, double amount, Operation operation) {
        return new AttributeModifier(attribute, id, amount, operation, EquipmentSlotGroup.ANY);
    }


    public enum Operation {

        ADD_VALUE(0, "add_value"),
        ADD_MULTIPLIED_BASE(1, "add_multiplied_base"),
        ADD_MULTIPLIED_TOTAL(2, "add_multiplied_total");

        private final int networkId;
        private final String serializedName;

        Operation(int networkId, String serializedName) {
            this.networkId = networkId;
            this.serializedName = serializedName;
        }

        public int networkId() {
            return networkId;
        }

        public String serializedName() {
            return serializedName;
        }

        public static Operation byName(String name) {
            if (name == null) {
                return ADD_VALUE;
            }
            String lower = name.toLowerCase(Locale.ROOT);
            for (Operation operation : values()) {
                if (operation.serializedName.equals(lower)) {
                    return operation;
                }
            }
            return ADD_VALUE;
        }
    }
}
