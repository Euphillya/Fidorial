package fr.fidorial.sound;

import net.kyori.adventure.key.Key;
import net.kyori.adventure.key.KeyPattern;
import net.kyori.adventure.sound.Sound;

public class SoundEvents {

    // --- Creeper ---
    public static final Sound.Type CREEPER_PRIMED = of("entity.creeper.primed");
    public static final Sound.Type CREEPER_HURT = of("entity.creeper.hurt");
    public static final Sound.Type CREEPER_DEATH = of("entity.creeper.death");

    // --- Chicken ---
    public static final Sound.Type CHICKEN_AMBIENT = of("entity.chicken.ambient");
    public static final Sound.Type CHICKEN_HURT = of("entity.chicken.hurt");
    public static final Sound.Type CHICKEN_DEATH = of("entity.chicken.death");
    public static final Sound.Type CHICKEN_STEP = of("entity.chicken.step");
    public static final Sound.Type CHICKEN_EGG = of("entity.chicken.egg");


    // --- Generic ---
    public static final Sound.Type GENERIC_EXPLODE = of("entity.generic.explode");

    private SoundEvents() {
    }

    public static Sound.Type of(@KeyPattern final String path) {
        final Key key = Key.key(path);
        return () -> key;
    }
}
