package fr.euphyllia.fidorial.api.text;

public interface TextFormatter {

    String stripTags(String input);

    String toAnsi(String input);

    boolean isTag(String tag);

    static String escape(String input) {
        return input.replace("<", "\\<");
    }
}
