package fr.euphyllia.fidorial.server.protocol.packet.clientbound.play;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.protocol.catalog.PlayClientboundPackets;
import fr.euphyllia.fidorial.server.protocol.packet.ClientboundPacket;

/**
 * <p>This is sent to the client when it should create a new scoreboard objective or remove one.</p>
 *
 * <p><b>Packet ID:</b> Play = 106 (0x6A)</p>
 * <p><b>Source:</b> <a href="https://minecraft.wiki/w/Java_Edition_protocol/Packets#Update_Objectives">Update Objectives</a></p>
 *
 * <h4>Packet structure</h4>
 * <table>
 *   <caption>Fields, in write/read order</caption>
 *   <thead>
 *     <tr><th>#</th><th>Field</th><th>Type</th><th>Description</th></tr>
 *   </thead>
 *   <tbody>
 *     <tr><td>0</td><td>Objective Name</td><td>String (32767)</td><td>A unique name for the objective.</td></tr>
 *     <tr><td>1</td><td>Mode</td><td>Byte</td><td>0 to create the scoreboard. 1 to remove the scoreboard. 2 to update the display text.</td></tr>
 *     <tr><td>2</td><td>Objective Value</td><td>Optional Text Component</td><td>Only if mode is 0 or 2.The text to be displayed for the score.</td></tr>
 *     <tr><td>3</td><td>Type</td><td>Optional VarInt Enum</td><td>Only if mode is 0 or 2. 0 = "integer", 1 = "hearts".</td></tr>
 *     <tr><td>4</td><td>Has Number Format</td><td>Optional Boolean</td><td>Only if mode is 0 or 2. Whether this objective has a set number format for the scores.</td></tr>
 *     <tr><td>5</td><td>Number Format</td><td>Optional VarInt Enum</td><td>Only if mode is 0 or 2 and the previous boolean is true. Determines how the score number should be formatted.</td></tr>
 *     <tr><td>6</td><td>Number Format</td><td>Field Name</td><td>&nbsp;</td></tr>
 *     <tr><td>7</td><td>0: blank</td><td>no fields</td><td>Show nothing.</td></tr>
 *     <tr><td>8</td><td>1: styled</td><td>Styling</td><td>Compound Tag</td></tr>
 *     <tr><td>9</td><td>2: fixed</td><td>Content</td><td>Text Component</td></tr>
 *   </tbody>
 * </table>
 */
public record ClientboundSetObjectivePacket(String objectiveName, byte mode, Object objectiveValue, Object type,
                                            Object hasNumberFormat, Object numberFormat, Object numberFormat2,
                                            Object _0Blank, Object _1Styled,
                                            Object _2Fixed) implements ClientboundPacket {

    @Override
    public String name() {
        return PlayClientboundPackets.SET_OBJECTIVE;
    }

    @Override
    public void write(PacketBuffer buf) {
        buf.writeString(objectiveName);
        buf.writeByte(mode);
        // TODO: write objectiveValue (Optional Text Component)
        // TODO: write type (Optional VarInt Enum)
        // TODO: write hasNumberFormat (Optional Boolean)
        // TODO: write numberFormat (Optional VarInt Enum)
        // TODO: write numberFormat2 (Field Name)
        // TODO: write _0Blank (no fields)
        // TODO: write _1Styled (Styling)
        // TODO: write _2Fixed (Content)
    }
}
