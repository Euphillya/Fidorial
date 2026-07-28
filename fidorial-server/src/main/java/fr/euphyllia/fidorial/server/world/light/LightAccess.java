package fr.euphyllia.fidorial.server.world.light;

import fr.euphyllia.fidorial.server.world.chunk.BlockState;
import org.jspecify.annotations.Nullable;

public interface LightAccess {

    int minY();

    int height();

    default int maxY() {
        return minY() + height();
    }

    BlockState blockAt(int x, int y, int z);

    @Nullable ChunkLightData lightAt(int chunkX, int chunkZ);
}
