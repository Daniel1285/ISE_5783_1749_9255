package primitives;

import java.util.Objects;


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
    Point(Double3 xyz) {
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
    public double getX(){
        return xyz.d1;
    }
    public double getY(){
        return xyz.d2;
    }
    public double getZ(){
        return xyz.d3;
    }
}