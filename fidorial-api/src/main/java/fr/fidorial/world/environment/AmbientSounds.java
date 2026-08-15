package fr.fidorial.world.environment;

import net.kyori.adventure.key.Key;
import org.jspecify.annotations.Nullable;

/**
 * The {@code minecraft:audio/ambient_sounds} environment attribute, which merges the three sound
 * fields biomes used to carry before 1.21.11.
 *
 * @param loop      sound continually looped while inside, or {@code null} for none — was
 *                  {@code effects.ambient_sound}
 * @param mood      darkness ambience, or {@code null} for none — was {@code effects.mood_sound}
 * @param additions randomly played sound, or {@code null} for none — was
 *                  {@code effects.additions_sound}
 * @since 0.1.0
 */
public record AmbientSounds(
        @Nullable Key loop,
        @Nullable MoodSound mood,
        @Nullable AdditionsSound additions
) {

    /**
     * Creates an attribute carrying only a looping sound.
     *
     * @param loop the sound event key
     * @return the ambient sounds
     */
    public static AmbientSounds loop(final Key loop) {
        return new AmbientSounds(loop, null, null);
    }

    /**
     * Creates an attribute carrying only a mood sound.
     *
     * @param mood the mood sound
     * @return the ambient sounds
     */
    public static AmbientSounds mood(final MoodSound mood) {
        return new AmbientSounds(null, mood, null);
    }

    /**
     * Creates an attribute carrying a looping sound and a mood sound.
     *
     * @param loop the sound event key
     * @param mood the mood sound
     * @return the ambient sounds
     */
    public static AmbientSounds of(final Key loop, final MoodSound mood) {
        return new AmbientSounds(loop, mood, null);
    }
}
