package fr.euphyllia.fidorial.api.plugin;

public interface Plugin {

    default void onLoad(PluginContext context) {
    }

    default void onEnable() {
    }

    default void onDisable() {
    }
}
