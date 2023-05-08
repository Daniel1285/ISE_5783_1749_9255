package geometries;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

/**
 * Interface for geometries that can be intersected with rays and have a normal vector.
 */
public abstract class Geometry extends Intersectable {
    protected Color emission = Color.BLACK;
    public Color getEmission() {
        return emission;
    }

    public Geometry setEmission(Color emission) {
        this.emission = emission;
        return this;
    }
    /**
     * Calculates the normal vector of a geometry at a given point.
     *
     * @param point The point on the geometry to get the normal vector at.
     * @return The normal vector at the given point.
     */
    public abstract Vector getNormal(Point point);
}

