package fr.euphyllia.fidorial.server.world.nbt;

/**
 * Voir <a href="https://minecraft.wiki/w/NBT_format">NBT format</a>.
 */
public enum NbtType {
    END(0),
    BYTE(1),
    SHORT(2),
    INT(3),
    LONG(4),
    FLOAT(5),
    DOUBLE(6),
    BYTE_ARRAY(7),
    STRING(8),
    LIST(9),
    COMPOUND(10),
    INT_ARRAY(11),
    LONG_ARRAY(12);

    private final int id;

    NbtType(int id) {
        this.id = id;
    }

    public static NbtType byId(int id) {
        for (NbtType t : values()) {
            if (t.id == id) return t;
        }
        throw new IllegalArgumentException("Type NBT inconnu : " + id);
    }

    public int id() {
        return id;
    }
}
