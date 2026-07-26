package fr.euphyllia.fidorial.server.item;

import fr.euphyllia.fidorial.server.world.chunk.BlockState;
import fr.fidorial.entity.Player;
import fr.fidorial.inventory.Hand;
import fr.fidorial.inventory.ItemStack;
import fr.fidorial.item.ItemContext;
import fr.fidorial.world.BlockFace;
import fr.fidorial.world.BlockPos;
import fr.fidorial.world.World;

import java.util.Objects;

/**
 * Represents the context in which a block is being interacted with or used.
 * This interface provides additional details about the interaction, allowing access
 * to specific elements such as the player involved, the block's position, state,
 * and the face of the block that was interacted with.
 *
 * @since 0.1.0
 */
public record BlockUseContext(World world,
                              ItemStack stack,
                              Player player,
                              Hand hand,
                              BlockPos position,
                              BlockState blockState,
                              BlockFace face) implements ItemContext {

    public BlockUseContext {
        Objects.requireNonNull(world, "world");
        Objects.requireNonNull(stack, "stack");
        Objects.requireNonNull(player, "player");
        Objects.requireNonNull(hand, "hand");
        Objects.requireNonNull(position, "position");
        Objects.requireNonNull(blockState, "blockState");
        Objects.requireNonNull(face, "face");
    }

}