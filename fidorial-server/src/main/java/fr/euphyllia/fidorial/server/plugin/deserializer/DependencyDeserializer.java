package fr.euphyllia.fidorial.server.plugin.deserializer;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import fr.euphyllia.fidorial.server.util.JsonHelper;
import fr.fidorial.plugin.PluginMeta;

import java.lang.reflect.Type;

public final class DependencyDeserializer implements JsonDeserializer<PluginMeta.Dependency> {
    @Override
    public PluginMeta.Dependency deserialize(
            final JsonElement element,
            final Type type,
            final JsonDeserializationContext context
    ) throws JsonParseException {
        final JsonObject object = JsonHelper.asObject(element, "dependency");
        if (object.has("plugin")) {
            return context.deserialize(element, PluginMeta.PluginDependency.class);
        }
        if (object.has("jar")) {
            return context.deserialize(element, PluginMeta.JarDependency.class);
        }
        if (object.has("groupId") || object.has("artifactId")) {
            return context.deserialize(element, PluginMeta.RemoteDependency.class);
        }
        throw new JsonParseException("Dependency must declare 'plugin', 'jar', or Maven coordinates");
    }
}
