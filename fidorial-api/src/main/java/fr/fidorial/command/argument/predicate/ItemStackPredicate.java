package fr.fidorial.command.argument.predicate;

import fr.fidorial.inventory.ItemStack;
import org.jetbrains.annotations.ApiStatus;

import java.util.function.Predicate;

@ApiStatus.NonExtendable
public interface ItemStackPredicate extends Predicate<ItemStack> {
}
