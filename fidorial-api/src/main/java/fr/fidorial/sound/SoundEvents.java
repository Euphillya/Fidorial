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

    // --- Zombie ---
    public static final Sound.Type ZOMBIE_AMBIENT = of("entity.zombie.ambient");
    public static final Sound.Type ZOMBIE_HURT = of("entity.zombie.hurt");
    public static final Sound.Type ZOMBIE_DEATH = of("entity.zombie.death");
    public static final Sound.Type ZOMBIE_STEP = of("entity.zombie.step");
    public static final Sound.Type ZOMBIE_INFECT = of("entity.zombie.infect");
    public static final Sound.Type ZOMBIE_ATTACK_WOODEN_DOOR = of("entity.zombie.attack_wooden_door");
    public static final Sound.Type ZOMBIE_BREAK_WOODEN_DOOR = of("entity.zombie.break_wooden_door");
    public static final Sound.Type ZOMBIE_DESTROY_EGG = of("entity.zombie.destroy_egg");
    public static final Sound.Type ZOMBIE_CONVERTED_TO_DROWNED = of("entity.zombie.converted_to_drowned");

    // --- Generic ---
    public static final Sound.Type GENERIC_EXPLODE = of("entity.generic.explode");
    public static final Sound.Type GENERIC_BURN = of("entity.generic.burn");
    public static final Sound.Type PLAYER_ATTACK_STRONG = of("entity.player.attack.strong");

    private SoundEvents() {
    }

    public static Sound.Type of(@KeyPattern final String path) {
        final Key key = Key.key(path);
        return () -> key;
    }
}
