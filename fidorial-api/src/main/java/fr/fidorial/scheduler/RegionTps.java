package fr.fidorial.scheduler;

public interface RegionTps {

    String world();

    int sectionX();

    int sectionZ();

    int originChunkX();

    int originChunkZ();

    double tps();

    double msptAvg();

    int queuedTasks();

    int tickets();
}
