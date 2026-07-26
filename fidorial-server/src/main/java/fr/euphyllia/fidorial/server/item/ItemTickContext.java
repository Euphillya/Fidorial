package fr.euphyllia.fidorial.server.item;

import fr.fidorial.entity.Entity;
import fr.fidorial.inventory.ItemStack;
import fr.fidorial.item.ItemContext;
import fr.fidorial.world.World;

import java.util.Objects;

/**
 * Represents the context in which an item is ticking or being updated.
 * This class provides details about the world, item stack, the entity
 * holding the item, the slot in which the item resides, and whether the
 * item is currently selected.
 *
 * It is an immutable record that implements the {@link ItemContext} interface.
 *
 * @param world    The world in which the context is operating. Must not be null.
 * @param stack    The {@link ItemStack} being updated. Must not be null.
 * @param holder   The {@link Entity} holding the item. Must not be null.
 * @param slot     The inventory slot occupied by the item. Cannot be negative.
 * @param selected Whether the item is currently selected in the holder's inventory.
 *
 * @since 0.1.0
 */
public record ItemTickContext(World world,
                              ItemStack stack,
                              Entity holder,
                              int slot,
                              boolean selected) implements ItemContext {

    public ItemTickContext {

        Objects.requireNonNull(world, "world");
        Objects.requireNonNull(stack, "stack");
        Objects.requireNonNull(holder, "holder");

        if(slot < 0) {
            throw new IllegalArgumentException("slot cannot be negative");
        }
    }
}