package fr.fidorial.entity;

public interface LivingEntity extends Entity {

    float health();

    void setHealth(float health);

    float maxHealth();

    default boolean isDead() {
        return health() <= 0f;
    }
}
