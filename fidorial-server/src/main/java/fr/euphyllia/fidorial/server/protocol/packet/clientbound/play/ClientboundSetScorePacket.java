package fr.euphyllia.fidorial.server.protocol.packet.clientbound.play;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.protocol.catalog.PlayClientboundPackets;
import fr.euphyllia.fidorial.server.protocol.packet.ClientboundPacket;

/**
 * <p>This is sent to the client when it should update a scoreboard item.</p>
 *
 * <p><b>Packet ID:</b> Play = 110 (0x6E)</p>
 * <p><b>Source:</b> <a href="https://minecraft.wiki/w/Java_Edition_protocol/Packets#Update_Score">Update Score</a></p>
 *
 * <h4>Packet structure</h4>
 * <table>
 *   <caption>Fields, in write/read order</caption>
 *   <thead>
 *     <tr><th>#</th><th>Field</th><th>Type</th><th>Description</th></tr>
 *   </thead>
 *   <tbody>
 *     <tr><td>0</td><td>Entity Name</td><td>String (32767)</td><td>The entity whose score this is. For players, this is their username; for other entities, it is their UUID.</td></tr>
 *     <tr><td>1</td><td>Objective Name</td><td>String (32767)</td><td>The name of the objective the score belongs to.</td></tr>
 *     <tr><td>2</td><td>Value</td><td>VarInt</td><td>The score to be displayed next to the entry.</td></tr>
 *     <tr><td>3</td><td>Display Name</td><td>Prefixed Optional Text Component</td><td>The custom display name.</td></tr>
 *     <tr><td>4</td><td>Number Format</td><td>Prefixed Optional VarInt Enum</td><td>Determines how the score number should be formatted.</td></tr>
 *     <tr><td>5</td><td>Number Format</td><td>Field Name</td><td>&nbsp;</td></tr>
 *     <tr><td>6</td><td>0: blank</td><td>no fields</td><td>Show nothing.</td></tr>
 *     <tr><td>7</td><td>1: styled</td><td>Styling</td><td>Compound Tag</td></tr>
 *     <tr><td>8</td><td>2: fixed</td><td>Content</td><td>Text Component</td></tr>
 *   </tbody>
 * </table>
 */
public record ClientboundSetScorePacket(String entityName, String objectiveName, int value, Object displayName,
                                        Object numberFormat, Object numberFormat2, Object _0Blank, Object _1Styled,
                                        Object _2Fixed) implements ClientboundPacket {

    @Override
    public String name() {
        return PlayClientboundPackets.SET_SCORE;
    }

    @Override
    public void write(PacketBuffer buf) {
        buf.writeString(entityName);
        buf.writeString(objectiveName);
        buf.writeVarInt(value);
        // TODO: write displayName (Prefixed Optional Text Component)
        // TODO: write numberFormat (Prefixed Optional VarInt Enum)
        // TODO: write numberFormat2 (Field Name)
        // TODO: write _0Blank (no fields)
        // TODO: write _1Styled (Styling)
        // TODO: write _2Fixed (Content)
    }
}
