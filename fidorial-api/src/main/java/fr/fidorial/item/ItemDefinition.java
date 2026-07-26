package fr.fidorial.item;

import net.kyori.adventure.key.Key;

import java.util.Objects;

/**
 * Represents the definition of an item within the system, detailing its characteristics
 * such as a unique identifier, stack size limit, durability, and behavior.
 * This class provides core metadata about items and performs validation to enforce
 * consistency with item rules.
 *
 * @param id            The unique identifier associated with the item. Cannot be null.
 * @param maxStackSize  The maximum number of items that can be stacked together. Must
 *                      be between 1 and 99 inclusive. Damageable items must have a stack size of 1.
 * @param maxDurability The maximum durability of the item. Non-negative values represent
 *                      the item can take damage, while 0 indicates the item is not damageable.
 * @param behavior      The behavior of the item, defining custom interactions such as
 *                      usage and crafting. Cannot be null.
 *
 * @since 0.1.0
 */
public record ItemDefinition(Key id, int maxStackSize, int maxDurability, ItemBehavior behavior) {

    public ItemDefinition(final Key id, final int maxStackSize, final int maxDurability, final ItemBehavior behavior) {

        this.id = Objects.requireNonNull(id, "id");
        this.behavior = Objects.requireNonNull(behavior, "behavior");

        if(maxStackSize < 1 || maxStackSize > 99) {
            throw new IllegalArgumentException("maxStackSize must be between 1 and 99");
        }

        if(maxDurability < 0) {
            throw new IllegalArgumentException("maxDurability cannot be negative");
        }

        if(maxDurability > 0 && maxStackSize != 1) {
            throw new IllegalArgumentException("Damageable items must have a stack size of 1");
        }

        this.maxStackSize = maxStackSize;
        this.maxDurability = maxDurability;
    }

    public boolean isDamageable() {

        return maxDurability > 0;
    }
}