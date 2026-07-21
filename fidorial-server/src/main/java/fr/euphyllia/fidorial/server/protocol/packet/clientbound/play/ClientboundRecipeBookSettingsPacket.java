package fr.euphyllia.fidorial.server.protocol.packet.clientbound.play;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.protocol.catalog.PlayClientboundPackets;
import fr.euphyllia.fidorial.server.protocol.packet.ClientboundPacket;

/**
 * Automatically generated from the wiki — verify the serialisation.
 *
 * <p><b>Packet ID:</b> Play = 76 (0x4C)</p>
 * <p><b>Source:</b> <a href="https://minecraft.wiki/w/Java_Edition_protocol/Packets#Recipe_Book_Settings">Recipe Book Settings</a></p>
 *
 * <h4>Packet structure</h4>
 * <table>
 *   <caption>Fields, in write/read order</caption>
 *   <thead>
 *     <tr><th>#</th><th>Field</th><th>Type</th><th>Description</th></tr>
 *   </thead>
 *   <tbody>
 *     <tr><td>0</td><td>Crafting Recipe Book Open</td><td>Boolean</td><td>If true, then the crafting recipe book will be open when the player opens its inventory.</td></tr>
 *     <tr><td>1</td><td>Crafting Recipe Book Filter Active</td><td>Boolean</td><td>If true, then the filtering option is active when the player opens its inventory.</td></tr>
 *     <tr><td>2</td><td>Smelting Recipe Book Open</td><td>Boolean</td><td>If true, then the smelting recipe book will be open when the player opens its inventory.</td></tr>
 *     <tr><td>3</td><td>Smelting Recipe Book Filter Active</td><td>Boolean</td><td>If true, then the filtering option is active when the player opens its inventory.</td></tr>
 *     <tr><td>4</td><td>Blast Furnace Recipe Book Open</td><td>Boolean</td><td>If true, then the blast furnace recipe book will be open when the player opens its inventory.</td></tr>
 *     <tr><td>5</td><td>Blast Furnace Recipe Book Filter Active</td><td>Boolean</td><td>If true, then the filtering option is active when the player opens its inventory.</td></tr>
 *     <tr><td>6</td><td>Smoker Recipe Book Open</td><td>Boolean</td><td>If true, then the smoker recipe book will be open when the player opens its inventory.</td></tr>
 *     <tr><td>7</td><td>Smoker Recipe Book Filter Active</td><td>Boolean</td><td>If true, then the filtering option is active when the player opens its inventory.</td></tr>
 *   </tbody>
 * </table>
 */
public record ClientboundRecipeBookSettingsPacket(boolean craftingRecipeBookOpen,
                                                  boolean craftingRecipeBookFilterActive,
                                                  boolean smeltingRecipeBookOpen,
                                                  boolean smeltingRecipeBookFilterActive,
                                                  boolean blastFurnaceRecipeBookOpen,
                                                  boolean blastFurnaceRecipeBookFilterActive,
                                                  boolean smokerRecipeBookOpen,
                                                  boolean smokerRecipeBookFilterActive) implements ClientboundPacket {

    @Override
    public String name() {
        return PlayClientboundPackets.RECIPE_BOOK_SETTINGS;
    }

    @Override
    public void write(PacketBuffer buf) {
        buf.writeBoolean(craftingRecipeBookOpen);
        buf.writeBoolean(craftingRecipeBookFilterActive);
        buf.writeBoolean(smeltingRecipeBookOpen);
        buf.writeBoolean(smeltingRecipeBookFilterActive);
        buf.writeBoolean(blastFurnaceRecipeBookOpen);
        buf.writeBoolean(blastFurnaceRecipeBookFilterActive);
        buf.writeBoolean(smokerRecipeBookOpen);
        buf.writeBoolean(smokerRecipeBookFilterActive);
    }
}
