package fr.euphyllia.fidorial.server.item;

import fr.fidorial.entity.Player;
import fr.fidorial.inventory.Hand;
import fr.fidorial.inventory.ItemStack;
import fr.fidorial.item.ItemContext;
import fr.fidorial.world.World;

import java.util.Objects;

/**
 * Represents the context in which a player is using an item.
 * This class provides access to critical details such as the world, the item stack,
 * the player interacting with the item, and the hand being used.
 *
 * @param world  the world where the item interaction is taking place
 * @param stack  the item stack being used in the interaction
 * @param player the player who is using the item
 * @param hand   the hand the player is using for the interaction
 *
 * @since 0.1.0
 */
public record ItemUseContext(World world, ItemStack stack, Player player, Hand hand) implements ItemContext {

    public ItemUseContext {
        Objects.requireNonNull(world, "world");
        Objects.requireNonNull(stack, "stack");
        Objects.requireNonNull(player, "player");
        Objects.requireNonNull(hand, "hand");
    }

}