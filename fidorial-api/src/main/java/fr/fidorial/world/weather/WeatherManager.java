package fr.fidorial.world.weather;

public interface WeatherManager {

    Weather weather();

    boolean isRaining();

    boolean isThundering();

    void setWeather(Weather weather, int durationTicks);

    default void setWeather(Weather weather) {
        setWeather(weather, 0);
    }
}
