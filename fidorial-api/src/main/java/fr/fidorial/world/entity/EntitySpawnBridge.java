package fr.fidorial.world.entity;

import fr.fidorial.entity.Entity;

public interface EntitySpawnBridge {

    void onEntityAppear(Entity entity);

    void onEntityDisappear(Entity entity);

}
