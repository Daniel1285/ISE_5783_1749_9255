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

    @Override
    public List<Point> findIntersections(Ray ray) {
        double tm = 0, d = 0;
        if(!this.center.equals(ray.getP0())) {
            var u = this.center.subtract(ray.getP0());
            tm = ray.getDir().dotProduct(u);
            d = Math.sqrt(u.dotProduct(u) - tm*tm);
        }

        if(d >= this.radius){
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