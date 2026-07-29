package fr.euphyllia.fidorial.server.network.protocol.packet.clientbound.utils;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.registry.RegistryHolder;
import fr.fidorial.inventory.ItemStack;


public final class ItemStackWriter {

    private static final int AIR_ITEM = 0;

    private ItemStackWriter() {
    }

    public static void writeSlot(final PacketBuffer buf, final ItemStack stack, final RegistryHolder frozen) {
        if (stack.isEmpty()) {
            buf.writeVarInt(0);
            return;
        }
        buf.writeVarInt(stack.count());
        buf.writeVarInt(networkId(stack, frozen));
        buf.writeVarInt(0); // components to add
        buf.writeVarInt(0); // components to remove
    }

    public static int networkId(final ItemStack stack, final RegistryHolder frozen) {
        final int id = frozen.networkId("minecraft:item", stack.id().asString());
        return Math.max(id, AIR_ITEM);
    }
}