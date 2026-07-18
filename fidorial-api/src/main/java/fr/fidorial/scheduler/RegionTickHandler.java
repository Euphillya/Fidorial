package fr.fidorial.scheduler;

@FunctionalInterface
public interface RegionTickHandler {
    void tick(String world, int sectionX, int sectionZ, long currentTick);
}