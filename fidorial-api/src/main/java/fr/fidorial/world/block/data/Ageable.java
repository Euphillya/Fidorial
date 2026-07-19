package fr.fidorial.world.block.data;

import fr.fidorial.world.block.BlockData;
import fr.fidorial.world.block.BlockProperty;

public interface Ageable extends BlockData {

    default int getAge() {
        return Integer.parseInt(get("age"));
    }

    default Ageable setAge(int age) {
        return (Ageable) with("age", String.valueOf(age));
    }

    default int getMaximumAge() {
        BlockProperty property = type().property("age");
        if (property == null) {
            throw new IllegalStateException("Cannot determine maximum age");
        }
        return Integer.parseInt(property.values().getLast());
    }
}
