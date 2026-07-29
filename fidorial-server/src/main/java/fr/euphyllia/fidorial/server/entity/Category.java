package fr.euphyllia.fidorial.server.entity;

import net.kyori.adventure.sound.Sound;

public interface Category {

    interface Monster extends fr.fidorial.entity.Entity {
        @Override
        default Sound.Source soundSource() {
            return Sound.Source.HOSTILE;
        }
    }

    interface Ambient extends fr.fidorial.entity.Entity {
        @Override
        default Sound.Source soundSource() {
            return Sound.Source.AMBIENT;
        }
    }

    interface Neutral extends fr.fidorial.entity.Entity {
        @Override
        default Sound.Source soundSource() {
            return Sound.Source.NEUTRAL;
        }
    }
}
