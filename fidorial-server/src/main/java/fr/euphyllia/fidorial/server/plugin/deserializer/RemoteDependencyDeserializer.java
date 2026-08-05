package fr.euphyllia.fidorial.server.plugin.deserializer;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import fr.euphyllia.fidorial.server.plugin.meta.FidorialRemoteDependency;
import fr.euphyllia.fidorial.server.util.JsonHelper;
import fr.fidorial.plugin.PluginMeta;
import org.eclipse.aether.graph.Exclusion;

import java.lang.reflect.Type;
import java.net.URI;
import java.util.LinkedHashSet;
import java.util.Set;

public final class RemoteDependencyDeserializer implements JsonDeserializer<PluginMeta.RemoteDependency> {
    @Override
    public PluginMeta.RemoteDependency deserialize(
            final JsonElement element,
            final Type type,
            final JsonDeserializationContext context
    ) throws JsonParseException {
        final JsonObject object = JsonHelper.asObject(element, "remote dependency");
        final Set<URI> repositories = new LinkedHashSet<>();
        final JsonElement repositoriesElement = object.get("repositories");
        if (repositoriesElement != null && !repositoriesElement.isJsonNull()) {
            final JsonArray repositoriesArray = JsonHelper.asArray(repositoriesElement, "repositories");
            for (final JsonElement repositoryElement : repositoriesArray) {
                if (!repositoryElement.isJsonPrimitive() || !repositoryElement.getAsJsonPrimitive().isString()) {
                    throw new JsonParseException("'repositories' entries must be strings");
                }
                repositories.add(JsonHelper.uri(repositoryElement.getAsString(), "repositories"));
            }
        }

        final Set<Exclusion> excludes = new LinkedHashSet<>();
        final JsonElement excludesElement = object.get("excludes");
        if (excludesElement != null && !excludesElement.isJsonNull()) {
            final JsonArray excludesArray = JsonHelper.asArray(excludesElement, "excludes");
            for (final JsonElement excludeElement : excludesArray) {
                excludes.add(exclusion(excludeElement));
            }
        }

        return new FidorialRemoteDependency(
                repositories,
                excludes,
                JsonHelper.requiredString(object, "groupId"),
                JsonHelper.requiredString(object, "artifactId"),
                JsonHelper.requiredString(object, "version")
        );
    }

    private static Exclusion exclusion(final JsonElement element) {
        if (element.isJsonPrimitive() && element.getAsJsonPrimitive().isString()) {
            final String[] parts = element.getAsString().split(":", -1);
            if (parts.length < 2 || parts.length > 4) {
                throw new JsonParseException("Exclusion strings must use groupId:artifactId[:classifier[:extension]]");
            }
            return new Exclusion(
                    parts[0],
                    parts[1],
                    parts.length > 2 && !parts[2].isEmpty() ? parts[2] : "*",
                    parts.length > 3 && !parts[3].isEmpty() ? parts[3] : "*"
            );
        }
        final JsonObject object = JsonHelper.asObject(element, "exclusion");
        return new Exclusion(
                JsonHelper.requiredString(object, "groupId"),
                JsonHelper.requiredString(object, "artifactId"),
                JsonHelper.getString(object, "classifier", "*"),
                JsonHelper.getString(object, "extension", "*")
        );
    }
}
