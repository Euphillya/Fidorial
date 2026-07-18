package fr.fidorial.permission;

import fr.fidorial.plugin.PluginManager;

import java.util.*;

public class Permission {

    public static final PermissionDefault DEFAULT_PERMISSION = PermissionDefault.OP;

    private final String name;
    private final Map<String, Boolean> children = new LinkedHashMap<>();
    private PermissionDefault defaultValue = DEFAULT_PERMISSION;
    private String description;

    private PluginManager manager;

    public Permission(String name) {
        this(name, null, null, null);
    }

    public Permission(String name, String description) {
        this(name, description, null, null);
    }

    public Permission(String name, PermissionDefault defaultValue) {
        this(name, null, defaultValue, null);
    }

    public Permission(String name, String description, PermissionDefault defaultValue) {
        this(name, description, defaultValue, null);
    }

    public Permission(String name, Map<String, Boolean> children) {
        this(name, null, null, children);
    }

    public Permission(String name, String description, Map<String, Boolean> children) {
        this(name, description, null, children);
    }

    public Permission(String name, PermissionDefault defaultValue, Map<String, Boolean> children) {
        this(name, null, defaultValue, children);
    }

    public Permission(String name, String description, PermissionDefault defaultValue,
                      Map<String, Boolean> children) {
        this.name = Objects.requireNonNull(name, "name");
        this.description = description == null ? "" : description;
        if (defaultValue != null) {
            this.defaultValue = defaultValue;
        }
        if (children != null) {
            this.children.putAll(children);
        }
        recalculatePermissibles();
    }

    public static List<Permission> loadPermissions(Map<?, ?> data, PermissionDefault def) {
        List<Permission> result = new ArrayList<>();
        for (Map.Entry<?, ?> entry : data.entrySet()) {
            result.add(loadPermission(entry.getKey().toString(), (Map<?, ?>) entry.getValue(), def, result));
        }
        return result;
    }

    public static Permission loadPermission(String name, Map<?, ?> data) {
        return loadPermission(name, data, DEFAULT_PERMISSION, null);
    }

    public static Permission loadPermission(String name, Map<?, ?> data,
                                            PermissionDefault def, java.util.List<Permission> output) {
        Objects.requireNonNull(name, "name");
        Objects.requireNonNull(data, "data");

        String desc = null;
        Map<String, Boolean> children = null;

        if (data.get("default") != null) {
            PermissionDefault value = PermissionDefault.getByName(data.get("default").toString());
            if (value == null) {
                throw new IllegalArgumentException("'default' invalide pour " + name);
            }
            def = value;
        }
        if (data.get("children") != null) {
            Object childrenNode = data.get("children");
            if (childrenNode instanceof Iterable<?> iterable) {
                children = new LinkedHashMap<>();
                for (Object child : iterable) {
                    if (child != null) {
                        children.put(child.toString(), Boolean.TRUE);
                    }
                }
            } else if (childrenNode instanceof Map<?, ?> map) {
                children = extractChildren(map, name, def, output);
            } else {
                throw new IllegalArgumentException("'children' invalide pour " + name);
            }
        }
        if (data.get("description") != null) {
            desc = data.get("description").toString();
        }
        return new Permission(name, desc, def, children);
    }

    private static Map<String, Boolean> extractChildren(Map<?, ?> input, String name,
                                                        PermissionDefault def,
                                                        java.util.List<Permission> output) {
        Map<String, Boolean> children = new LinkedHashMap<>();
        for (Map.Entry<?, ?> entry : input.entrySet()) {
            Object value = entry.getValue();
            if (value instanceof Boolean bool) {
                children.put(entry.getKey().toString(), bool);
            } else if (value instanceof Map<?, ?> map) {
                try {
                    Permission perm = loadPermission(entry.getKey().toString(), map, def, output);
                    children.put(perm.getName(), Boolean.TRUE);
                    if (output != null) {
                        output.add(perm);
                    }
                } catch (Throwable t) {
                    throw new IllegalArgumentException(
                            "Enfant '" + entry.getKey() + "' de " + name + " invalide", t);
                }
            } else {
                throw new IllegalArgumentException(
                        "Enfant '" + entry.getKey() + "' de " + name + " invalide");
            }
        }
        return children;
    }

    public String getName() {
        return name;
    }

    public Map<String, Boolean> getChildren() {
        return children;
    }

    public PermissionDefault getDefault() {
        return defaultValue;
    }

    public void setDefault(PermissionDefault value) {
        Objects.requireNonNull(value, "value");
        this.defaultValue = value;
        recalculatePermissibles();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String value) {
        this.description = value == null ? "" : value;
    }

    public Set<Permissible> getPermissibles() {
        if (manager == null) {
            return Set.of();
        }
        return manager.getPermissionSubscriptions(name);
    }

    public void recalculatePermissibles() {
        if (manager != null) {
            manager.recalculatePermissionDefaults(this);
            for (Permissible permissible : getPermissibles()) {
                permissible.recalculatePermissions();
            }
        }
    }

    public Permission addParent(String name, boolean value) {
        if (manager == null) {
            throw new IllegalStateException(
                    "Permission '" + this.name + "' non enregistree aupres d'un PluginManager");
        }
        String lname = name.toLowerCase(Locale.ROOT);
        Permission perm = manager.getPermission(lname);
        if (perm == null) {
            perm = new Permission(lname);
            manager.addPermission(perm);
        }
        addParent(perm, value);
        return perm;
    }

    public void addParent(Permission perm, boolean value) {
        perm.getChildren().put(getName(), value);
        perm.recalculatePermissibles();
    }

    public void attach(PluginManager manager) {
        this.manager = manager;
    }

    @Override
    public boolean equals(Object o) {
        return this == o || (o instanceof Permission other && name.equals(other.name));
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public String toString() {
        return "Permission{name=" + name + ", default=" + defaultValue + "}";
    }
}
