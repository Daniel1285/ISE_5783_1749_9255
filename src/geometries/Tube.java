package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

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
}