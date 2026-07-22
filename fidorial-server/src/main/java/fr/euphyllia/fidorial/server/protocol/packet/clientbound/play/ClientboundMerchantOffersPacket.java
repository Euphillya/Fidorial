package fr.euphyllia.fidorial.server.protocol.packet.clientbound.play;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.protocol.catalog.PlayClientboundPackets;
import fr.euphyllia.fidorial.server.protocol.packet.ClientboundPacket;

/**
 * <p>The list of trades a villager NPC is offering. Trade Item: Modifiers can increase or decrease the number of items for the first input slot. The second input slot and the output slot never change the number of items. The number of items may never be less than 1, and never more than the stack size. If special price and demand are both zero, only the default price is displayed. If either is non-zero, then the adjusted price is displayed next to the crossed-out default price. The adjusted prices is calculated as follows: Adjusted price = default price + floor(default price x multiplier x demand) + special price</p>
 *
 * <p><b>Packet ID:</b> Play = 52 (0x34)</p>
 * <p><b>Source:</b> <a href="https://minecraft.wiki/w/Java_Edition_protocol/Packets#Merchant_Offers">Merchant Offers</a></p>
 *
 * <h4>Packet structure</h4>
 * <table>
 *   <caption>Fields, in write/read order</caption>
 *   <thead>
 *     <tr><th>#</th><th>Field</th><th>Type</th><th>Description</th></tr>
 *   </thead>
 *   <tbody>
 *     <tr><td>0</td><td>Window ID</td><td>VarInt</td><td>The ID of the window that is open; this is an int rather than a byte.</td></tr>
 *     <tr><td>1</td><td>Trades</td><td>Input item 1</td><td>Prefixed Array</td></tr>
 *     <tr><td>2</td><td>Output item</td><td>Slot</td><td>The item the player will receive from this villager trade.</td></tr>
 *     <tr><td>3</td><td>Input item 2</td><td>Prefixed Optional Trade Item</td><td>The second item the player has to supply for this villager trade.</td></tr>
 *     <tr><td>4</td><td>Trade disabled</td><td>Boolean</td><td>True if the trade is disabled; false if the trade is enabled.</td></tr>
 *     <tr><td>5</td><td>Number of trade uses</td><td>Int</td><td>Number of times the trade has been used so far. If equal to the maximum number of trades, the client will display a red X.</td></tr>
 *     <tr><td>6</td><td>Maximum number of trade uses</td><td>Int</td><td>Number of times this trade can be used before it's exhausted.</td></tr>
 *     <tr><td>7</td><td>XP</td><td>Int</td><td>Amount of XP the villager will earn each time the trade is used.</td></tr>
 *     <tr><td>8</td><td>Special Price</td><td>Int</td><td>Can be zero or negative. The number is added to the price when an item is discounted due to player reputation or other effects.</td></tr>
 *     <tr><td>9</td><td>Price Multiplier</td><td>Float</td><td>Can be low (0.05) or high (0.2). Determines how much demand, player reputation, and temporary effects will adjust the price.</td></tr>
 *     <tr><td>10</td><td>Demand</td><td>Int</td><td>If positive, causes the price to increase. Negative values seem to be treated the same as zero.</td></tr>
 *     <tr><td>11</td><td>Villager level</td><td>VarInt</td><td>Appears on the trade GUI; meaning comes from the translation key merchant.level. + level. 1: Novice, 2: Apprentice, 3: Journeyman, 4: Expert, 5: Master.</td></tr>
 *     <tr><td>12</td><td>Experience</td><td>VarInt</td><td>Total experience for this villager (always 0 for the wandering trader).</td></tr>
 *     <tr><td>13</td><td>Is regular villager</td><td>Boolean</td><td>True if this is a regular villager; false for the wandering trader.  When false, hides the villager level and some other GUI elements.</td></tr>
 *     <tr><td>14</td><td>Can restock</td><td>Boolean</td><td>True for regular villagers and false for the wandering trader. If true, the "Villagers restock up to two times per day." message is displayed when hovering over disabled trades.</td></tr>
 *   </tbody>
 * </table>
 */
public record ClientboundMerchantOffersPacket(int windowId, Object trades, Object outputItem, Object inputItem2,
                                              boolean tradeDisabled, int numberOfTradeUses,
                                              int maximumNumberOfTradeUses, int xp, int specialPrice,
                                              float priceMultiplier, int demand, int villagerLevel, int experience,
                                              boolean isRegularVillager,
                                              boolean canRestock) implements ClientboundPacket {

    @Override
    public String name() {
        return PlayClientboundPackets.MERCHANT_OFFERS;
    }

    @Override
    public void write(PacketBuffer buf) {
        buf.writeVarInt(windowId);
        // TODO: write trades (Input item 1)
        // TODO: write outputItem (Slot)
        // TODO: write inputItem2 (Prefixed Optional Trade Item)
        buf.writeBoolean(tradeDisabled);
        buf.writeInt(numberOfTradeUses);
        buf.writeInt(maximumNumberOfTradeUses);
        buf.writeInt(xp);
        buf.writeInt(specialPrice);
        buf.writeFloat(priceMultiplier);
        buf.writeInt(demand);
        buf.writeVarInt(villagerLevel);
        buf.writeVarInt(experience);
        buf.writeBoolean(isRegularVillager);
        buf.writeBoolean(canRestock);
    }
}
