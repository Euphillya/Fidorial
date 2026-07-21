package fr.fidorial.attribute;

import org.jspecify.annotations.Nullable;

import java.util.Locale;

public enum EquipmentSlotGroup {

    ANY(0, "any"),
    MAIN_HAND(1, "mainhand"),
    OFF_HAND(2, "offhand"),
    HAND(3, "hand"),
    FEET(4, "feet"),
    LEGS(5, "legs"),
    CHEST(6, "chest"),
    HEAD(7, "head"),
    ARMOR(8, "armor"),
    BODY(9, "body");

    private final int networkId;
    private final String serializedName;

    EquipmentSlotGroup(int networkId, String serializedName) {
        this.networkId = networkId;
        this.serializedName = serializedName;
    }

    public int networkId() {
        return networkId;
    }

    public String serializedName() {
        return serializedName;
    }

    public static EquipmentSlotGroup byName(@Nullable String name) {
        if (name == null) {
            return ANY;
        }
        String lower = name.toLowerCase(Locale.ROOT);
        for (EquipmentSlotGroup group : values()) {
            if (group.serializedName.equals(lower)) {
                return group;
            }
        }
        return ANY;
    }
}
