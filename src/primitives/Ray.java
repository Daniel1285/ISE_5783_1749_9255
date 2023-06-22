package primitives;
import geometries.Intersectable.GeoPoint;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import static  primitives.Util.*;
/**

 The Ray class represents a ray in 3D space, defined by a starting point (p0) and a direction (dir).
 */
public class Ray {
    private static final double DELTA = 0.1;
    private Point p0; // The starting point of the ray
    private Vector dir; // The direction of the ray
    /**
     * Constructs a new Ray object with the specified starting point and direction.
     * If the direction vector is not a unit vector, it will be normalized.
     *
     * @param p0  the starting point of the ray
     * @param dir the direction of the ray
     */
    public Ray(Point p0, Vector dir) {
        this.p0 = p0;
        if (dir.length() != 1) {
            this.dir = dir.normalize();
        }
        else{
            this.dir = dir;
        }
    }
    /**
     * Constructs a Ray object with the specified starting point, normal vector, and direction.
     * The direction vector is normalized, and the starting point is adjusted slightly along the normal vector to avoid self-intersections.
     *
     * @param p   The starting point of the ray.
     * @param n   The normal vector of the surface intersected by the ray.
     * @param dir The direction vector of the ray.
     */
    public Ray(Point p, Vector n, Vector dir) {
        this.dir = dir.normalize();

        double nv = n.dotProduct(dir);
        Vector delta = n.scale(DELTA);

        // Adjust the starting point along the normal vector to avoid self-intersections
        if (nv < 0) {
            delta = delta.scale(-1);
        }

        p0 = p.add(delta);
    }


    /**
     * Returns the starting point of the ray.
     *
     * @return the starting point of the ray
     */
    public Point getP0() {
        return p0;
    }

    /**
     * Returns the direction of the ray.
     *
     * @return the direction of the ray
     */
    public Vector getDir() {
        return dir;
    }

    /**
     * Calculation function for a point on a beam
     * @param delta It's actually the "t" in the formula P = P0 + t*v
     * @return point
     */
    public Point getPoint(double delta) {
        if (isZero(delta)) {
            return p0;
        }
        return p0.add(dir.scale(delta));
    }
    /**

     /**
     * Finds the closest Point from a list of Points.
     *
     * @param points The list of Points.
     * @return The closest Point, or null if the list is null or empty.
     */
    public Point findClosestPoint(List<Point> points) {
        return points == null || points.isEmpty() ? null
                : findClosestGeoPoint(points.stream().map(p -> new GeoPoint(null, p)).toList()).point;
    }

    /**
     * Finds the closest GeoPoint from a list of GeoPoints based on their distances from the reference point (p0).
     *
     * @param points The list of GeoPoints.
     * @return The closest GeoPoint, or null if the list is empty.
     */
    public GeoPoint findClosestGeoPoint(List<GeoPoint> points) {
        if (points.size() == 0) {
            return null;
        }
        GeoPoint closest = points.get(0);
        for (GeoPoint item : points) {
            // Checks which distance is smaller and if smaller, switches between them
            if (item.point.distanceSquared(p0) < closest.point.distanceSquared(p0)) {
                closest = item;
            }
        }
        return closest;
    }


    /**
     * Compares this Ray object to the specified object.
     * The result is true if and only if the argument is not null and is a Ray object that
     * has the same starting point and direction as this Ray object.
     *
     * @param o the object to compare
     * @return true if the objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Ray ray)) return false;
        return p0.equals(ray.p0) && dir.equals(ray.dir);
    }



    /**
     * Computes and returns the hash code for this Ray object.
     *
     * @return the hash code for this Ray object
     */
    @Override
    public int hashCode() {
        return Objects.hash(p0, dir);
    }

    /**
     * Returns a string representation of this Ray object, in the form "Ray[p0=<p0>, dir=<dir>]".
     *
     * @return a string representation of this Ray object
     */
    @Override
    public String toString() {
        return "Ray" +
                "p0=" + p0 +
                ", dir=" + dir;
    }
    /**
     * Creates a beam of rays from a target area to a specified point.
     *
     * @param points The list of points representing the target area.
     * @param p The destination point.
     * @return A list of rays originating from the points in the target area and pointing towards the destination point.
     */
    public static List<Ray> createBeamOfRaysFromTargetArea(List<Point> points, Point p) {
        List<Ray> rays = new LinkedList<>(); // Create an empty list to store the rays.
        // Iterate over each point in the list of points representing the target area.
        for (Point point : points) {
            // Create a new ray with the current point as the origin and a direction pointing towards the destination point.
            rays.add(new Ray(point, p.subtract(point)));
        }

        return rays; // Return the list of rays.
    }



}