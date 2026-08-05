package fr.euphyllia.fidorial.server.util;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import org.jetbrains.annotations.Contract;
import org.jspecify.annotations.Nullable;

import java.net.URI;
import java.net.URISyntaxException;

public final class JsonHelper {
    private JsonHelper() {
    }

    public static JsonObject asObject(final JsonElement element, final String name) {
        if (element instanceof final JsonObject object) return object;
        throw new JsonParseException("'" + name + "' must be an object: " + element);
    }

    public static @Nullable JsonObject optionalObject(final JsonObject root, final String name) {
        final JsonElement element = root.get(name);
        return element != null ? asObject(element, name) : null;
    }

    public static JsonArray asArray(final JsonElement element, final String name) {
        if (element instanceof final JsonArray array) return array;
        throw new JsonParseException("'" + name + "' must be an array: " + element);
    }

    public static String requiredString(final JsonObject object, final String name) {
        final JsonElement element = object.get(name);
        if (element instanceof JsonPrimitive primitive && primitive.isString()) return primitive.getAsString();
        throw new JsonParseException("'" + name + "' must be a string: " + object);
    }

    @Contract("_, _, !null -> !null")
    public static @Nullable String getString(final JsonObject object, final String name, @Nullable String fallback) {
        final JsonElement element = object.get(name);
        if (element == null) return fallback;
        if (element instanceof JsonPrimitive primitive && primitive.isString()) return primitive.getAsString();
        throw new JsonParseException("'" + name + "' must be a string: " + element);
    }

    public static boolean optionalBoolean(final JsonObject object, final String name, final boolean fallback) {
        final JsonElement element = object.get(name);
        if (element == null) return fallback;
        if (element instanceof JsonPrimitive primitive && primitive.isBoolean()) return primitive.getAsBoolean();
        throw new JsonParseException("'" + name + "' must be a boolean: " + element);
    }

    public static @Nullable URI optionalUri(final JsonObject object, final String name) {
        final String value = getString(object, name, null);
        return value != null ? uri(value, name) : null;
    }

    public static URI uri(String value, String name) {
        try {
            return new URI(value);
        } catch (final URISyntaxException e) {
            throw new JsonParseException("'" + name + "' must be a valid URI: " + value, e);
        }
    }
}
