package fr.euphyllia.fidorial.api.world.block;

import fr.euphyllia.fidorial.api.world.block.data.*;
import fr.euphyllia.fidorial.api.world.block.data.type.Door;
import fr.euphyllia.fidorial.api.world.block.data.type.NoteBlock;
import fr.euphyllia.fidorial.api.world.block.data.type.Slab;
import fr.euphyllia.fidorial.api.world.block.data.type.Stairs;
import net.kyori.adventure.key.Key;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class BlockTraits {

    private static final Set<String> FACES = Set.of("north", "south", "east", "west", "up", "down");
    private static final Set<String> AXES = Set.of("x", "y", "z");
    private static final Set<String> HALVES = Set.of("top", "bottom", "upper", "lower");
    private static final Set<String> SLAB_TYPES = Set.of("top", "bottom", "double");

    private BlockTraits() {
    }

    public static List<Class<? extends BlockData>> detect(Key key, List<BlockProperty> properties) {
        List<Class<? extends BlockData>> traits = new ArrayList<>();

        BlockProperty facing = find(properties, "facing");
        BlockProperty half = find(properties, "half");
        BlockProperty type = find(properties, "type");

        if (facing != null && FACES.containsAll(facing.values())) {
            traits.add(Directional.class);
        }
        BlockProperty axis = find(properties, "axis");
        if (axis != null && AXES.containsAll(axis.values())) {
            traits.add(Orientable.class);
        }
        if (half != null && HALVES.containsAll(half.values())) {
            traits.add(Bisected.class);
        }
        if (find(properties, "waterlogged") != null) {
            traits.add(Waterlogged.class);
        }
        if (find(properties, "powered") != null) {
            traits.add(Powerable.class);
        }
        if (find(properties, "open") != null) {
            traits.add(Openable.class);
        }
        if (find(properties, "lit") != null) {
            traits.add(Lightable.class);
        }
        if (find(properties, "snowy") != null) {
            traits.add(Snowable.class);
        }
        if (find(properties, "age") != null) {
            traits.add(Ageable.class);
        }
        if (find(properties, "level") != null) {
            traits.add(Levelled.class);
        }
        if (find(properties, "rotation") != null) {
            traits.add(Rotatable.class);
        }

        if (key.asString().equals("minecraft:note_block")) {
            traits.add(NoteBlock.class);
        }
        if (facing != null && half != null && find(properties, "shape") != null
                && traits.contains(Bisected.class)) {
            traits.add(Stairs.class);
        }
        if (type != null && SLAB_TYPES.containsAll(type.values()) && type.values().size() == 3) {
            traits.add(Slab.class);
        }
        if (facing != null && half != null && find(properties, "hinge") != null
                && find(properties, "open") != null) {
            traits.add(Door.class);
        }

        return traits;
    }

    private static BlockProperty find(List<BlockProperty> properties, String name) {
        for (BlockProperty property : properties) {
            if (property.name().equals(name)) {
                return property;
            }
        }
        return null;
    }
}
