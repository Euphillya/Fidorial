package fr.fidorial.world.time;

import net.kyori.adventure.key.Key;

public interface DayNightCycle {

    int DAY_LENGTH = 24_000;

    int SUNRISE_END = 0;

    int NOON = 6_000;

    int SUNSET = 12_000;

    int NIGHT_START = 13_000;

    int MIDNIGHT = 18_000;

    int SUNRISE = 23_000;

    int SLEEP_START = 12_542;

    int SLEEP_END = 23_459;

    int MOON_PHASES = 8;

    Key clock();

    long worldAge();

    long time();

    void setTime(long time);

    default void addTime(final long ticks) {
        setTime(time() + ticks);
    }

    float fractionalTime();

    float rate();

    void setRate(float rate);

    boolean doDaylightCycle();

    void setDoDaylightCycle(boolean enabled);

    default int timeOfDay() {
        return (int) Math.floorMod(time(), (long) DAY_LENGTH);
    }

    default long day() {
        return Math.floorDiv(time(), (long) DAY_LENGTH);
    }

    default int moonPhase() {
        return (int) Math.floorMod(day(), (long) MOON_PHASES);
    }

    default DayPhase phase() {
        return DayPhase.at(timeOfDay());
    }

    default boolean isDay() {
        return phase() == DayPhase.DAY;
    }

    default boolean isNight() {
        return phase() == DayPhase.NIGHT;
    }

    default boolean canSleep() {
        final int tick = timeOfDay();
        return tick >= SLEEP_START && tick <= SLEEP_END;
    }

    default long ticksUntil(final int targetTimeOfDay) {
        final int target = Math.floorMod(targetTimeOfDay, DAY_LENGTH);
        final int delta = target - timeOfDay();
        return delta > 0 ? delta : delta + DAY_LENGTH;
    }

    default double skyAngleDegrees() {
        final double tick = timeOfDay();
        final double turns = (tick - NOON) / (double) DAY_LENGTH;
        final double modOne = turns - Math.floor(turns);
        final double quarters = (tick - NOON) / (double) NOON;
        final double modFour = quarters - 4.0d * Math.floor(quarters / 4.0d);
        return ((1.0d - Math.cos(Math.PI * modOne)) + modFour) * 60.0d;
    }

    default double celestialAngle() {
        return skyAngleDegrees() / 360.0d;
    }
}
