package fr.euphyllia.fidorial.server.protocol.packet.clientbound.play;

import fr.euphyllia.fidorial.server.entity.ItemStack;
import fr.euphyllia.fidorial.server.entity.player.PlayerInventory;
import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.protocol.catalog.PlayClientboundPackets;
import fr.euphyllia.fidorial.server.protocol.packet.ClientboundPacket;
import fr.euphyllia.fidorial.server.registry.RegistryHolder;

import java.util.Arrays;

public record ClientboundContainerSetContentPacket(
        PlayerInventory inventory, RegistryHolder frozen) implements ClientboundPacket {

    private static final int PLAYER_INVENTORY_WINDOW = 0;
    private static final int WINDOW_SLOTS = 46;

    private static int toWindowSlot(int slot) {
        if (slot >= 0 && slot <= 8) return slot + 36; // hotbar -> 36..44
        if (slot >= 9 && slot <= 35) return slot;      // inventaire principal -> identite
        if (slot >= 36 && slot <= 39) return 44 - slot; // armure : 36->8(bottes)..39->5(casque)
        if (slot == 40) return 45;        // main secondaire
        return -1;
    }

    @Override
    public String name() {
        return PlayClientboundPackets.CONTAINER_SET_CONTENT;
    }

    @Override
    public void write(PacketBuffer buf) {
        ItemStack[] window = new ItemStack[WINDOW_SLOTS];
        Arrays.fill(window, ItemStack.EMPTY);
        for (int slot = 0; slot < inventory.size(); slot++) {
            int w = toWindowSlot(slot);
            if (w >= 0 && w < WINDOW_SLOTS) {
                window[w] = inventory.get(slot);
            }
        }

        buf.writeVarInt(PLAYER_INVENTORY_WINDOW); // Window ID (VarInt depuis 1.21.2)
        buf.writeVarInt(0);                       // State ID
        buf.writeVarInt(WINDOW_SLOTS);            // nb de slots (46)
        for (ItemStack stack : window) {
            writeSlot(buf, stack);
        }
        writeSlot(buf, ItemStack.EMPTY);          // Carried item (curseur)
    }

    private void writeSlot(PacketBuffer buf, ItemStack stack) {
        if (stack == null || stack.isEmpty()) {
            buf.writeVarInt(0);                   // count 0 => slot vide
            return;
        }
        buf.writeVarInt(stack.count());           // Item Count
        buf.writeVarInt(itemNetworkId(stack));    // Item ID (registre frozen)
        buf.writeVarInt(0);                       // nb components a ajouter
        buf.writeVarInt(0);                       // nb components a retirer
    }

    private int itemNetworkId(ItemStack stack) {
        int id = frozen.networkId("minecraft:item", stack.id().asString());
        return id < 0 ? 0 : id;                   // 0 = air en secours
    }
}