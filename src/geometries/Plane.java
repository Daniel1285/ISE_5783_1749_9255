package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;
import static primitives.Util.*;
import java.util.List;


/**

 Represents a plane in 3D space.
 */
public class Plane extends Geometry {
    /**
     * The base point of the plane
     */
    final private Point p0;

    /**
     * The normal vector of the plane
     */
    final private Vector normal;

    /**
     * Constructs a Plane object with the given base point and normal vector.
     * If the normal vector is not already normalized, it is normalized before being stored.
     *
     * @param p0     the base point of the plane
     * @param normal the normal vector of the plane
     */
    public Plane(Point p0, Vector normal) {
        this.p0 = p0;
        if (!isZero(normal.length() - 1d)) { // if the vector is not normalized
            this.normal = normal.normalize();
        } else {
            this.normal = normal;
        }
    }

    /**
     * Constructs a Plane object given three points on the plane.
     * The normal vector is calculated by taking the cross product of two vectors
     * formed by subtracting one point from the other two points.
     *
     * @param p1 a point on the plane
     * @param p2 another point on the plane
     * @param p3 a third point on the plane
     */
    public Plane(Point p1, Point p2, Point p3) {
        this.p0 = p1;

        Vector U = p1.subtract(p2); // AB
        Vector V = p1.subtract(p3); // AC

        Vector N = U.crossProduct(V); // AB x AC

        this.normal = N.normalize(); // right hand rule
    }

    /**
     * @return the base point of the plane
     */
    public Point getP0() {
        return p0;
    }

    /**
     * @return the normal vector of the plane
     */
    public Vector getNormal() {
        return normal;
    }

    @Override
    public Vector getNormal(Point point) {
        return normal;
    }

    /**
     * Finds the intersections between a ray and the plane represented by this object within a maximum distance.
     *
     * @param ray         The ray representing the starting point and direction of the intersection search.
     * @param maxDistance The maximum distance within which to search for intersections.
     * @return A list of geographic points that intersect with the ray within the specified maximum distance, or null if no intersections are found.
     */
    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance) {
        // Get the starting point, direction, and normal vector of the ray and plane
        Point P0 = ray.getP0();
        Vector v = ray.getDir();
        Vector n = normal;

        // Calculate the dot product between the normal vector and the direction vector of the ray
        double nv = alignZero(n.dotProduct(v));

        // If the ray is parallel to the plane, there are no intersections
        if (isZero(nv)) {
            return null;
        }

        // If the starting point of the ray is equal to the reference point, there are no intersections
        if (p0.equals(P0)) {
            return null;
        }

        // Calculate the dot product between the normal vector and the vector from the reference point to the starting point of the ray
        Vector P0_Q0 = p0.subtract(P0);
        double nP0Q0 = alignZero(n.dotProduct(P0_Q0));
        if (isZero(nP0Q0)) {
            return null;
        }
        // Calculate the parameter t for the intersection point
        double t = alignZero(nP0Q0 / nv);


        // If t is negative or greater than the maximum distance, there are no intersections
        if (t < 0 || alignZero(t - maxDistance) > 0) {
            return null;
        }

        // Return a list containing the intersection point as a GeoPoint
        return List.of(new GeoPoint(this, ray.getPoint(t)));
    }
}