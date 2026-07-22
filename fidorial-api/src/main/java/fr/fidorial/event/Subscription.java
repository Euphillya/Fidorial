package fr.fidorial.event;

import org.jetbrains.annotations.ApiStatus;

@ApiStatus.NonExtendable
public interface Subscription {
    boolean isActive();

    void unsubscribe();
}
