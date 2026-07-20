package fr.fidorial.command.argument.resolvers.selector;

import fr.fidorial.entity.Entity;
import org.jetbrains.annotations.ApiStatus;

import java.util.List;

@ApiStatus.NonExtendable
public interface EntitySelectorArgumentResolver extends SelectorArgumentResolver<List<Entity>> {
}
