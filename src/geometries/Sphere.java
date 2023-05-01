package geometries;

import primitives.Point;
import primitives.Vector;
/**

 The Sphere class represents a sphere in a 3D Cartesian coordinate system.
 It extends the RadialGeometry class and implements the Geometry interface.
 */
public class Sphere extends RadialGeometry {
    /**
     * The center point of the sphere.
     */
    private Point center;

    /**
     * Constructs a Sphere object with the specified radius and center point.
     *
     * @param radius The radius of the sphere.
     * @param center The center point of the sphere.
     */
    public Sphere(double radius, Point center) {
        super(radius);
        this.center = center;
    }

    /**
     * Returns the center point of the sphere.
     *
     * @return The center point of the sphere.
     */
    public Point getCenter() {
        return center;
    }

    /**
     * Calculates and returns the normal vector of the sphere at a given point.
     * Since a sphere is a symmetrical object, the normal at any point on the surface points directly outward.
     *
     * @param point The point on the surface of the sphere.
     * @return The normal vector at the given point on the sphere.
     */
    @Override
    public Vector getNormal(Point point) {
        Vector v = point.subtract(center);
        return v.normalize();
    }
}