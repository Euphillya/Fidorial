package fr.euphyllia.fidorial.server.registry;

import fr.euphyllia.fidorial.server.context.ServerContext;
import fr.fidorial.registry.BiomeRegistry;

public interface RegistryContext extends ServerContext {

    Registries registries();

    BiomeRegistry biomes();

    static RegistryContext get() {
        return ServerContext.get(RegistryContext.class);
    }
}
