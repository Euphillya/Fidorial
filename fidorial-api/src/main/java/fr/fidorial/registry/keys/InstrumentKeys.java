package fr.fidorial.registry.keys;

import fr.fidorial.registry.RegistryKey;
import fr.fidorial.registry.TypedKey;
import fr.fidorial.registry.data.Instrument;
import net.kyori.adventure.key.KeyPattern;

/**
 * Generated {@link TypedKey} constants for the {@code minecraft:instrument} registry.
 */
public final class InstrumentKeys {

    public static final TypedKey<Instrument> ADMIRE_GOAT_HORN = create("admire_goat_horn");
    public static final TypedKey<Instrument> CALL_GOAT_HORN = create("call_goat_horn");
    public static final TypedKey<Instrument> DREAM_GOAT_HORN = create("dream_goat_horn");
    public static final TypedKey<Instrument> FEEL_GOAT_HORN = create("feel_goat_horn");
    public static final TypedKey<Instrument> PONDER_GOAT_HORN = create("ponder_goat_horn");
    public static final TypedKey<Instrument> SEEK_GOAT_HORN = create("seek_goat_horn");
    public static final TypedKey<Instrument> SING_GOAT_HORN = create("sing_goat_horn");
    public static final TypedKey<Instrument> YEARN_GOAT_HORN = create("yearn_goat_horn");

    private InstrumentKeys() {
    }

    private static TypedKey<Instrument> create(@KeyPattern String value) {
        return TypedKey.create(RegistryKey.INSTRUMENT, value);
    }
}
