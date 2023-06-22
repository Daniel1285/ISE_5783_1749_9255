package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

/**

 The Tube class represents a tube in 3D Cartesian coordinate system
 with a center axis represented by a Ray and a radius.
 It is a subclass of RadialGeometry and inherits its radius parameter.
 */
public class Tube extends RadialGeometry {
    /**
     * The center axis of the tube represented by a Ray
     */
    protected Ray axisRay;
    /**
     * Constructs a new Tube object with the specified radius and center axis
     *
     * @param radius the radius of the tube
     * @param axiRay the center axis of the tube represented by a Ray
     */
    public Tube(double radius, Ray axiRay) {
        super(radius);
        this.axisRay = axiRay;
    }

    /**
     * Returns the center axis of the tube represented by a Ray
     *
     * @return the center axis of the tube represented by a Ray
     */
    public Ray getAxiRay() {
        return axisRay;
    }

    /**
     * Calculates and returns the normal vector to the tube at the specified point.
     * For a tube, the normal vector is parallel to its center axis.
     *
     * @param point the point to calculate the normal vector at
     * @return the normal vector to the tube at the specified point
     */
    @Override
    public Vector getNormal(Point point) {

        Vector v = axisRay.getDir(); // (1, 0, 0)
        Point P0 = axisRay.getP0(); // (0, 0, 0)

        double t = v.dotProduct(point.subtract(P0)); // t = v * (P - P0) = 1
        if(isZero(t)) { // check if the projection length is not 0
            throw new IllegalArgumentException("(P - P0) is orthogonal to v");
        }
        Point O = P0.add(v.scale(t)); // O = P0 + t * v = (1, 0, 0)

        Vector N = point.subtract(O); // N = P - O = (0, 0, 1)

        return N.normalize();
    }

    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray,double maxDistance){
        Vector vAxis = axisRay.getDir();
        Vector v = ray.getDir();
        Point p0 = ray.getP0();

        // At^2+Bt+C=0
        double a = 0;
        double b = 0;
        double c = 0;

        double vVa = alignZero(v.dotProduct(vAxis));
        Vector vVaVa;
        Vector vMinusVVaVa;
        if (vVa == 0) // the ray is orthogonal to the axis
            vMinusVVaVa = v;
        else {
            vVaVa = vAxis.scale(vVa);
            try {
                vMinusVVaVa = v.subtract(vVaVa);
            } catch (IllegalArgumentException e1) { // the rays is parallel to axis
                return null;
            }
        }
        // A = (v-(v*va)*va)^2
        a = vMinusVVaVa.lengthSquared();

        Vector deltaP = null;
        try {
            deltaP = p0.subtract(axisRay.getP0());
        } catch (IllegalArgumentException e1) { // the ray begins at axis P0
            if (vVa == 0) // the ray is orthogonal to Axis
                return List.of(new GeoPoint(this,ray.getPoint(radius)));

            double t = alignZero(Math.sqrt(radius * radius / vMinusVVaVa.lengthSquared()));
            return t == 0 ? null : List.of( new GeoPoint(this, ray.getPoint(t)));
        }

        double dPVAxis = alignZero(deltaP.dotProduct(vAxis));
        Vector dPVaVa;
        Vector dPMinusdPVaVa;
        if (dPVAxis == 0)
            dPMinusdPVaVa = deltaP;
        else {
            try {
                dPVaVa = vAxis.scale(dPVAxis);
                dPMinusdPVaVa = deltaP.subtract(dPVaVa);
            } catch (IllegalArgumentException e1) {
                double t = alignZero(Math.sqrt(radius * radius / a));
                return t == 0 ? null : List.of((new GeoPoint(this,ray.getPoint(t))));
            }
        }

        // B = 2(v - (v*va)*va) * (dp - (dp*va)*va))
        b = 2 * alignZero(vMinusVVaVa.dotProduct(dPMinusdPVaVa));
        c = dPMinusdPVaVa.lengthSquared() - radius * radius;

        // A*t^2 + B*t + C = 0 - lets resolve it
        double discr = alignZero(b * b - 4 * a * c);
        if (discr <= 0) return null; // the ray is outside or tangent to the tube

        double doubleA = 2 * a;
        double tm = alignZero(-b / doubleA);
        double th = Math.sqrt(discr) / doubleA;
        if (isZero(th)) return null; // the ray is tangent to the tube

        double t1 = alignZero(tm + th);
        if (t1 <= 0) // t1 is behind the head
            return null; // since th must be positive (sqrt), t2 must be non-positive as t1

        double t2 = alignZero(tm - th);

        // if both t1 and t2 are positive
        if (t2 > 0)
            return List.of(new GeoPoint(this,ray.getPoint(t1)), (new GeoPoint(this,ray.getPoint(t2))));
        else // t2 is behind the head
            return List.of((new GeoPoint(this,ray.getPoint(t1))));

//        return null;
    }
}