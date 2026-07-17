package fr.euphyllia.fidorial.api.translation;

import net.kyori.adventure.text.Component;

import java.util.Locale;

public interface TranslationStore {

    /**
     * Replace the active translation store implementation.
     *
     * @param store new {@link TranslationStore}
     */
    static void setStore(TranslationStore store) {
        if (store == null) {
            throw new IllegalArgumentException("TranslationStore cannot be null");
        }

        TranslationStore previous = current();

        store.load();
        Holder.INSTANCE = store;

        if (previous != null) {
            previous.unload();
        }
    }

    /**
     * Gets the active translation store implementation.
     *
     * @return active {@link TranslationStore}
     */
    static TranslationStore current() {
        return Holder.INSTANCE;
    }

    /**
     * Render a component using a locale.
     *
     * @param component {@link Component} to render
     * @param locale {@link Locale} to render with
     * @return rendered {@link Component}
     */
    static Component render(Component component, Locale locale) {
        return current().renderComponent(component, locale);
    }

    /**
     * Gets the default locate for the current translation store.
     *
     * @return default {@link Locale}
     */
    static Locale defaultLocale() {
        return current().getDefaultLocale();
    }

    Component renderComponent(Component component, Locale locale);

    Locale getDefaultLocale();

    void load();

    void unload();

    final class Holder {
        private Holder() {}
        private static volatile TranslationStore INSTANCE;
    }
}
