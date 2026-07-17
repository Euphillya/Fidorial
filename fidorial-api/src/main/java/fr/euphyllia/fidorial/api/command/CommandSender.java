package fr.euphyllia.fidorial.api.command;

import fr.euphyllia.fidorial.api.permission.Permissible;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TranslatableComponent;

import java.util.Locale;

public interface CommandSender extends Permissible, Audience {

    String name();

    void setLocale(final String language);

    void setLocale(final Locale locale);

    Locale locale();

    void sendMessage(final TranslatableComponent message);

    default boolean isConsole() {
        return false;
    }
}
