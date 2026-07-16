package fr.euphyllia.fidorial.server.command.defaults;

import fr.euphyllia.fidorial.api.command.CommandExecutor;
import fr.euphyllia.fidorial.api.command.CommandSender;
import fr.euphyllia.fidorial.api.world.weather.Weather;
import fr.euphyllia.fidorial.server.FidorialServer;
import fr.euphyllia.fidorial.server.world.weather.WeatherEngine;

import java.util.Locale;

/**
 * /weather                     -> affiche la meteo courante
 * /weather clear|rain|thunder  -> change la meteo (duree aleatoire vanilla)
 * /weather rain 300            -> change la meteo pour 300 secondes
 */
public final class WeatherCommand implements CommandExecutor {

    private static String describe(Weather w) {
        return switch (w) {
            case CLEAR -> "beau temps";
            case RAIN -> "pluie";
            case THUNDER -> "orage";
        };
    }

    @Override
    public void execute(CommandSender sender, String label, String[] args) {
        if (!sender.hasPermission("fidorial.command.weather")) {
            sender.sendMessage("Vous n'avez pas la permission d'utiliser cette commande.");
            return;
        }
        WeatherEngine weather = FidorialServer.getInstance().weatherEngine();
        if (weather == null) {
            sender.sendMessage("Le moteur meteo n'est pas encore demarre.");
            return;
        }

        if (args.length == 0) {
            sender.sendMessage("Meteo actuelle : " + describe(weather.weather()));
            return;
        }

        Weather target;
        switch (args[0].toLowerCase(Locale.ROOT)) {
            case "clear", "sun", "beau" -> target = Weather.CLEAR;
            case "rain", "pluie" -> target = Weather.RAIN;
            case "thunder", "storm", "orage" -> target = Weather.THUNDER;
            case "get" -> {
                sender.sendMessage("Meteo actuelle : " + describe(weather.weather()));
                return;
            }
            default -> {
                sender.sendMessage("Usage : /" + label + " [clear|rain|thunder] [duree en secondes]");
                return;
            }
        }

        int durationTicks = 0;
        if (args.length >= 2) {
            try {
                durationTicks = Math.multiplyExact(Integer.parseInt(args[1]), 20);
            } catch (NumberFormatException | ArithmeticException e) {
                sender.sendMessage("Duree invalide : " + args[1]);
                return;
            }
        }

        weather.setWeather(target, durationTicks);
        sender.sendMessage("Meteo changee : " + describe(target)
                + (durationTicks > 0 ? " pendant " + (durationTicks / 20) + " s" : ""));
    }
}
