package fr.euphyllia.fidorial.server.entity;

import fr.fidorial.entity.Entity;
import net.kyori.adventure.sound.Sound;

public interface Category {

    interface Monster extends Entity {
        @Override
        default Sound.Source soundSource() {
            return Sound.Source.HOSTILE;
        }
    }

    interface Ambient extends Entity {
        @Override
        default Sound.Source soundSource() {
            return Sound.Source.AMBIENT;
        }
    }

    interface Neutral extends Entity {
        @Override
        default Sound.Source soundSource() {
            return Sound.Source.NEUTRAL;
        }
    }
}
