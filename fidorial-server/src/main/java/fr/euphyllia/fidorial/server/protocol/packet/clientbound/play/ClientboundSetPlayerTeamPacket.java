package fr.euphyllia.fidorial.server.protocol.packet.clientbound.play;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.protocol.catalog.PlayClientboundPackets;
import fr.euphyllia.fidorial.server.protocol.packet.ClientboundPacket;
import net.kyori.adventure.text.Component;

/**
 * <p>Creates and updates teams. Team Color: The color of a team defines how the names of the team members are visualized; any formatting code can be used. The following table lists all the possible values.</p>
 *
 * <p><b>Packet ID:</b> Play = 109 (0x6D)</p>
 * <p><b>Source:</b> <a href="https://minecraft.wiki/w/Java_Edition_protocol/Packets#Update_Teams">Update Teams</a></p>
 *
 * <h4>Packet structure</h4>
 * <table>
 *   <caption>Fields, in write/read order</caption>
 *   <thead>
 *     <tr><th>#</th><th>Field</th><th>Type</th><th>Description</th></tr>
 *   </thead>
 *   <tbody>
 *     <tr><td>0</td><td>Team Name</td><td>String (32767)</td><td>A unique name for the team. (Shared with scoreboard).</td></tr>
 *     <tr><td>1</td><td>Method</td><td>Byte</td><td>Determines the layout of the remaining packet.</td></tr>
 *     <tr><td>2</td><td>0: create team</td><td>Team Display Name</td><td>Text Component</td></tr>
 *     <tr><td>3</td><td>Team Prefix</td><td>Text Component</td><td>Displayed before the names of players that are part of this team.</td></tr>
 *     <tr><td>4</td><td>Team Suffix</td><td>Text Component</td><td>Displayed after the names of players that are part of this team.</td></tr>
 *     <tr><td>5</td><td>Name Tag Visibility</td><td>VarInt Enum</td><td>0 = ALWAYS, 1 = NEVER, 2 = HIDE_FOR_OTHER_TEAMS, 3 = HIDE_FOR_OWN_TEAMS</td></tr>
 *     <tr><td>6</td><td>Collision Rule</td><td>VarInt Enum</td><td>0 = ALWAYS, 1 = NEVER, 2 = PUSH_OTHER_TEAMS, 3 = PUSH_OWN_TEAM</td></tr>
 *     <tr><td>7</td><td>Team Color</td><td>VarInt Enum</td><td>Used to color the names of players on the team; see below.</td></tr>
 *     <tr><td>8</td><td>Friendly Flags</td><td>Byte</td><td>Bit mask. 0x01: Allow friendly fire, 0x02: can see invisible players on the same team.</td></tr>
 *     <tr><td>9</td><td>Entities</td><td>Prefixed Array of String (32767)</td><td>Identifiers for the entities in this team.  For players, this is their username; for other entities, it is their UUID.</td></tr>
 *     <tr><td>10</td><td>1: remove team</td><td>no fields</td><td>no fields</td></tr>
 *     <tr><td>11</td><td>2: update team info</td><td>Team Display Name</td><td>Text Component</td></tr>
 *     <tr><td>12</td><td>Team Prefix</td><td>Text Component</td><td>Displayed before the names of players that are part of this team.</td></tr>
 *     <tr><td>13</td><td>Team Suffix</td><td>Text Component</td><td>Displayed after the names of players that are part of this team.</td></tr>
 *     <tr><td>14</td><td>Name Tag Visibility</td><td>VarInt Enum</td><td>0 = ALWAYS, 1 = NEVER, 2 = HIDE_FOR_OTHER_TEAMS, 3 = HIDE_FOR_OWN_TEAMS</td></tr>
 *     <tr><td>15</td><td>Collision Rule</td><td>VarInt Enum</td><td>0 = ALWAYS, 1 = NEVER, 2 = PUSH_OTHER_TEAMS, 3 = PUSH_OWN_TEAM</td></tr>
 *     <tr><td>16</td><td>Team Color</td><td>VarInt Enum</td><td>Used to color the names of players on the team; see below.</td></tr>
 *     <tr><td>17</td><td>Friendly Flags</td><td>Byte</td><td>Bit mask. 0x01: Allow friendly fire, 0x02: can see invisible players on the same team.</td></tr>
 *     <tr><td>18</td><td>3: add entities to team</td><td>Entities</td><td>Prefixed Array of String (32767)</td></tr>
 *     <tr><td>19</td><td>4: remove entities from team</td><td>Entities</td><td>Prefixed Array of String (32767)</td></tr>
 *   </tbody>
 * </table>
 */
public record ClientboundSetPlayerTeamPacket(String teamName, byte method, Object _0CreateTeam, Component teamPrefix,
                                             Component teamSuffix, Object nameTagVisibility, Object collisionRule,
                                             Object teamColor, byte friendlyFlags, Object entities, Object _1RemoveTeam,
                                             Object _2UpdateTeamInfo, Component teamPrefix2, Component teamSuffix2,
                                             Object nameTagVisibility2, Object collisionRule2, Object teamColor2,
                                             byte friendlyFlags2, Object _3AddEntitiesToTeam,
                                             Object _4RemoveEntitiesFromTeam) implements ClientboundPacket {

    @Override
    public String name() {
        return PlayClientboundPackets.SET_PLAYER_TEAM;
    }

    @Override
    public void write(PacketBuffer buf) {
        buf.writeString(teamName);
        buf.writeByte(method);
        // TODO: write _0CreateTeam (Team Display Name)
        buf.writeComponent(teamPrefix);
        buf.writeComponent(teamSuffix);
        // TODO: write nameTagVisibility (VarInt Enum)
        // TODO: write collisionRule (VarInt Enum)
        // TODO: write teamColor (VarInt Enum)
        buf.writeByte(friendlyFlags);
        // TODO: write entities (Prefixed Array of String)
        // TODO: write _1RemoveTeam (no fields)
        // TODO: write _2UpdateTeamInfo (Team Display Name)
        buf.writeComponent(teamPrefix2);
        buf.writeComponent(teamSuffix2);
        // TODO: write nameTagVisibility2 (VarInt Enum)
        // TODO: write collisionRule2 (VarInt Enum)
        // TODO: write teamColor2 (VarInt Enum)
        buf.writeByte(friendlyFlags2);
        // TODO: write _3AddEntitiesToTeam (Entities)
        // TODO: write _4RemoveEntitiesFromTeam (Entities)
    }
}
