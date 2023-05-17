package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;
import static primitives.Util.*;
import java.util.List;
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
    /**
     * Finds the intersections between a ray and the sphere represented by this object within a maximum distance.
     *
     * @param ray         The ray representing the starting point and direction of the intersection search.
     * @param maxDistance The maximum distance within which to search for intersections.
     * @return A list of geographic points that intersect with the ray within the specified maximum distance, or null if no intersections are found.
     */
    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance) {
        double tm = 0, d = 0;

        // Calculate the vector between the ray's start point and the sphere's center
        if (!this.center.equals(ray.getP0())) {
            var u = this.center.subtract(ray.getP0());

            // Calculate the projection of the vector onto the ray's direction vector
            tm = ray.getDir().dotProduct(u);

            // Calculate the distance between the ray's start point and the point on the vector that is perpendicular to the ray's direction vector
            d = Math.sqrt(u.dotProduct(u) - tm * tm);
        }

        // If the distance is greater than or equal to the sphere's radius, there are no intersections
        if (d >= this.radius) {
            return null;
        }

        double th = Math.sqrt(this.radius * this.radius - d * d);
        double t1 = tm + th;
        double t2 = tm - th;

        // Check if both intersection points are within the maximum distance
        if (t1 > 0 && t2 > 0 && alignZero(t1 - maxDistance) <= 0 && alignZero(t2 - maxDistance) <= 0) {
            return List.of(new GeoPoint(this, ray.getPoint(t1)), new GeoPoint(this, ray.getPoint(t2)));
        }

        // Check if one intersection point is within the maximum distance
        if (t2 > 0 && alignZero(t2 - maxDistance) <= 0) {
            return List.of(new GeoPoint(this, ray.getPoint(t2)));
        }

        if (t1 > 0 && alignZero(t1 - maxDistance) <= 0) {
            return List.of(new GeoPoint(this, ray.getPoint(t1)));
        }

        // No intersections within the maximum distance
        return null;
    }
}