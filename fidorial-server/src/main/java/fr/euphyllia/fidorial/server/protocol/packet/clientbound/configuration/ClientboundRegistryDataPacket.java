package fr.euphyllia.fidorial.server.protocol.packet.clientbound.configuration;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.protocol.catalog.ConfigurationClientboundPackets;
import fr.euphyllia.fidorial.server.protocol.packet.ClientboundPacket;

import java.util.List;

/**
 * <p>Sent by the server to inform the client of the contents of synchronized registries , which are sourced from the server's data packs . Each packet contains the contents of a single registry. The client will accumulate the data contained in these packets during the configuration phase, and validate it once Finish Configuration is received from the server. The ordering of the entries in the Entries array defines the numeric IDs that they will be assigned to, starting from 0 and counting upwards. It is essential to maintain consistency between server and client, since many parts of the protocol reference these entries by their ID. The client will disconnect upon receiving a reference to a non-existing entry. The NBT data of registry entries has the same structure as their definitions in data packs , but represented in NBT instead of JSON . The format for each registry is defined in Java Edition protocol/Registries#List of synchronized registries .</p>
 *
 * <p><b>Packet ID:</b> Configuration = 7 (0x07)</p>
 * <p><b>Source:</b> <a href="https://minecraft.wiki/w/Java_Edition_protocol/Packets#Registry_Data">Registry Data</a></p>
 *
 * <h4>Packet structure</h4>
 * <table>
 *   <caption>Fields, in write/read order</caption>
 *   <thead>
 *     <tr><th>#</th><th>Field</th><th>Type</th><th>Description</th></tr>
 *   </thead>
 *   <tbody>
 *     <tr><td>0</td><td>Registry ID</td><td>Identifier</td><td>Name of the registry, such as minecraft:dimension_type .</td></tr>
 *     <tr><td>1</td><td>Entries</td><td>Entry ID</td><td>Prefixed Array</td></tr>
 *     <tr><td>2</td><td>Data</td><td>Prefixed Optional NBT</td><td>Entry data. If omitted, sourced from the selected known packs .</td></tr>
 *   </tbody>
 * </table>
 */
public record ClientboundRegistryDataPacket(String registryId, List<String> entries)
        implements ClientboundPacket {

    @Override
    public String name() {
        return ConfigurationClientboundPackets.REGISTRY_DATA;
    }

    @Override
    public void write(PacketBuffer buf) {
        buf.writeIdentifier(registryId);
        buf.writeVarInt(entries.size());
        for (String entry : entries) {
            buf.writeIdentifier(entry);
            buf.writeBoolean(false);
        }
    }
}
