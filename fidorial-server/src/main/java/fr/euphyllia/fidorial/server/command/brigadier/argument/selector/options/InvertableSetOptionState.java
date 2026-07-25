package fr.euphyllia.fidorial.server.command.brigadier.argument.selector.options;

public class InvertableSetOptionState {

    private Limitation state = Limitation.NONE;

    public boolean canParsePositiveElement() {
        return this.state == Limitation.NONE;
    }

    public boolean canParseNegativeElement() {
        return this.state != Limitation.SINGLE;
    }

    public boolean canParseAny() {
        return this.state != Limitation.SINGLE;
    }

    public boolean canParseElement(boolean inverted) {
        return inverted ? this.canParseNegativeElement() : this.canParsePositiveElement();
    }

    public void markParsedElement(boolean inverted) {
        this.state = inverted ? Limitation.MULTIPLE : Limitation.SINGLE;
    }

    private enum Limitation {
        NONE, SINGLE, MULTIPLE
    }
}
