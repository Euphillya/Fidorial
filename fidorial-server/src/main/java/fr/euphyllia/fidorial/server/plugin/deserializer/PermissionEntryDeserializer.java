package fr.euphyllia.fidorial.server.plugin.deserializer;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import fr.euphyllia.fidorial.server.plugin.meta.FidorialPermissionEntry;
import fr.euphyllia.fidorial.server.util.JsonHelper;
import fr.fidorial.permission.PermissionNode;
import fr.fidorial.plugin.PluginMeta;
import org.jspecify.annotations.Nullable;

import java.lang.reflect.Type;
import java.util.LinkedHashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

public final class PermissionEntryDeserializer implements JsonDeserializer<PluginMeta.PermissionEntry> {
    @Override
    public PluginMeta.PermissionEntry deserialize(
            final JsonElement element,
            final Type type,
            final JsonDeserializationContext context
    ) throws JsonParseException {
        final JsonObject object = JsonHelper.asObject(element, "permission");
        return deserialize(object, JsonHelper.requiredString(object, "permission"));
    }

    public PluginMeta.PermissionEntry deserialize(final String permission, final JsonElement element) {
        return deserialize(JsonHelper.asObject(element, permission), permission);
    }

    private PluginMeta.PermissionEntry deserialize(final JsonObject object, final String permission) {
        final PermissionNode node;
        try {
            node = PermissionNode.of(permission);
        } catch (final IllegalArgumentException e) {
            throw new JsonParseException("Invalid permission node '" + permission + "'", e);
        }

        final String description = JsonHelper.getString(object, "description", null);
        final PluginMeta.PermissionEntry.Scope scope = scope(object);
        final Set<PluginMeta.PermissionEntry> children = new LinkedHashSet<>();
        final JsonElement childrenElement = object.get("children");
        if (childrenElement != null && !childrenElement.isJsonNull()) {
            final JsonObject childrenObject = JsonHelper.asObject(childrenElement, "children");
            for (final Map.Entry<String, JsonElement> child : childrenObject.entrySet()) {
                children.add(deserialize(child.getKey(), child.getValue()));
            }
        }
        return new FidorialPermissionEntry(node, description, scope, children);
    }

    private static PluginMeta.PermissionEntry.Scope scope(final JsonObject object) {
        final JsonElement scopeElement = object.get("scope");
        if (scopeElement != null && !scopeElement.isJsonNull()) {
            if (!scopeElement.isJsonPrimitive() || !scopeElement.getAsJsonPrimitive().isString()) {
                throw new JsonParseException("'scope' must be a string");
            }
            return parseScope(scopeElement.getAsString());
        }
        final Boolean regular = optionalTriBoolean(object, "regular");
        final Boolean operator = optionalTriBoolean(object, "operator");
        if (Boolean.TRUE.equals(regular) && Boolean.TRUE.equals(operator)) {
            return PluginMeta.PermissionEntry.Scope.TRUE;
        }
        if (Boolean.FALSE.equals(regular) && Boolean.FALSE.equals(operator)) {
            return PluginMeta.PermissionEntry.Scope.FALSE;
        }
        if ((regular == null || !regular) && Boolean.TRUE.equals(operator)) {
            return PluginMeta.PermissionEntry.Scope.OP;
        }
        return PluginMeta.PermissionEntry.Scope.NOT_SET;
    }

    private static PluginMeta.PermissionEntry.Scope parseScope(final String value) {
        return switch (value.toLowerCase(Locale.ROOT)) {
            case "op" -> PluginMeta.PermissionEntry.Scope.OP;
            case "true" -> PluginMeta.PermissionEntry.Scope.TRUE;
            case "false" -> PluginMeta.PermissionEntry.Scope.FALSE;
            case "not_set", "not-set", "none" -> PluginMeta.PermissionEntry.Scope.NOT_SET;
            default -> throw new JsonParseException("Unknown permission scope '" + value + "'");
        };
    }

    private static @Nullable Boolean optionalTriBoolean(final JsonObject object, final String name) {
        final JsonElement element = object.get(name);
        if (element == null || element.isJsonNull()) {
            return null;
        }
        if (element.isJsonPrimitive() && element.getAsJsonPrimitive().isBoolean()) {
            return element.getAsBoolean();
        }
        if (element.isJsonPrimitive() && element.getAsJsonPrimitive().isString()) {
            return switch (element.getAsString().toUpperCase(Locale.ROOT)) {
                case "TRUE" -> true;
                case "FALSE" -> false;
                case "NOT_SET" -> null;
                default -> throw new JsonParseException("'" + name + "' must be TRUE, FALSE, or NOT_SET");
            };
        }
        throw new JsonParseException("'" + name + "' must be a boolean or tri-state string");
    }
}
