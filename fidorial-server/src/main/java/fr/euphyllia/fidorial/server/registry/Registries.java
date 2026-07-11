package fr.euphyllia.fidorial.server.registry;

import fr.euphyllia.fidorial.server.registry.loader.DatapackRegistryLoader;
import fr.euphyllia.fidorial.server.registry.loader.SnapshotRegistryLoader;

public final class Registries {

    private final RegistryHolder dynamic;
    private final RegistryHolder snapshot;

    private Registries(RegistryHolder dynamic, RegistryHolder snapshot) {
        this.dynamic = dynamic;
        this.snapshot = snapshot;
    }

    public static Registries load() {
        return new Registries(
                new DatapackRegistryLoader().load(),
                new SnapshotRegistryLoader().load());
    }

    public RegistryHolder dynamic() {
        return dynamic;
    }

    public RegistryHolder snapshot() {
        return snapshot;
    }
}
