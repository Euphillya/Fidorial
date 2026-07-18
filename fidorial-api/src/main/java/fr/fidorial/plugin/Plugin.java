package fr.fidorial.plugin;

public interface Plugin {

    default void onLoad(PluginContext context) {
    }

    default void onEnable() {
    }

    default void onDisable() {
    }
}
