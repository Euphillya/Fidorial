package fr.euphyllia.fidorial.api.world.block.data.type;

import fr.euphyllia.fidorial.api.world.block.data.Waterlogged;

public interface Slab extends Waterlogged {

    @Override
    default Slab setWaterlogged(boolean waterlogged) {
        return (Slab) Waterlogged.super.setWaterlogged(waterlogged);
    }

    default Type getType() {
        return Type.fromValue(get("type"));
    }

    default Slab setType(Type type) {
        return (Slab) with("type", type.value());
    }

    enum Type {
        TOP("top"), BOTTOM("bottom"), DOUBLE("double");

        private final String value;

        Type(String value) {
            this.value = value;
        }

        public static Type fromValue(String value) {
            for (Type type : values()) {
                if (type.value.equals(value)) {
                    return type;
                }
            }
            throw new IllegalArgumentException("Unknown slab type: " + value);
        }

        public String value() {
            return value;
        }
    }
}
