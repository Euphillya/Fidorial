package fr.euphyllia.fidorial.server.command.brigadier.argument.selector.options;

public final class SelectorSetState {
    private boolean positive;
    private boolean negative;

    public boolean canAdd(boolean inverted) {
        return inverted || (!positive && !negative);
    }

    public boolean canAddAny() {
        return !positive || !negative;
    }

    public void add(boolean inverted) {
        if (inverted) {
            negative = true;
        } else {
            positive = true;
        }
    }
}
