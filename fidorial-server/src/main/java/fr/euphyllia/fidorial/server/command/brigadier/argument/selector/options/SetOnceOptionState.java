package fr.euphyllia.fidorial.server.command.brigadier.argument.selector.options;

public class SetOnceOptionState {
    private boolean hasValue;

    public boolean canParse() {
        return !this.hasValue;
    }

    public void markParsed() {
        this.hasValue = true;
    }
}
