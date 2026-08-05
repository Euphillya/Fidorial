package fr.euphyllia.fidorial.server.plugin.deserializer;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import fr.euphyllia.fidorial.server.plugin.meta.FidorialJarDependency;
import fr.euphyllia.fidorial.server.util.JsonHelper;
import fr.fidorial.plugin.PluginMeta;

import java.lang.reflect.Type;
import java.nio.file.Path;

public final class JarDependencyDeserializer implements JsonDeserializer<PluginMeta.JarDependency> {
    @Override
    public PluginMeta.JarDependency deserialize(
            final JsonElement element,
            final Type type,
            final JsonDeserializationContext context
    ) throws JsonParseException {
        final JsonObject object = JsonHelper.asObject(element, "jar dependency");
        return new FidorialJarDependency(Path.of(JsonHelper.requiredString(object, "jar")));
    }
}
