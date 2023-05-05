package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;
import static primitives.Util.*;
import java.util.List;
import java.util.ArrayList;
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
     * Calculates the intersection points between the sphere and the given ray.
     * If there are no intersections, returns null.
     *
     * @param ray The ray to find intersection points with.
     * @return A list of intersection points, or null if there are no intersections.
     */
    @Override
    public List<Point> findIntersections(Ray ray) {
        double tm = 0 , d = 0 ;

        // Calculate the vector between the ray's start point and the sphere's center
        if(!this.center.equals(ray.getP0())) {
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

        double th = Math.sqrt(this.radius* this.radius - d * d);
        double t1 = tm + th;
        double t2 = tm - th;


        if(t1 > 0 && t2 > 0 ){
            return List.of(ray.getPoint(t1),ray.getPoint(t2));
        }

        if(t2 > 0 && t1 <=0){
            return List.of(ray.getPoint(t2));
        }
        if(t1 > 0 && t2 <=0){
            return List.of(ray.getPoint(t1));
        }
        return null;
    }
}