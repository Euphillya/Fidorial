package fr.euphyllia.fidorial.server.world;

import fr.fidorial.world.Location;

public final class CoordMath {

    private CoordMath() {
    }

    public static Location applyLocalCoords(Location origin, double left, double up, double forwards) {
        double yaw = Math.toRadians(origin.yaw());
        double pitch = Math.toRadians(origin.pitch());

        double yCos = Math.cos(yaw + Math.PI / 2);
        double ySin = Math.sin(yaw + Math.PI / 2);

        double xCos = Math.cos(-pitch);
        double xSin = Math.sin(-pitch);

        double xCosUp = Math.cos(-pitch + Math.PI / 2);
        double xSinUp = Math.sin(-pitch + Math.PI / 2);

        // forward vector
        double fx = yCos * xCos;
        double fy = xSin;
        double fz = ySin * xCos;

        // up vector
        double ux = yCos * xCosUp;
        double uy = xSinUp;
        double uz = ySin * xCosUp;

        // left vector
        double lx = -(fy * uz - fz * uy);
        double ly = -(fz * ux - fx * uz);
        double lz = -(fx * uy - fy * ux);

        double x = lx * left + ux * up + fx * forwards;
        double y = ly * left + uy * up + fy * forwards;
        double z = lz * left + uz * up + fz * forwards;

        return new Location(
                origin.x() + x,
                origin.y() + y,
                origin.z() + z,
                origin.yaw(),
                origin.pitch()
        );
    }
}
