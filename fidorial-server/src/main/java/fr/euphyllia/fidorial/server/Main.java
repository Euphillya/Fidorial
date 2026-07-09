package fr.euphyllia.fidorial.server;

public final class Main {

    public static void main(String[] args) throws Exception {
        FidorialServer server = new FidorialServer(25565);
        Runtime.getRuntime().addShutdownHook(new Thread(server::shutdown, "fidorial-shutdown"));
        server.start();
    }
}
