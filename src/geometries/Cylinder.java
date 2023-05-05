package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import static primitives.Util.isZero;

/**

 Represents a cylinder in 3D Cartesian coordinate system
 extending infinitely along its axis with finite radius and height.
 Inherits from the Tube class.
 */
public class Cylinder extends Tube {

    /** height of the tube */
    final private double height;

    /**
     * Creates a new Cylinder object with the given radius, axis ray, and height.
     * @param radius   the radius of the cylinder
     * @param axisRay  the axis ray of the cylinder
     * @param height   the height of the cylinder
     */
    public Cylinder(Ray axisRay, double radius, double height) {
        super(radius, axisRay);
        this.height = height;
    }

    /**
     * @return the height of this cylinder
     */
    public double getHeight() {
        return height;
    }
    /**
     * Computes and returns the normal vector to this Cylinder at the given Point.
     * @param point the Point at which to compute the normal vector
     * @return the normal vector to this Cylinder at the given Point
     */
    @Override
    public Vector getNormal(Point point) {
        Vector v = axisRay.getDir();
        Point p0 = axisRay.getP0();
        Point topP = p0.add(v.scale(height));

        // If the given point is at the top or bottom cap, return the cylinder's axis direction vector
        if (point.equals(topP) || isZero(v.dotProduct(point.subtract(topP)))) {
            return v.normalize();
        }
        if (point.equals(p0) || isZero(v.dotProduct(point.subtract(p0)))) {
            return v.normalize();
        }

        // Otherwise, delegate to the superclass to compute the normal vector
        return super.getNormal(point);
    }
}
