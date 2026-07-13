package fr.euphyllia.fidorial.server.metrics;

import com.google.gson.JsonObject;
import dev.faststats.SimpleMetrics;
import fr.euphyllia.fidorial.server.FidorialServer;

public class FidorialMetricsImpl extends SimpleMetrics {

    FidorialMetricsImpl(final Factory factory) throws IllegalArgumentException {
        super(factory);
    }

    @Override
    protected void appendDefaultData(final JsonObject metrics) {
        metrics.addProperty("online_mode", true);
        metrics.addProperty(
                "platform_version",
                FidorialServer.getInstance().minecraftVersion()
        );
        metrics.addProperty(
                "player_count",
                FidorialServer.getInstance().getPlayerCount()
        );
        metrics.addProperty("server_type", "Fidorial");
    }
}
