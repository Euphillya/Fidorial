package fr.euphyllia.fidorial.server.command.brigadier.argument.selector.options;

public class SingleUseOption {
    private boolean consumed;

    public boolean available() {
        return !this.consumed;
    }

    public void consume() {
        this.consumed = true;
    }
}
