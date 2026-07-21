package fr.euphyllia.fidorial.server.command.brigadier.packet.registry;

import java.util.HashMap;
import java.util.Map;

public final class NetworkArgumentIds {

    private static final Map<Integer, ArgumentTypeRegistrar<?, ?>> BY_ID = new HashMap<>();
    private static final Map<ArgumentTypeRegistrar<?, ?>, Integer> IDS = new HashMap<>();

    private NetworkArgumentIds() {
    }

    public static void register(
            int id,
            ArgumentTypeRegistrar<?, ?> registrar
    ) {
        if (BY_ID.put(id, registrar) != null) {
            throw new IllegalStateException(
                    "Duplicate network id: " + id
            );
        }

        if (IDS.put(registrar, id) != null) {
            throw new IllegalStateException(
                    "Registrar already registered: " + registrar
            );
        }
    }

    public static int getId(
            ArgumentTypeRegistrar<?, ?> registrar
    ) {
        Integer id = IDS.get(registrar);

        if (id == null) {
            throw new IllegalArgumentException(
                    "Unknown registrar: " + registrar.getClass().getName()
            );
        }

        return id;
    }

    public static ArgumentTypeRegistrar<?, ?> byId(
            int id
    ) {
        ArgumentTypeRegistrar<?, ?> registrar = BY_ID.get(id);

        if (registrar == null) {
            throw new IllegalArgumentException(
                    "Unknown network argument id: " + id
            );
        }

        return registrar;
    }
}
