package fr.euphyllia.fidorial.server.entity;

import java.util.concurrent.atomic.AtomicInteger;

public final class EntityIdAllocator {

    private final AtomicInteger next = new AtomicInteger(1);

    public int allocate() {
        return next.getAndIncrement();
    }
}
