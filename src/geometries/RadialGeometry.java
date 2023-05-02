package geometries;

import primitives.Point;
import primitives.Vector;
/**

 The RadialGeometry abstract class represents a geometry shape that has a radial attribute such as a sphere, a cylinder, or a cone.
 It implements the Geometry interface.
 */
public abstract class RadialGeometry implements Geometry {
    /** The radius of the RadialGeometry */
    protected double radius;

    /**
     * Constructs a new RadialGeometry object with the given radius.
     * @param radius The radius of the RadialGeometry
     */
    public RadialGeometry(double radius) {
        this.radius = radius;
    }

    /**
     * Returns the radius of the RadialGeometry.
     * @return The radius of the RadialGeometry
     */
    public double getRadius() {
        return radius;
    }


}
