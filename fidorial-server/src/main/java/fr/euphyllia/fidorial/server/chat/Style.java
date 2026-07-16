package fr.euphyllia.fidorial.server.chat;

import java.util.HashSet;
import java.util.Set;

public record Style(
        String color,
        Set<String> decorations,
        String font,
        Integer shadowColor,
        String insertion,
        String clickAction,
        String clickValue,
        String hoverText) {

    static final Style EMPTY =
            new Style(null, Set.of(), null, null, null, null, null, null);

    Style withColor(String c) {
        return new Style(c, decorations, font, shadowColor, insertion,
                clickAction, clickValue, hoverText);
    }

    Style withDecoration(String d) {
        HashSet<String> copy = new HashSet<>(decorations);
        copy.add(d);
        return new Style(color, Set.copyOf(copy), font, shadowColor,
                insertion, clickAction, clickValue, hoverText);
    }

    Style withFont(String f) {
        return new Style(color, decorations, f, shadowColor, insertion,
                clickAction, clickValue, hoverText);
    }

    Style withShadowColor(int argb) {
        return new Style(color, decorations, font, argb, insertion,
                clickAction, clickValue, hoverText);
    }

    Style withInsertion(String value) {
        return new Style(color, decorations, font, shadowColor, value,
                clickAction, clickValue, hoverText);
    }

    Style withClick(String action, String value) {
        return new Style(color, decorations, font, shadowColor, insertion,
                action, value, hoverText);
    }

    Style withHover(String text) {
        return new Style(color, decorations, font, shadowColor, insertion,
                clickAction, clickValue, text);
    }
}
