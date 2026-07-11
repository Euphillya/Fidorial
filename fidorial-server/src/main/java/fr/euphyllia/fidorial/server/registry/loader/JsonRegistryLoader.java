package fr.euphyllia.fidorial.server.registry.loader;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import fr.euphyllia.fidorial.server.registry.RegistryHolder;
import fr.euphyllia.fidorial.server.registry.RegistryLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public abstract class JsonRegistryLoader implements RegistryLoader {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    protected abstract String resource();

    protected abstract String missingResourceMessage();

    protected abstract RegistryHolder parse(JsonObject root);

    @Override
    public final RegistryHolder load() {
        try (InputStream in = JsonRegistryLoader.class.getResourceAsStream(resource())) {
            if (in == null) {
                logger.warn(missingResourceMessage());
                return RegistryHolder.empty();
            }
            JsonObject root = JsonParser.parseReader(
                    new InputStreamReader(in, StandardCharsets.UTF_8)).getAsJsonObject();
            return parse(root);
        } catch (Exception e) {
            logger.error("{} illisible", resource(), e);
            return RegistryHolder.empty();
        }
    }
}
