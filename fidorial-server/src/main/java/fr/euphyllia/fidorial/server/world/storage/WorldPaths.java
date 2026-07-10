package fr.euphyllia.fidorial.server.world.storage;

import java.nio.file.Files;
import java.nio.file.Path;

public class WorldPaths {

    private final Path worldRoot;
    private final Layout writeLayout;
    public WorldPaths(Path worldRoot) {
        this(worldRoot, Layout.MODERN);
    }

    public WorldPaths(Path worldRoot, Layout writeLayout) {
        this.worldRoot = worldRoot;
        this.writeLayout = writeLayout;
    }

    public Path worldRoot() {
        return worldRoot;
    }

    public Path levelDat() {
        return worldRoot.resolve("level.dat");
    }

    private Path modernDimensionRoot(Dimension dim) {
        return worldRoot.resolve("dimensions").resolve(dim.namespace()).resolve(dim.path());
    }

    private Path legacyDimensionRoot(Dimension dim) {
        return dim.legacyFolder() == null ? worldRoot : worldRoot.resolve(dim.legacyFolder());
    }

    public Path regionDir(Dimension dim) {
        Path base = writeLayout == Layout.MODERN ? modernDimensionRoot(dim) : legacyDimensionRoot(dim);
        return base.resolve("region");
    }

    public Path entitiesDir(Dimension dim) {
        Path base = writeLayout == Layout.MODERN ? modernDimensionRoot(dim) : legacyDimensionRoot(dim);
        return base.resolve("entities");
    }

    public Path poiDir(Dimension dim) {
        Path base = writeLayout == Layout.MODERN ? modernDimensionRoot(dim) : legacyDimensionRoot(dim);
        return base.resolve("poi");
    }

    public Path regionDirForRead(Dimension dim) {
        Path modern = modernDimensionRoot(dim).resolve("region");
        if (Files.isDirectory(modern)) return modern;
        Path legacy = legacyDimensionRoot(dim).resolve("region");
        if (Files.isDirectory(legacy)) return legacy;
        return modern;
    }


    public enum Layout {
        MODERN,
        LEGACY
    }

}
