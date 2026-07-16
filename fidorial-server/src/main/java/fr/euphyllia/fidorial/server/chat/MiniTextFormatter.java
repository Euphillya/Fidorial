package fr.euphyllia.fidorial.server.chat;

import fr.euphyllia.fidorial.api.text.TextFormatter;

public class MiniTextFormatter implements TextFormatter {

    public MiniTextFormatter() {
    }

    @Override
    public String stripTags(String input) {
        return MiniText.stripTags(input);
    }

    @Override
    public String toAnsi(String input) {
        return MiniText.toAnsi(input);
    }

    @Override
    public boolean isTag(String tag) {
        return MiniText.isTag(tag);
    }
}
