package primitives;
/**

 The Vector class represents a geometric vector in a three-dimensional space. It extends the Point class,

 so it inherits the x, y, and z fields.

 The class provides basic vector operations such as addition, scaling, dot product, cross product, length, and normalization.
 */
public class Vector extends Point {

    /**
     * Constructs a vector from three double values.
     *
     * @param x The x coordinate of the vector.
     * @param y The y coordinate of the vector.
     * @param z The z coordinate of the vector.
     * @throws IllegalArgumentException If the vector (0,0,0) is created.
     */
    public Vector(double x, double y, double z) {
        super(x, y, z);
        if (this.xyz.equals(Double3.ZERO)) {
            throw new IllegalArgumentException("vector (0,0,0) isn't allowed!");
        }
    }

    /**
     * Constructs a vector from a Double3 object.
     *
     * @param xyz The Double3 object that represents the vector.
     * @throws IllegalArgumentException If the vector (0,0,0) is created.
     */
    Vector(Double3 xyz) {
        super(xyz);
        if (this.xyz.equals(Double3.ZERO)) {
            throw new IllegalArgumentException("vector (0,0,0) isn't allowed!");
        }
    }

    /**
     * Returns the result of adding this vector with another vector.
     *
     * @param vector1 The vector to add.
     * @return The resulting vector.
     */
    public Vector add(Vector vector1) {
        return new Vector(this.xyz.add(vector1.xyz));
    }

    /**
     * Returns the result of scaling this vector by a factor.
     *
     * @param t The scaling factor.
     * @return The resulting vector.
     */
    public Vector scale(double t) {
        return new Vector(this.xyz.scale(t));
    }

    /**
     * Returns the dot product of this vector with another vector.
     *
     * @param vector1 The vector to dot with.
     * @return The resulting dot product.
     */
    public double dotProduct(Vector vector1) {
        return (xyz.d1 * vector1.xyz.d1 + xyz.d2 * vector1.xyz.d2 + xyz.d3 * vector1.xyz.d3);
    }

    /**
     * Returns the cross product of this vector with another vector.
     *
     * @param vector1 The vector to cross with.
     * @return The resulting cross product vector.
     */
    public Vector crossProduct(Vector vector1) {
        return new Vector(xyz.d2 * vector1.xyz.d3 - vector1.xyz.d2 * xyz.d3,
                xyz.d3 * vector1.xyz.d1 - vector1.xyz.d3 * xyz.d1,
                xyz.d1 * vector1.xyz.d2 - vector1.xyz.d1 * xyz.d2);
    }

    /**
     * Returns the square of the length of this vector.
     *
     * @return The resulting squared length.
     */
    public double lengthSquared() {
        return (dotProduct(this));
    }

    /**
     * Returns the length of this vector.
     *
     * @return The resulting length.
     */
    public double length() {
        return Math.sqrt(lengthSquared());
    }

    /**
     * Returns a normalized version of this vector.
     *
     * @return The normalized vector.
     */
    public Vector normalize() {
        return new Vector(xyz.scale(1 / length()));
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public String toString() {
        return "Vector " + xyz;
    }
}