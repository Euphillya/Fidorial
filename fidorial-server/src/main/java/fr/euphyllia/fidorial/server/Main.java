package fr.euphyllia.fidorial.server;

import net.kyori.adventure.text.logger.slf4j.ComponentLogger;

import static fr.euphyllia.fidorial.server.adventure.AdventureHelper.getLogger;

public final class Main {

    private static final ComponentLogger LOGGER = getLogger(Main.class);

    private Main() {
    }

    static void main(String[] args) {
        try {
            FidorialServer server = new FidorialServer();
            Runtime.getRuntime().addShutdownHook(
                    new Thread(server::shutdown, "fidorial-shutdown"));
            server.start();
        } catch (Throwable t) {
            LOGGER.error("Fidorial n'a pas pu demarrer", t);
            System.exit(1);
        }
    }
}
