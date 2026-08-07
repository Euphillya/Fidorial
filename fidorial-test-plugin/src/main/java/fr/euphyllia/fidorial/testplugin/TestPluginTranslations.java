package fr.euphyllia.fidorial.testplugin;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import fr.fidorial.plugin.PluginContext;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.minimessage.translation.MiniMessageTranslationStore;
import net.kyori.adventure.translation.GlobalTranslator;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.Map;

public final class TestPluginTranslations {

    private static final Gson GSON = new Gson();
    private static final Type LANGUAGE_TYPE = new TypeToken<Map<String, String>>() {}.getType();

    private static final Key STORE_KEY = Key.key("fidorial-testplugin", "translations");

    private static final MiniMessageTranslationStore STORE = MiniMessageTranslationStore.create(STORE_KEY);

    private TestPluginTranslations() {
    }

    public static void register(final PluginContext context) {
        final Map<Locale, String> languages = Map.of(
                Locale.FRANCE, "languages/fr_fr.json",
                Locale.US, "languages/en_us.json"
        );

        for (final Map.Entry<Locale, String> entry : languages.entrySet()) {
            try (final InputStream stream = context.resource(entry.getValue())) {
                if (stream == null) {
                    System.err.println("Could not find resource " + entry.getValue());
                    continue;
                }

                try (final Reader reader = new InputStreamReader(stream, StandardCharsets.UTF_8)) {
                    load(entry.getKey(), reader);
                }
            } catch (final IOException e) {
                System.err.println("Could not read resource " + entry.getValue());
            }
        }

        GlobalTranslator.translator().addSource(STORE);
    }

    private static void load(final Locale locale, final Reader reader) throws IOException {
        final Map<String, String> entries = GSON.fromJson(reader, LANGUAGE_TYPE);
        if (entries == null) {
            return;
        }
        STORE.registerAll(locale, entries);
    }

    public static void unregister() {
        GlobalTranslator.translator().removeSource(STORE);
    }
}
