package fr.euphyllia.fidorial.api.plugin;

import java.util.Collection;
import java.util.Optional;

public interface PluginManager {

    Collection<PluginMeta> loaded();

    Optional<Plugin> plugin(String id);

    boolean isEnabled(String id);
}
