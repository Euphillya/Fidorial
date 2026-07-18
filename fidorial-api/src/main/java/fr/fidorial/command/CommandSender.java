package fr.fidorial.command;

import fr.fidorial.permission.Permissible;
import net.kyori.adventure.audience.Audience;

import java.util.Locale;

public interface CommandSender extends Permissible, Audience {

    String name();

    void setLocale(final String language);

    void setLocale(final Locale locale);

    Locale locale();

    default boolean isConsole() {
        return false;
    }
}
