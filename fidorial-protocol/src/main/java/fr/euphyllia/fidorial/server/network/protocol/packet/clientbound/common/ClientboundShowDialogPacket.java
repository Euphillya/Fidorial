package fr.euphyllia.fidorial.server.network.protocol.packet.clientbound.common;

import fr.euphyllia.fidorial.server.codecs.dialog.DialogCodecs;
import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.network.protocol.packet.ClientboundPacket;
import fr.fidorial.dialog.Dialog;
import fr.fidorial.dialog.DialogDefinition;
import fr.fidorial.dialog.DialogReference;
import net.kyori.adventure.key.Key;

import java.util.Objects;

/**
 * Opens a dialog on the client.
 *
 * @param name          the packet identifier for the current connection phase
 * @param dialog        the dialog to open
 * @param registryIndex the position of the referenced entry in the dialog registry, or {@code -1}
 *                      when the dialog is written inline
 * @see <a href="https://minecraft.wiki/w/Java_Edition_protocol/Packets#Show_Dialog">Show Dialog</a>
 */
public record ClientboundShowDialogPacket(Key name, Dialog dialog, int registryIndex) implements ClientboundPacket {

    /**
     * @param name          the packet identifier for the current connection phase
     * @param dialog        the dialog to open
     * @param registryIndex the position of the referenced entry, or {@code -1} for an inline dialog
     */
    public ClientboundShowDialogPacket {
        Objects.requireNonNull(name, "name");
        Objects.requireNonNull(dialog, "dialog");
        if (dialog instanceof DialogReference && registryIndex < 0) {
            throw new IllegalArgumentException("A dialog reference needs the network id of the registry entry");
        }
    }

    /**
     * Creates a packet carrying a full dialog definition.
     *
     * @param name   the packet identifier for the current connection phase
     * @param dialog the dialog to open
     * @return the packet
     */
    public static ClientboundShowDialogPacket inline(final Key name, final DialogDefinition dialog) {
        return new ClientboundShowDialogPacket(name, dialog, -1);
    }

    /**
     * Creates a packet pointing at a registered dialog.
     *
     * @param name          the packet identifier for the current connection phase
     * @param reference     the dialog to open
     * @param registryIndex the position of the entry in the dialog registry
     * @return the packet
     */
    public static ClientboundShowDialogPacket reference(
            final Key name,
            final DialogReference reference,
            final int registryIndex
    ) {
        return new ClientboundShowDialogPacket(name, reference, registryIndex);
    }

    @Override
    public void write(final PacketBuffer buf) {
        switch (dialog) {
            case DialogReference _ -> buf.writeVarInt(registryIndex + 1);
            case final DialogDefinition definition -> {
                buf.writeVarInt(0);
                buf.writeNbt(DialogCodecs.toNbt(definition));
            }
        }
    }
}
