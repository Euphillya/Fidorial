package fr.euphyllia.fidorial.server.plugin.deserializer;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import fr.euphyllia.fidorial.server.plugin.meta.FidorialPluginMeta;
import fr.euphyllia.fidorial.server.util.JsonHelper;
import fr.fidorial.plugin.PluginMeta;
import org.jspecify.annotations.Nullable;

import java.lang.reflect.Type;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public final class PluginMetaDeserializer implements JsonDeserializer<PluginMeta> {
    @Override
    public PluginMeta deserialize(
            final JsonElement element,
            final Type type,
            final JsonDeserializationContext context
    ) throws JsonParseException {
        final JsonObject object = JsonHelper.asObject(element, "plugin meta");
        return new FidorialPluginMeta.Builder()
                .id(JsonHelper.requiredString(object, "id"))
                .name(JsonHelper.getString(object, "name", null))
                .description(JsonHelper.getString(object, "description", null))
                .version(JsonHelper.requiredString(object, "version"))
                .mainClass(mainClass(JsonHelper.requiredString(object, "main")))
                .url(JsonHelper.optionalUri(object, "url"))
                .license(JsonHelper.getString(object, "license", null))
                .apiVersion(JsonHelper.getString(object, "apiVersion", "*"))
                .providedPlugins(strings(object.get("provided"), "provided"))
                .authors(authors(object.get("authors"), context))
                .dependencies(dependencies(object, context))
                .permissions(permissions(object.get("permissions")))
                .build();
    }

    private static Class<?> mainClass(final String className) {
        final ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        try {
            return Class.forName(
                    className,
                    false,
                    classLoader != null ? classLoader : ClassLoader.getSystemClassLoader());
        } catch (final ClassNotFoundException e) {
            throw new JsonParseException("'main' class not found: " + className, e);
        }
    }

    private static Set<String> strings(@Nullable final JsonElement element, final String name) {
        final Set<String> values = new LinkedHashSet<>();
        if (element == null || element.isJsonNull()) {
            return values;
        }
        final JsonArray array = JsonHelper.asArray(element, name);
        for (final JsonElement entry : array) {
            if (!entry.isJsonPrimitive() || !entry.getAsJsonPrimitive().isString()) {
                throw new JsonParseException("'" + name + "' entries must be strings");
            }
            values.add(entry.getAsString());
        }
        return values;
    }

    private static Set<PluginMeta.Author> authors(
            @Nullable final JsonElement element,
            final JsonDeserializationContext context
    ) {
        final Set<PluginMeta.Author> authors = new LinkedHashSet<>();
        if (element == null || element.isJsonNull()) {
            return authors;
        }
        final JsonArray array = JsonHelper.asArray(element, "authors");
        for (final JsonElement entry : array) {
            authors.add(context.deserialize(entry, PluginMeta.Author.class));
        }
        return authors;
    }

    private static Set<PluginMeta.Dependency> dependencies(
            final JsonObject object,
            final JsonDeserializationContext context
    ) {
        final Set<PluginMeta.Dependency> dependencies = new LinkedHashSet<>();
        final JsonElement dependenciesElement = object.get("dependencies");
        if (dependenciesElement != null && !dependenciesElement.isJsonNull()) {
            final JsonArray array = JsonHelper.asArray(dependenciesElement, "dependencies");
            for (final JsonElement entry : array) {
                dependencies.add(context.deserialize(entry, PluginMeta.Dependency.class));
            }
        }
        for (final String dependency : strings(object.get("depends"), "depends")) {
            dependencies.add(context.deserialize(newDependencyObject("plugin", dependency), PluginMeta.Dependency.class));
        }
        return dependencies;
    }

    private static JsonObject newDependencyObject(final String property, final String value) {
        final JsonObject object = new JsonObject();
        object.addProperty(property, value);
        return object;
    }

    private static Set<PluginMeta.PermissionEntry> permissions(@Nullable final JsonElement element) {
        final Set<PluginMeta.PermissionEntry> permissions = new LinkedHashSet<>();
        if (element == null || element.isJsonNull()) {
            return permissions;
        }
        final JsonObject object = JsonHelper.asObject(element, "permissions");
        final PermissionEntryDeserializer deserializer = new PermissionEntryDeserializer();
        for (final Map.Entry<String, JsonElement> entry : object.entrySet()) {
            permissions.add(deserializer.deserialize(entry.getKey(), entry.getValue()));
        }
        return permissions;
    }
}
