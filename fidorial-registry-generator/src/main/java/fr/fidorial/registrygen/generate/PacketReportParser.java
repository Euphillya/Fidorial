package fr.fidorial.registrygen.generate;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import fr.fidorial.registrygen.model.RegistryDefinition;
import fr.fidorial.registrygen.model.RegistryEntryDefinition;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public final class PacketReportParser {

    private static final List<String> BOUNDS = List.of("clientbound", "serverbound");

    public List<RegistryDefinition> parse(final Path packetsJson) throws IOException {
        final JsonObject root;
        try (var reader = Files.newBufferedReader(packetsJson)) {
            root = JsonParser.parseReader(reader).getAsJsonObject();
        }

        final List<RegistryDefinition> results = new ArrayList<>();

        for (final var stateEntry : root.entrySet()) {
            final String state = stateEntry.getKey();
            final JsonObject stateObject = stateEntry.getValue().getAsJsonObject();

            for (final String bound : BOUNDS) {
                if (!stateObject.has(bound)) continue;

                final JsonObject boundObject = stateObject.getAsJsonObject(bound);
                final List<RegistryEntryDefinition> entries = new ArrayList<>();

                for (final var packetEntry : boundObject.entrySet()) {
                    final int protocolId = packetEntry.getValue().getAsJsonObject().get("protocol_id").getAsInt();
                    entries.add(new RegistryEntryDefinition(packetEntry.getKey(), protocolId));
                }

                results.add(new RegistryDefinition("packet:" + state + "/" + bound, 0, null, entries));
            }
        }
        return results;
    }
}
