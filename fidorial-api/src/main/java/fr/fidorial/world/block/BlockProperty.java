package fr.fidorial.world.block;

import java.util.List;

public record BlockProperty(String name, List<String> values) {

    public BlockProperty {
        values = List.copyOf(values);
        if (values.isEmpty()) {
            throw new IllegalArgumentException("Property '" + name + "' must have at least one value");
        }
    }

    public int indexOf(String value) {
        return values.indexOf(value);
    }

    public boolean isValid(String value) {
        return values.contains(value);
    }
}
