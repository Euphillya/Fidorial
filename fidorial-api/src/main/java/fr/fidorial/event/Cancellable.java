package fr.fidorial.event;

public interface Cancellable {

    boolean isCancelled();

    void setCancelled(boolean cancelled);
}
