package fr.euphyllia.fidorial.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class Main {

    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

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
