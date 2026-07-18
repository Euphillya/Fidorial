package fr.fidorial.world.block.data.type;

import fr.fidorial.world.BlockFace;
import fr.fidorial.world.block.data.Bisected;
import fr.fidorial.world.block.data.Directional;
import fr.fidorial.world.block.data.Waterlogged;

public interface Stairs extends Directional, Bisected, Waterlogged {

    @Override
    default Stairs setFacing(BlockFace facing) {
        return (Stairs) Directional.super.setFacing(facing);
    }

    @Override
    default Stairs setHalf(Half half) {
        return (Stairs) Bisected.super.setHalf(half);
    }

    @Override
    default Stairs setWaterlogged(boolean waterlogged) {
        return (Stairs) Waterlogged.super.setWaterlogged(waterlogged);
    }

    default Shape getShape() {
        return Shape.fromValue(get("shape"));
    }

    default Stairs setShape(Shape shape) {
        return (Stairs) with("shape", shape.value());
    }

    enum Shape {
        STRAIGHT("straight"),
        INNER_LEFT("inner_left"),
        INNER_RIGHT("inner_right"),
        OUTER_LEFT("outer_left"),
        OUTER_RIGHT("outer_right");

        private final String value;

        Shape(String value) {
            this.value = value;
        }

        public static Shape fromValue(String value) {
            for (Shape shape : values()) {
                if (shape.value.equals(value)) {
                    return shape;
                }
            }
            throw new IllegalArgumentException("Unknown stair shape: " + value);
        }

        public String value() {
            return value;
        }
    }
}
