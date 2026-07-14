package fr.euphyllia.fidorial.api.event;

public interface Cancellable {

    boolean isCancelled();

    void setCancelled(boolean cancelled);
}
