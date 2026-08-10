package fr.euphyllia.fidorial.server.world.light;

import java.util.Set;

public interface LightEngine {

    Set<Long> checkBlock(int x, int y, int z, LightAccess access);

    void relight(Set<Long> chunks, LightAccess access);
}
