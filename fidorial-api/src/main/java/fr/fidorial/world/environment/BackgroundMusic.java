package fr.fidorial.world.environment;

import org.jspecify.annotations.Nullable;

/**
 * The {@code minecraft:audio/background_music} environment attribute.
 *
 * <p>Since 1.21.11 this replaced the weighted music list biomes used to carry: instead of picking
 * randomly among several tracks, the client picks the variant matching the player's situation.</p>
 *
 * @param normal     track played by default, or {@code null} for silence — the {@code default} field
 *                   on the wire, renamed here because {@code default} is a Java keyword
 * @param underwater track overriding {@code normal} while the player is submerged, or {@code null}
 * @param creative   track overriding {@code normal} in Creative mode, or {@code null}
 * @since 0.1.0
 */
public record BackgroundMusic(
        @Nullable MusicTrack normal,
        @Nullable MusicTrack underwater,
        @Nullable MusicTrack creative
) {

    /**
     * Creates an attribute carrying a single track for every situation.
     *
     * @param track the track
     * @return the background music
     */
    public static BackgroundMusic of(final MusicTrack track) {
        return new BackgroundMusic(track, null, null);
    }
}
