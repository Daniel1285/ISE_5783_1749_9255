package geometries;

import primitives.Point;
import primitives.Vector;

/**
 * Interface for geometries that can be intersected with rays and have a normal vector.
 */
public interface Geometry extends Intersectable {

    /**
     * Calculates the normal vector of a geometry at a given point.
     *
     * @param point The point on the geometry to get the normal vector at.
     * @return The normal vector at the given point.
     */
    public Vector getNormal(Point point);
}

