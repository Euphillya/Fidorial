package fr.euphyllia.fidorial.server.registry;

public final class Registries {

    private final RegistryHolder dynamic;

    private Registries(RegistryHolder dynamic) {
        this.dynamic = dynamic;
    }

    public static Registries load() {
        return new Registries(RegistryHolder.of(GeneratedRegistryData.dynamic()));
    }

    public RegistryHolder dynamic() {
        return dynamic;
    }
}
