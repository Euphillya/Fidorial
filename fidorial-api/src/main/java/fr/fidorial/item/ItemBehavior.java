package fr.fidorial.item;

public interface ItemBehavior {

    ItemBehavior NONE = new ItemBehavior() {};

    default InteractionResult use(final ItemContext context) {
        return InteractionResult.PASS;
    }

    default InteractionResult useOnBlock(final ItemContext context) {
        return InteractionResult.PASS;
    }

    default void inventoryTick(final ItemContext context) {
    }

    default void onCrafted(final ItemContext context) {
    }

    default void onDestroyed(final ItemContext context) {
    }
}