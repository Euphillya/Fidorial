package fr.euphyllia.fidorial.server.inventory;

import fr.euphyllia.fidorial.server.entity.player.InventorySlots;
import fr.euphyllia.fidorial.server.entity.player.ServerPlayer;
import fr.euphyllia.fidorial.server.registry.RegistryHolder;
import fr.fidorial.inventory.Container;
import fr.fidorial.item.ItemStack;
import net.kyori.adventure.text.Component;

public final class PlayerInventoryMenu extends ContainerMenu {

    public static final int WINDOW_ID = 0;

    public static final int SLOT_COUNT = 46;

    private static final int CRAFT_RESULT = 0;
    private static final int CRAFT_FIRST = 1;
    private static final int CRAFT_SIZE = 4;
    private static final int ARMOUR_LAST = 8;
    private static final int MAIN_WINDOW_FIRST = 9;
    private static final int MAIN_WINDOW_LAST = 35;
    private static final int HOTBAR_WINDOW_FIRST = 36;
    private static final int HOTBAR_WINDOW_LAST = 44;
    private static final int OFFHAND_WINDOW = 45;

    private final SimpleContainer crafting = new SimpleContainer(CRAFT_SIZE);

    public PlayerInventoryMenu(final ServerPlayer player) {
        super(player, WINDOW_ID);
    }

    private static boolean isCraftingGrid(final int windowSlot) {
        return windowSlot >= CRAFT_FIRST && windowSlot < CRAFT_FIRST + CRAFT_SIZE;
    }

    @Override
    protected Container top() {
        return crafting;
    }

    @Override
    public int slotCount() {
        return SLOT_COUNT;
    }

    @Override
    public int menuTypeId(final RegistryHolder frozen) {
        throw new UnsupportedOperationException("The player inventory is never opened with open_screen");
    }

    @Override
    public Component title() {
        return Component.translatable("container.inventory");
    }

    @Override
    protected boolean mayPlace(final int windowSlot) {
        return windowSlot != CRAFT_RESULT;
    }

    @Override
    protected boolean isTopSlot(final int windowSlot) {
        return windowSlot <= ARMOUR_LAST;
    }

    @Override
    protected int playerSectionStart() {
        return MAIN_WINDOW_FIRST;
    }

    @Override
    protected int playerSectionEnd() {
        return OFFHAND_WINDOW;
    }

    @Override
    protected ItemStack read(final int windowSlot) {
        if (windowSlot == CRAFT_RESULT) {
            return ItemStack.EMPTY; // TODO: recipe system
        }
        if (isCraftingGrid(windowSlot)) {
            return crafting.get(windowSlot - CRAFT_FIRST);
        }
        final int slot = InventorySlots.fromWindow(windowSlot);
        return slot == InventorySlots.INVALID ? ItemStack.EMPTY : player.inventory().get(slot);
    }

    @Override
    protected void write(final int windowSlot, final ItemStack stack) {
        if (windowSlot == CRAFT_RESULT) {
            return;
        }
        if (isCraftingGrid(windowSlot)) {
            crafting.set(windowSlot - CRAFT_FIRST, stack);
            return;
        }
        final int slot = InventorySlots.fromWindow(windowSlot);
        if (slot != InventorySlots.INVALID) {
            player.inventory().set(slot, stack);
        }
    }

    @Override
    protected void quickMove(final int windowSlot) {
        if (!isValidSlot(windowSlot) || windowSlot == CRAFT_RESULT) {
            return;
        }
        final ItemStack stack = read(windowSlot);
        if (stack.isEmpty()) {
            return;
        }

        final ItemStack leftover;
        if (windowSlot >= MAIN_WINDOW_FIRST && windowSlot <= MAIN_WINDOW_LAST) {
            leftover = insert(stack, HOTBAR_WINDOW_FIRST, HOTBAR_WINDOW_LAST + 1);
        } else if (windowSlot >= HOTBAR_WINDOW_FIRST && windowSlot <= HOTBAR_WINDOW_LAST) {
            leftover = insert(stack, MAIN_WINDOW_FIRST, HOTBAR_WINDOW_FIRST);
        } else {
            leftover = insert(stack, MAIN_WINDOW_FIRST, OFFHAND_WINDOW);
        }
        write(windowSlot, leftover);
    }


    @Override
    public void onClosed() {
        for (int i = 0; i < CRAFT_SIZE; i++) {
            final ItemStack stack = crafting.get(i);
            if (stack.isEmpty()) {
                continue;
            }
            crafting.set(i, insert(stack, MAIN_WINDOW_FIRST, OFFHAND_WINDOW));
        }
    }
}
