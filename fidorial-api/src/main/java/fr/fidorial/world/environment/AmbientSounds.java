package fr.fidorial.world.environment;

import net.kyori.adventure.key.Key;
import org.jspecify.annotations.Nullable;

import java.util.List;

/**
 * The {@code minecraft:audio/ambient_sounds} environment attribute, which merges the three sound
 * fields biomes used to carry before 1.21.11.
 *
 * @param loop      sound continually looped while inside, or {@code null} for none — was
 *                  {@code effects.ambient_sound}
 * @param mood      darkness ambience, or {@code null} for none — was {@code effects.mood_sound}
 * @param additions randomly played sounds, possibly empty — was {@code effects.additions_sound}
 * @since 0.1.0
 */
public record AmbientSounds(
        @Nullable Key loop,
        @Nullable MoodSound mood,
        List<AdditionsSound> additions
) {

    public AmbientSounds {
        additions = List.copyOf(additions);
    }

    /**
     * Creates an attribute carrying only a looping sound.
     *
     * @param loop the sound event key
     * @return the ambient sounds
     */
    public static AmbientSounds loop(final Key loop) {
        return new AmbientSounds(loop, null, List.of());
    }

    /**
     * Creates an attribute carrying only a mood sound.
     *
     * @param mood the mood sound
     * @return the ambient sounds
     */
    public static AmbientSounds mood(final MoodSound mood) {
        return new AmbientSounds(null, mood, List.of());
    }

    /**
     * Creates an attribute carrying a looping sound and a mood sound.
     *
     * @param loop the sound event key
     * @param mood the mood sound
     * @return the ambient sounds
     */
    public static AmbientSounds of(final Key loop, final MoodSound mood) {
        return new AmbientSounds(loop, mood, List.of());
    }
}
