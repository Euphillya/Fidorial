package fr.euphyllia.fidorial.server.item;

import fr.fidorial.entity.Player;
import fr.fidorial.inventory.ItemStack;
import fr.fidorial.item.ItemContext;
import fr.fidorial.world.World;

import java.util.Objects;

/**
 * Represents the context in which an item is being destroyed.
 * This record provides access to the {@link World}, {@link ItemStack},
 * and {@link Player} involved in the destruction process.
 *
 * @param world the world where the item destruction is occurring; cannot be null
 * @param stack the item stack being destroyed; cannot be null
 * @param player the player responsible for or involved in the destruction; cannot be null
 *
 * @since 0.1.0
 */
public record ItemDestroyContext(World world,
                                 ItemStack stack,
                                 Player player) implements ItemContext {

    public ItemDestroyContext {
        Objects.requireNonNull(world, "world");
        Objects.requireNonNull(stack, "stack");
        Objects.requireNonNull(player, "player");
    }
}