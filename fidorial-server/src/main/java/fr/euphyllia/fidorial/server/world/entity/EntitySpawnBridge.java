package fr.euphyllia.fidorial.server.world.entity;

import fr.euphyllia.fidorial.server.entity.AbstractEntity;

public interface EntitySpawnBridge {

    void onEntityAppear(AbstractEntity entity);

    void onEntityDisappear(AbstractEntity entity);

}
