package fr.fidorial.world.block.data;

import fr.fidorial.world.block.BlockData;

public interface Bisected extends BlockData {

    default Half getHalf() {
        return Half.fromValue(get("half"));
    }

    default Bisected setHalf(Half half) {
        return (Bisected) with("half", half.value());
    }

    enum Half {
        TOP("top"), BOTTOM("bottom"), UPPER("upper"), LOWER("lower");

        private final String value;

        Half(String value) {
            this.value = value;
        }

        public static Half fromValue(String value) {
            for (Half half : values()) {
                if (half.value.equals(value)) {
                    return half;
                }
            }
            throw new IllegalArgumentException("Unknown half: " + value);
        }

        public String value() {
            return value;
        }
    }
}
