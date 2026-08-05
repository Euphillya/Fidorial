package fr.euphyllia.fidorial.server.plugin.deserializer;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import fr.euphyllia.fidorial.server.plugin.meta.FidorialAuthor;
import fr.euphyllia.fidorial.server.util.JsonHelper;
import fr.fidorial.plugin.PluginMeta;

import java.lang.reflect.Type;
import java.net.URI;
import java.util.LinkedHashMap;
import java.util.Map;

public final class AuthorDeserializer implements JsonDeserializer<PluginMeta.Author> {
    @Override
    public PluginMeta.Author deserialize(
            final JsonElement element,
            final Type type,
            final JsonDeserializationContext context
    ) throws JsonParseException {
        if (element instanceof JsonPrimitive primitive && primitive.isString()) {
            return new FidorialAuthor(element.getAsString());
        }
        if (!(element instanceof JsonObject object)) {
            throw new JsonParseException("Author must be a JSON object or string: " + element);
        }
        final String name = JsonHelper.requiredString(object, "name");
        final URI website = JsonHelper.optionalUri(object, "website");

        final Map<String, String> contact = new LinkedHashMap<>();
        final JsonObject contactObject = JsonHelper.optionalObject(object, "contact");
        if (contactObject != null) contactObject.entrySet().forEach(entry -> {
            if (!entry.getValue().isJsonPrimitive() || !entry.getValue().getAsJsonPrimitive().isString()) {
                throw new JsonParseException("'contact." + entry.getKey() + "' must be a string");
            }
            contact.put(entry.getKey(), entry.getValue().getAsString());
        });

        return new FidorialAuthor(name, website, contact);
    }
}
