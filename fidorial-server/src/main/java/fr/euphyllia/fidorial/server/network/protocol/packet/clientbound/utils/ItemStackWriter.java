package fr.euphyllia.fidorial.server.network.protocol.packet.clientbound.utils;

import fr.euphyllia.fidorial.server.FidorialServer;
import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.registry.RegistryHolder;
import fr.euphyllia.fidorial.server.world.chunk.BlockState;
import fr.fidorial.inventory.ItemStack;
import net.kyori.adventure.key.Key;

public final class ItemStackWriter {

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
        final int id = frozen.networkId(Key.key("minecraft", "item"), stack.id());
        return Math.max(id, FidorialServer.getInstance().blockStateRegistry().networkId(BlockState.AIR));
    }
}