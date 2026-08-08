package fr.fidorial.item;


import fr.fidorial.inventory.ItemStack;
import fr.fidorial.world.World;

/**
 * Represents the context in which an item is being used or interacted with.
 * This interface provides access to the world and the item stack involved in
 * the interaction.
 *
 * @since 0.1.0
 */
public interface ItemContext {

    /**
     * Provides access to the {@link World} in which the current context is operating.
     *
     * @return the world associated with this context
     */
    World world();

    /**
     * Provides access to the {@link ItemStack} associated with the current context.
     * The {@link ItemStack} may represent the item being used, held, or interacted with.
     *
     * @return the {@link ItemStack} associated with this context
     */
    ItemStack stack();
}