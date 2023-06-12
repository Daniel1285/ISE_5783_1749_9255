package primitives;
import primitives.*;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

import static primitives.Util.isZero;


/**

 This class represents a point in 3D space using three double values (x, y, z).
 */
public class Point {
    @Override
    public String toString() {
        return "Point{" + xyz +'}';
    }

    /**
     * The 3D coordinates of the point as a Double3 object.
     */
    final Double3 xyz;

    /**
     * Creates a new Point object with the specified coordinates.
     *
     * @param x The x-coordinate of the point.
     * @param y The y-coordinate of the point.
     * @param z The z-coordinate of the point.
     */
    public Point(double x, double y, double z) {
        xyz = new Double3(x, y, z);
    }

    /**
     * Creates a new Point object with the specified Double3 object.
     *
     * @param xyz The Double3 object representing the 3D coordinates of the point.
     */
    public Point(Double3 xyz) {
        this.xyz = xyz;
    }

    /**
     * Checks whether this Point object is equal to another object.
     * Two Point objects are equal if their xyz fields are equal.
     *
     * @param o The object to compare this Point object to.
     * @return True if the two objects are equal, false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Point point)) {
            return false;
        }
        return xyz.equals(point.xyz);
    }

    /**
     * Computes the hash code for this Point object.
     * The hash code is based on the hash code of the xyz field.
     *
     * @return The hash code for this Point object.
     */
    @Override
    public int hashCode() {
        return Objects.hash(xyz);
    }

    /**
     * Computes the vector from this point to another point.
     *
     * @param point2 The other point.
     * @return The vector from this point to point2.
     */
    public Vector subtract(Point point2) {
        return new Vector(xyz.subtract(point2.xyz));
    }

    /**
     * Computes the point obtained by adding a vector to this point.
     *
     * @param vector2 The vector to add.
     * @return The point obtained by adding vector2 to this point.
     */
    public Point add(Vector vector2) {
        return new Point(this.xyz.add(vector2.xyz));
    }

    /**
     * Computes the squared distance between this point and another point.
     *
     * @param point2 The other point.
     * @return The squared distance between this point and point2.
     */
    public double distanceSquared(Point point2) {
        return ((xyz.d1 - point2.xyz.d1) * (xyz.d1 - point2.xyz.d1) +
                (xyz.d2 - point2.xyz.d2) * (xyz.d2 - point2.xyz.d2) +
                (xyz.d3 - point2.xyz.d3) * (xyz.d3 - point2.xyz.d3));
    }

    /**
     * Computes the distance between this point and another point.
     *
     * @param point2 The other point.
     * @return The distance between this point and point2.
     */
    public double distance(Point point2) {
        return Math.sqrt(distanceSquared(point2));
    }

    /**
     * Returns the X-coordinate of the point.
     * @return The X-coordinate.
     */
    public double getX(){
        return xyz.d1;
    }

    /**
     * Returns the Y-coordinate of the point.
     * @return The Y-coordinate.
     */
    public double getY(){
        return xyz.d2;
    }

    /**
     * Returns the Z-coordinate of the point.
     * @return The Z-coordinate.
     */
    public double getZ(){
        return xyz.d3;
    }

    /**
     * Returns the XYZ coordinates of the point.
     * @return The XYZ coordinates.
     */
    public Double3 getXYZ(){
        return xyz;
    }
    private static Random rnd = new Random();
    /**
     * Generates a list of points within a target area.
     *
     * @param p0 The initial point.
     * @param up The up vector.
     * @param right The right vector.
     * @param superSampling The super-sampling factor.
     * @param radius The radius of the target area.
     * @return A list of points within the target area.
     */
    public static List<Point> pointsInTheTargetArea(Point p0, Vector up, Vector right, double superSampling, double radius) {
        List<Point> pointsInTarget = new LinkedList<>(); // Create an empty list to store points within the target area.

        // Iterate over the i and j values within the range of -radius to radius, with a step size of radius/superSampling.
        for (double i = -radius; i < radius; i += radius / superSampling) {
            for (double j = -radius; j < radius; j += radius / superSampling) {
                double jitRed = rnd.nextDouble(-0.1, 0.1); // Generate a random value between -0.1 and 0.1.

                // Check if neither i nor j is zero.
                if (!isZero(i) && !isZero(j)) {
                    // Calculate the point p by adding the scaled up vector, scaled right vector,
                    // and a random jitter to the right vector.
                    Point p = p0.add(up.scale(i).add(right.scale(j + jitRed)));

                    // Check if the distance between p0 and p is less than or equal to the radius.
                    if (p0.distance(p) <= radius) {
                        pointsInTarget.add(p); // Add the point to the list of points within the target area.
                    }
                }
            }
        }

        return pointsInTarget; // Return the list of points within the target area.
    }

}