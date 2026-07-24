package fr.euphyllia.fidorial.server.command.defaults;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.tree.LiteralCommandNode;
import fr.euphyllia.fidorial.server.FidorialServer;
import fr.euphyllia.fidorial.server.world.weather.WeatherEngine;
import fr.fidorial.command.CommandSource;
import fr.fidorial.command.CommandTree;
import fr.fidorial.command.argument.ArgumentTypes;
import fr.fidorial.world.weather.Weather;
import net.kyori.adventure.text.Component;

import java.util.List;

/**
 * /weather                     -> affiche la meteo courante
 * /weather clear|rain|thunder  -> change la meteo (duree aleatoire vanilla)
 * /weather rain 300s            -> change la meteo pour 300 secondes
 */
public final class WeatherCommand {

    private WeatherCommand() {
    }

    private static Component describe(Weather weather) {
        return switch (weather) {
            case CLEAR -> Component.translatable("weather.clear");
            case RAIN -> Component.translatable("weather.rain");
            case THUNDER -> Component.translatable("weather.thunder");
        };
    }

    public static CommandTree create() {
        LiteralCommandNode<CommandSource> command = CommandTree.literal("weather")
                .requires(source -> source.sender().hasPermission("fidorial.command.weather"))
                .then(CommandTree.literal("get").executes(WeatherCommand::get))
                .then(weather("clear", Weather.CLEAR))
                .then(weather("rain", Weather.RAIN))
                .then(weather("thunder", Weather.THUNDER))
                .build();
        return new CommandTree(command, List.of("weather", "w"));
    }

    private static LiteralCommandNode<CommandSource> weather(String name, Weather weather) {
        return CommandTree.literal(name)
                .executes(context -> set(context.getSource(), weather, 0))
                .then(CommandTree.argument("duration", ArgumentTypes.time(0))
                        .executes(context ->
                                set(context.getSource(), weather, context.getArgument("duration", Integer.class))))
                .build();
    }

    private static int get(CommandContext<CommandSource> context) {
        WeatherEngine weather = FidorialServer.getInstance().weatherEngine();
        context.getSource()
                .sender()
                .sendMessage(Component.translatable("command.weather.current", describe(weather.weather())));
        return Command.SINGLE_SUCCESS;
    }

    private static int set(CommandSource source, Weather target, int durationTicks) {
        WeatherEngine weather = FidorialServer.getInstance().weatherEngine();
        weather.setWeather(target, durationTicks);

        if (durationTicks > 0) {
            source.sender()
                    .sendMessage(Component.translatable(
                            "command.weather.changed.duration", describe(target), Component.text(durationTicks / 20)));
        } else {
            source.sender().sendMessage(Component.translatable("command.weather.changed", describe(target)));
        }
        return Command.SINGLE_SUCCESS;
    }
}
