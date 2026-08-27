package fr.euphyllia.fidorial.server.world.entity;

public interface LevelPlayer extends LevelEntity {

    @Override
    default boolean isPlayer() {
        return true;
    }
}
