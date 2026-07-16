package fr.euphyllia.fidorial.api.world.block.data;

import fr.euphyllia.fidorial.api.world.block.BlockData;
import fr.euphyllia.fidorial.api.world.block.BlockProperty;

public interface Ageable extends BlockData {

    default int getAge() {
        return Integer.parseInt(get("age"));
    }

    default Ageable setAge(int age) {
        return (Ageable) with("age", String.valueOf(age));
    }

    default int getMaximumAge() {
        BlockProperty property = type().property("age");
        return Integer.parseInt(property.values().getLast());
    }
}
