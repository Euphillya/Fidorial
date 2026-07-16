package fr.euphyllia.fidorial.server.world;

import java.util.function.LongConsumer;

public interface ChunkViewSource {

    void collectViewedChunks(LongConsumer keys);
}
