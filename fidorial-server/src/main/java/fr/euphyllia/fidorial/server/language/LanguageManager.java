package fr.euphyllia.fidorial.server.language;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import fr.euphyllia.fidorial.api.registry.Key;
import fr.euphyllia.fidorial.server.FidorialServer;
import fr.euphyllia.fidorial.server.Main;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.translation.MiniMessageTranslationStore;
import net.kyori.adventure.translation.GlobalTranslator;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

public final class LanguageManager {

    private static final Gson GSON = new Gson();
    private static final Type LANGUAGE_TYPE = new TypeToken<Map<String, String>>() {}.getType();
    private final MiniMessageTranslationStore miniMessageStore = MiniMessageTranslationStore.create(Key.minecraft("translations"));

    public static final Locale DEFAULT_LOCALE = Locale.US;

    private static final Set<Locale> SUPPORTED_LOCALES = Set.of(
            Locale.FRANCE,
            Locale.US
    );

    public void loadBuiltin() {
        Map<Locale, String> languages = Map.of(
                Locale.FRANCE, "languages/fr_fr.json",
                Locale.US, "languages/en_us.json"
        );
        for (Map.Entry<Locale, String> entry : languages.entrySet()) {
            try {
                InputStream stream = Main.class.getClassLoader()
                        .getResourceAsStream(entry.getValue());

                if (stream == null) {
                    FidorialServer.LOGGER.warn("Missing builtin language: {}", entry.getValue());
                    continue;
                }

                try (Reader reader = new InputStreamReader(stream, StandardCharsets.UTF_8)) {
                    load(entry.getKey(), reader);
                }
            } catch (IOException ex) {
                FidorialServer.LOGGER.error("Couldn't load language {}", entry.getKey(), ex);
            }
        }
        install();
    }

    private void load(final Locale locale, final Reader reader) throws IOException {
        Map<String, String> entries = GSON.fromJson(reader, LANGUAGE_TYPE);
        if (entries == null) {
            return;
        }
        miniMessageStore.registerAll(locale, entries);
    }

    public void install() {
        GlobalTranslator.translator()
                .addSource(miniMessageStore);
    }

    public static GlobalTranslator translator() {
        return GlobalTranslator.translator();
    }

    public static Component render(final Component component, final Locale locale) {
        return GlobalTranslator.render(component, resolveLocale(locale));
    }

    private static Locale resolveLocale(final Locale locale) {
        if (locale == null) {
            return DEFAULT_LOCALE;
        }

        if (SUPPORTED_LOCALES.contains(locale)) {
            return locale;
        }

        return SUPPORTED_LOCALES.stream()
                .filter(supported -> supported.getLanguage()
                        .equals(locale.getLanguage()))
                .findFirst()
                .orElse(DEFAULT_LOCALE);
    }
}
