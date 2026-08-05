package fr.euphyllia.fidorial.server.plugin.deserializer;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import fr.euphyllia.fidorial.server.plugin.meta.FidorialPluginDependency;
import fr.euphyllia.fidorial.server.util.JsonHelper;
import fr.fidorial.plugin.PluginMeta;

import java.lang.reflect.Type;
import java.util.Locale;

public final class PluginDependencyDeserializer implements JsonDeserializer<PluginMeta.PluginDependency> {
    @Override
    @SuppressWarnings("PatternValidation")
    public PluginMeta.PluginDependency deserialize(
            final JsonElement element,
            final Type type,
            final JsonDeserializationContext context
    ) throws JsonParseException {
        final JsonObject object = JsonHelper.asObject(element, "plugin dependency");
        final PluginMeta.PluginDependency.RelativeLoadOrder load = switch (
                JsonHelper.getString(object, "load", "undefined").toLowerCase(Locale.ROOT)
        ) {
            case "before" -> PluginMeta.PluginDependency.RelativeLoadOrder.BEFORE;
            case "after" -> PluginMeta.PluginDependency.RelativeLoadOrder.AFTER;
            case "undefined", "none" -> PluginMeta.PluginDependency.RelativeLoadOrder.UNDEFINED;
            default -> throw new JsonParseException("Unknown dependency load order");
        };
        return new FidorialPluginDependency(
                JsonHelper.requiredString(object, "plugin"),
                JsonHelper.getString(object, "version", "*"),
                JsonHelper.optionalBoolean(object, "required", true),
                JsonHelper.optionalBoolean(object, "joinClasspath", true),
                load
        );
    }
}
