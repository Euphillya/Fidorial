package fr.euphyllia.fidorial.server;

import net.kyori.adventure.text.logger.slf4j.ComponentLogger;

public final class Main {

    private static final ComponentLogger LOGGER = ComponentLogger.logger(Main.class);

    private Main() {
    }

    static void main(String[] args) {
        try {
            FidorialServer server = new FidorialServer();
            Runtime.getRuntime().addShutdownHook(new Thread(server::shutdown, "fidorial-shutdown"));
            server.start();
        } catch (Throwable t) {
            LOGGER.error("Fidorial n'a pas pu demarrer", t);
            System.exit(1);
        }
    }
}
