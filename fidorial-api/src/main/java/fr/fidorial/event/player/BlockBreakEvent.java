package fr.fidorial.event.player;

import fr.fidorial.entity.Player;
import fr.fidorial.event.Cancellable;
import fr.fidorial.world.BlockPos;

public final class BlockBreakEvent implements PlayerEvent, Cancellable {

    private final Player player;
    private final BlockPos position;
    private boolean cancelled;

    public BlockBreakEvent(Player player, BlockPos position) {
        this.player = player;
        this.position = position;
    }

    @Override
    public Player player() {
        return player;
    }

    public BlockPos position() {
        return position;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }
}
