package fr.euphyllia.fidorial.server.adventure.providers;

import fr.euphyllia.fidorial.api.translation.TranslationStore;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.logger.slf4j.ComponentLogger;
import net.kyori.adventure.text.logger.slf4j.ComponentLoggerProvider;
import org.slf4j.LoggerFactory;

import java.util.Locale;

import static fr.euphyllia.fidorial.server.adventure.AdventureHelper.ANSI_SERIALIZER;

public class ComponentLoggerProviderImpl implements ComponentLoggerProvider {
    @Override
    public ComponentLogger logger(ComponentLoggerProvider.LoggerHelper helper, String name) {
        return helper.delegating(LoggerFactory.getLogger(name), this::serialize);
    }

    private String serialize(final Component message) {
        return ANSI_SERIALIZER.serialize(TranslationStore.render(message, Locale.getDefault()));
    }
}
