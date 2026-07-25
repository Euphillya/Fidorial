package fr.fidorial.entity;

import java.util.Collection;

public interface EntityManager {

    Collection<? extends Entity> entities();

    Collection<? extends Player> players();

    Collection<? extends Entity> entities(EntityPredicate predicate);
}
