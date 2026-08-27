package fr.euphyllia.fidorial.server.command.brigadier.argument.selector.options;

public final class SelectorSetState {
    private boolean positive;
    private boolean negative;

    public boolean canAdd(final boolean inverted) {
        return inverted ? !positive : (!positive && !negative);
    }

    public boolean canAddAny() {
        return !positive;
    }

    public void add(final boolean inverted) {
        if (inverted) {
            negative = true;
        } else {
            positive = true;
        }
    }
}
