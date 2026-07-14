package fr.euphyllia.fidorial.server.entity;

import fr.euphyllia.fidorial.api.entity.EntityType;
import fr.euphyllia.fidorial.api.registry.Key;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class EntityTypes {

    private static final Map<Key, EntityType> BY_KEY = new ConcurrentHashMap<>();

    public static final EntityType PLAYER = register(new EntityType(
            Key.minecraft("player"), EntityType.Category.PLAYER, 0.6f, 1.8f));

    private EntityTypes() {
    }

    public static EntityType register(EntityType type) {
        EntityType previous = BY_KEY.putIfAbsent(type.key(), type);
        if (previous != null) {
            throw new IllegalStateException("Type d'entite deja enregistre : " + type.key());
        }
        return type;
    }

    public static EntityType get(Key key) {
        return BY_KEY.get(key);
    }
}
