package fr.euphyllia.fidorial.api.event.player;

import fr.euphyllia.fidorial.api.entity.Player;
import fr.euphyllia.fidorial.api.event.Cancellable;
import fr.euphyllia.fidorial.api.world.BlockPos;

public final class BlockPlaceEvent implements PlayerEvent, Cancellable {

    private final Player player;
    private final BlockPos position;
    private final int stateId;
    private boolean cancelled;

    public BlockPlaceEvent(Player player, BlockPos position, int stateId) {
        this.player = player;
        this.position = position;
        this.stateId = stateId;
    }

    @Override
    public Player player() {
        return player;
    }

    public BlockPos position() {
        return position;
    }

    public int stateId() {
        return stateId;
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
