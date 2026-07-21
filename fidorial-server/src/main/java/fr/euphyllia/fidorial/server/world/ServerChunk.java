package fr.euphyllia.fidorial.server.world;

import fr.euphyllia.fidorial.server.world.chunk.BlockState;
import fr.euphyllia.fidorial.server.world.chunk.ChunkColumn;
import fr.fidorial.world.Chunk;
import fr.fidorial.world.World;

public final class ServerChunk implements Chunk {

    private final ServerWorld world;
    private final ChunkColumn column;
    private final BlockStateRegistry blockStates;

    public ServerChunk(ServerWorld world, ChunkColumn column, BlockStateRegistry blockStates) {
        this.world = world;
        this.column = column;
        this.blockStates = blockStates;
    }

    public ChunkColumn column() {
        return column;
    }

    @Override
    public World world() {
        return world;
    }

    @Override
    public int chunkX() {
        return column.chunkX();
    }

    @Override
    public int chunkZ() {
        return column.chunkZ();
    }

    @Override
    public int minY() {
        return column.minY();
    }

    @Override
    public int height() {
        return column.height();
    }

    @Override
    public int getBlockStateId(int localX, int worldY, int localZ) {
        return blockStates.networkId(column.getBlock(localX & 15, worldY, localZ & 15));
    }

    @Override
    public boolean setBlockStateId(int localX, int worldY, int localZ, int stateId) {
        if (worldY < column.minY() || worldY >= column.minY() + column.height()) {
            return false;
        }
        BlockState state = blockStates.byId(stateId);
        column.setBlock(localX & 15, worldY, localZ & 15, state);
        world.markDirty(column.chunkX(), column.chunkZ());
        return true;
    }
}