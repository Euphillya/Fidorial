package fr.euphyllia.fidorial.server.command.defaults;

import fr.fidorial.command.CommandExecutor;
import fr.fidorial.command.CommandSender;
import fr.fidorial.world.weather.Weather;
import fr.euphyllia.fidorial.server.FidorialServer;
import fr.euphyllia.fidorial.server.world.weather.WeatherEngine;
import net.kyori.adventure.text.Component;

import java.util.Locale;

/**
 * /weather                     -> affiche la meteo courante
 * /weather clear|rain|thunder  -> change la meteo (duree aleatoire vanilla)
 * /weather rain 300            -> change la meteo pour 300 secondes
 */
public final class WeatherCommand implements CommandExecutor {

    private static Component describe(Weather w) {
        return switch (w) {
            case CLEAR -> Component.translatable("weather.clear");
            case RAIN -> Component.translatable("weather.rain");
            case THUNDER -> Component.translatable("weather.thunder");
        };
    }

    @Override
    public void execute(CommandSender sender, String label, String[] args) {
        if (!sender.hasPermission("fidorial.command.weather")) {
            sender.sendMessage(Component.translatable("command.error.nopermission"));
            return;
        }
        WeatherEngine weather = FidorialServer.getInstance().weatherEngine();

        if (args.length == 0) {
            sender.sendMessage(Component.translatable("command.weather.current", describe(weather.weather())));
            return;
        }

        Weather target;
        switch (args[0].toLowerCase(Locale.ROOT)) {
            case "clear", "sun", "beau" -> target = Weather.CLEAR;
            case "rain", "pluie" -> target = Weather.RAIN;
            case "thunder", "storm", "orage" -> target = Weather.THUNDER;
            case "get" -> {
                sender.sendMessage(Component.translatable("command.weather.current", describe(weather.weather())));
                return;
            }
            default -> {
                sender.sendMessage(Component.translatable("command.weather.usage", Component.text(label)));
                return;
            }
        }

        int durationTicks = 0;
        if (args.length >= 2) {
            try {
                durationTicks = Math.multiplyExact(Integer.parseInt(args[1]), 20);
            } catch (NumberFormatException | ArithmeticException e) {
                sender.sendMessage(Component.translatable("command.weather.invalidduration", Component.text(args[1])));
                return;
            }
        }

        weather.setWeather(target, durationTicks);
        if (durationTicks > 0) {
            sender.sendMessage(Component.translatable("command.weather.changed.duration",
                    describe(target), Component.text(durationTicks / 20)));
        } else {
            sender.sendMessage(Component.translatable("command.weather.changed", describe(target)));
        }
    }
}
