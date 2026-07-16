package fr.euphyllia.fidorial.api.permission;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public enum PermissionDefault {

    TRUE("true"),
    FALSE("false"),
    OP("op", "isop", "operator", "isoperator"),
    NOT_OP("!op", "notop", "!operator", "notoperator");

    private static final Map<String, PermissionDefault> LOOKUP = new HashMap<>();

    static {
        for (PermissionDefault value : values()) {
            for (String name : value.names) {
                LOOKUP.put(name, value);
            }
        }
    }

    private final String[] names;

    PermissionDefault(String... names) {
        this.names = names;
    }

    public static PermissionDefault getByName(String name) {
        if (name == null) {
            return null;
        }
        return LOOKUP.get(name.toLowerCase(Locale.ROOT).replaceAll("[\"']", ""));
    }

    public boolean getValue(boolean op) {
        return switch (this) {
            case TRUE -> true;
            case FALSE -> false;
            case OP -> op;
            case NOT_OP -> !op;
        };
    }

    @Override
    public String toString() {
        return names[0];
    }

}
