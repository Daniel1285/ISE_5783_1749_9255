package geometries;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;
import java.util.List;
import static primitives.Util.*;
/**
 * The Triangle class represents a two-dimensional triangle in a 3D Cartesian coordinate system.
 * It extends the Polygon class, which is a collection of connected vertices.
 */
public class Triangle extends Polygon {
    @Override
    public String toString() {
        return "Triangle{" +
                "vertices=" + vertices +
                '}';
    }

    /**
     * Constructs a Triangle object with the given three vertices.
     *
     * @param p1 the first vertex of the triangle
     * @param p2 the second vertex of the triangle
     * @param p3 the third vertex of the triangle
     */
    public Triangle(Point p1, Point p2, Point p3) {
        super(p1, p2, p3);
    }

    /**
     * Finds the intersections between a ray and the triangle represented by this object within a maximum distance.
     *
     * @param ray         The ray representing the starting point and direction of the intersection search.
     * @param maxDistance The maximum distance within which to search for intersections.
     * @return A list of geographic points that intersect with the ray within the specified maximum distance and lie on the triangle, or null if no intersections are found.
     */
    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance) {
        List<GeoPoint> intersections = plane.findGeoIntersections(ray, maxDistance);

        // If there are no intersections with the plane, there can be no intersections with the triangle
        if (intersections == null) {
            return null;
        }

        Point P0 = ray.getP0();
        Vector v = ray.getDir();

        Vector v1 = this.vertices.get(0).subtract(P0);
        Vector v2 = this.vertices.get(1).subtract(P0);
        Vector v3 = this.vertices.get(2).subtract(P0);

        Vector n1 = v1.crossProduct(v2);
        Vector n2 = v2.crossProduct(v3);
        Vector n3 = v3.crossProduct(v1);

        double s1 = v.dotProduct(n1);
        if (isZero(s1)) {
            return null;
        }
        double s2 = v.dotProduct(n2);
        if (isZero(s2)) {
            return null;
        }
        double s3 = v.dotProduct(n3);
        if (isZero(s3)) {
            return null;
        }

        // If the dot products have the same sign, the ray intersects the triangle
        if ((s1 > 0 && s2 > 0 && s3 > 0) || (s1 < 0 && s2 < 0 && s3 < 0)) {
            Point point = intersections.get(0).point;
            return List.of(new GeoPoint(this, point));
        }

        // No intersections with the triangle
        return null;
    }
}
