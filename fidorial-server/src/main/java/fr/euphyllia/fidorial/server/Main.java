package fr.euphyllia.fidorial.server;

import net.kyori.adventure.text.logger.slf4j.ComponentLogger;

import static fr.euphyllia.fidorial.server.adventure.AdventureHelper.getLogger;

public final class Main {

    private static final ComponentLogger LOGGER = getLogger(Main.class);

    private Main() {
    }

    static void main(String[] args) {
        try {
            ServerConfig config = ServerConfig.load();
            FidorialServer server = new FidorialServer(config);
            Runtime.getRuntime().addShutdownHook(
                    new Thread(server::shutdown, "fidorial-shutdown"));
            server.start();
        } catch (Exception e) {
            LOGGER.error("Fidorial n'a pas pu demarrer", e);
            System.exit(1);
        }
    }
}
