package fr.fidorial.entity;

@FunctionalInterface
public interface EntityPredicate {
    boolean test(Entity entity);
}
