package fr.euphyllia.fidorial.api.entity;

import net.kyori.adventure.key.Key;

import java.util.Objects;

public record EntityType(Key key, Category category, float width, float height) {

    public EntityType {
        Objects.requireNonNull(key, "key");
        Objects.requireNonNull(category, "category");
    }

    public enum Category {
        PLAYER,
        MONSTER,
        CREATURE,
        AMBIENT,
        WATER_CREATURE,
        MISC
    }
}
