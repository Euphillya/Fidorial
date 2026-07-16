package fr.euphyllia.fidorial.api.text;

public interface TextFormatter {

    static String escape(String input) {
        return input.replace("<", "\\<");
    }

    String stripTags(String input);

    String toAnsi(String input);

    boolean isTag(String tag);
}
