package fr.fidorial.sound;

import net.kyori.adventure.key.Key;
import net.kyori.adventure.key.KeyPattern;
import net.kyori.adventure.sound.Sound;

public class SoundEvents {

    // --- Creeper ---
    public static final Sound.Type CREEPER_PRIMED = of("entity.creeper.primed");
    public static final Sound.Type CREEPER_HURT = of("entity.creeper.hurt");
    public static final Sound.Type CREEPER_DEATH = of("entity.creeper.death");

    // --- Generic ---
    public static final Sound.Type GENERIC_EXPLODE = of("entity.generic.explode");

    private SoundEvents() {
    }

    public static Sound.Type of(@KeyPattern String path) {
        final Key key = Key.key(path);
        return () -> key;
    }
}
