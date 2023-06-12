package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;
import java.util.List;
import static primitives.Util.*;

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
    @Override
    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance) {
        Vector vAxis = axisRay.getDir();
        Vector v = ray.getDir();
        Point p0 = ray.getP0();
        double a = 0;
        double b = 0;
        double c = 0;

        double vVa = alignZero(v.dotProduct(vAxis));
        Vector vVaVa;
        Vector vMinusVVaVa;
        if (vVa == 0)
            vMinusVVaVa = v;
        else {
            vVaVa = vAxis.scale(vVa);
            try {
                vMinusVVaVa = v.subtract(vVaVa);
            } catch (IllegalArgumentException e1) {
                return null;
            }
        }

        a = vMinusVVaVa.lengthSquared();

        Vector deltaP = null;
        try {
            deltaP = p0.subtract(axisRay.getP0());
        } catch (IllegalArgumentException e1) {
            if (vVa == 0 && alignZero(radius - maxDistance) <= 0) {
                return List.of(new GeoPoint(this, ray.getPoint(radius)));
            }
            double t = alignZero(Math.sqrt(radius * radius / vMinusVVaVa.lengthSquared()));
            return alignZero(t - maxDistance) >= 0 ? null : List.of(new GeoPoint(this, ray.getPoint(t)));
        }

        double dPVAxis = alignZero(deltaP.dotProduct(vAxis));
        Vector dPVaVa;
        Vector dPMinusdPVaVa;
        if (dPVAxis == 0)
            dPMinusdPVaVa = deltaP;
        else {
            dPVaVa = vAxis.scale(dPVAxis);
            try {
                dPMinusdPVaVa = deltaP.subtract(dPVaVa);
            } catch (IllegalArgumentException e1) {
                double t = alignZero(Math.sqrt(radius * radius / a));
                return alignZero(t - maxDistance) >= 0 ? null : List.of(new GeoPoint(this, ray.getPoint(t)));
            }
        }

        b = 2 * alignZero(vMinusVVaVa.dotProduct(dPMinusdPVaVa));
        c = dPMinusdPVaVa.lengthSquared() - radius * radius;

        // A*t^2 + B*t + C = 0 - lets resolve it
        double discr = alignZero(b * b - 4 * a * c);
        if (discr <= 0) return null;

        double doubleA = 2 * a;
        double tm = alignZero(-b / doubleA);
        double th = Math.sqrt(discr) / doubleA;
        if (isZero(th)) return null;

        double t1 = alignZero(tm + th);
        if (t1 <= 0)
            return null;

        double t2 = alignZero(tm - th);
        if (t2 > 0 && alignZero(t2 - maxDistance) < 0)
            return List.of(new GeoPoint(this, ray.getPoint(t1)),new GeoPoint(this, ray.getPoint(t2)));
        else if (alignZero(t1 - maxDistance) < 0)// t2 is behind the head
            return List.of(new GeoPoint(this, ray.getPoint(t1)));
        return null;
    }

}
