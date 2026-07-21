package fr.euphyllia.fidorial.server.protocol.packet.clientbound.play;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.protocol.catalog.PlayClientboundPackets;
import fr.euphyllia.fidorial.server.protocol.packet.ClientboundPacket;
import fr.euphyllia.fidorial.server.registry.RegistryHolder;
import fr.fidorial.inventory.ItemStack;
import fr.fidorial.inventory.PlayerInventory;

import java.util.Arrays;

/**
 * <p>Replaces the contents of a container window. Sent by the server upon initialization of a container window or the player's inventory, and in response to state ID mismatches (see #Click Container ). See inventory windows for further information about how slots are indexed.
 * Use Open Screen to open the container on the client.</p>
 *
 * <p><b>Packet ID:</b> Play = 18 (0x12)</p>
 * <p><b>Source:</b> <a href="https://minecraft.wiki/w/Java_Edition_protocol/Packets#Set_Container_Content">Set Container Content</a></p>
 *
 * <h4>Packet structure</h4>
 * <table>
 *   <caption>Fields, in write/read order</caption>
 *   <thead>
 *     <tr><th>#</th><th>Field</th><th>Type</th><th>Description</th></tr>
 *   </thead>
 *   <tbody>
 *     <tr><td>0</td><td>Window ID</td><td>VarInt</td><td>The ID of window which items are being sent for. 0 for player inventory. The client ignores any packets targeting a Window ID other than the current one. However, an exception is made for the player inventory, which may be targeted at any time. (The vanilla server does not appear to utilize this special case.)</td></tr>
 *     <tr><td>1</td><td>State ID</td><td>VarInt</td><td>A server-managed sequence number used to avoid desynchronization; see #Click Container .</td></tr>
 *     <tr><td>2</td><td>Slot Data</td><td>Prefixed Array of Slot</td><td>&nbsp;</td></tr>
 *     <tr><td>3</td><td>Carried Item</td><td>Slot</td><td>Item being dragged with the mouse.</td></tr>
 *   </tbody>
 * </table>
 */
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
        if (stack.isEmpty()) {
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
        return Math.max(id, 0);                   // 0 = air en secours
    }
}