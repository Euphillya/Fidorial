package fr.fidorial.world.time;

public enum DayPhase {

    DAY(0, 12_000),
    DUSK(12_000, 13_000),
    NIGHT(13_000, 23_000),
    DAWN(23_000, 24_000);

    private final int start;
    private final int end;

    DayPhase(final int start, final int end) {
        this.start = start;
        this.end = end;
    }

    public static DayPhase at(final int timeOfDay) {
        final int tick = Math.floorMod(timeOfDay, DayNightCycle.DAY_LENGTH);
        if (tick < DUSK.start) {
            return DAY;
        }
        if (tick < NIGHT.start) {
            return DUSK;
        }
        if (tick < DAWN.start) {
            return NIGHT;
        }
        return DAWN;
    }

    public int start() {
        return start;
    }

    public int end() {
        return end;
    }

    public int lengthTicks() {
        return end - start;
    }

    public boolean daylight() {
        return this == DAY;
    }
}
