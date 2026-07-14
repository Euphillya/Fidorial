package fr.euphyllia.fidorial.server.entity.player;

public final class InventorySlots {

    public static final int INVALID = -1;

    private InventorySlots() {
    }

    public static int fromWindow(int windowSlot) {
        if (windowSlot >= 36 && windowSlot <= 44) {
            return windowSlot - 36;      // hotbar -> 0..8
        }
        if (windowSlot >= 9 && windowSlot <= 35) {
            return windowSlot;           // inventaire principal
        }
        if (windowSlot >= 5 && windowSlot <= 8) {
            return 44 - windowSlot;      // armure : 5 -> 39 ... 8 -> 36
        }
        if (windowSlot == 45) {
            return 40;                   // main secondaire
        }
        return INVALID;
    }
}
