package fr.euphyllia.fidorial.server.registry;

public final class Registries {

    private final RegistryHolder dynamic;
    private final RegistryHolder frozen;

    private Registries(RegistryHolder dynamic, RegistryHolder frozen) {
        this.dynamic = dynamic;
        this.frozen = frozen;
    }

    public static Registries load() {
        return new Registries(
                RegistryHolder.of(GeneratedRegistryData.dynamic()),
                RegistryHolder.of(GeneratedRegistryData.frozen()));
    }

    public RegistryHolder dynamic() {
        return dynamic;
    }

    public RegistryHolder frozen() {
        return frozen;
    }
}
