package fr.fidorial.entity;

import java.util.Locale;

/**
 * Modes de jeu vanilla, avec les identifiants reseau du protocole.
 */
public enum GameMode {

    SURVIVAL(0),
    CREATIVE(1),
    ADVENTURE(2),
    SPECTATOR(3);

    private final int id;

    GameMode(int id) {
        this.id = id;
    }

    public static GameMode byId(int id) {
        for (GameMode mode : values()) {
            if (mode.id == id) {
                return mode;
            }
        }
        return null;
    }

    public static GameMode byName(String input) {
        if (input == null || input.isBlank()) {
            return null;
        }
        return switch (input.toLowerCase(Locale.ROOT)) {
            case "survival", "survie", "s", "0" -> SURVIVAL;
            case "creative", "creatif", "créatif", "c", "1" -> CREATIVE;
            case "adventure", "aventure", "a", "2" -> ADVENTURE;
            case "spectator", "spectateur", "sp", "3" -> SPECTATOR;
            default -> null;
        };
    }

    public int id() {
        return id;
    }
}
