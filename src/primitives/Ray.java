
package primitives;
import java.util.Objects;
/**

 The Ray class represents a ray in 3D space, defined by a starting point (p0) and a direction (dir).
 */
public class Ray {
    private Point p0; // The starting point of the ray
    private Vector dir; // The direction of the ray
    /**
     * Constructs a new Ray object with the specified starting point and direction.
     * If the direction vector is not a unit vector, it will be normalized.
     *
     * @param p0  the starting point of the ray
     * @param dir the direction of the ray
     */
    public Ray(Point p0, Vector dir) {
        this.p0 = p0;
        if (dir.length() != 1) {
            dir.normalize();
        }
        this.dir = dir;
    }

    /**
     * Returns the starting point of the ray.
     *
     * @return the starting point of the ray
     */
    public Point getP0() {
        return p0;
    }

    /**
     * Returns the direction of the ray.
     *
     * @return the direction of the ray
     */
    public Vector getDir() {
        return dir;
    }

    /**
     * Compares this Ray object to the specified object.
     * The result is true if and only if the argument is not null and is a Ray object that
     * has the same starting point and direction as this Ray object.
     *
     * @param o the object to compare
     * @return true if the objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Ray ray)) return false;
        return p0.equals(ray.p0) && dir.equals(ray.dir);
    }

    /**
     * Computes and returns the hash code for this Ray object.
     *
     * @return the hash code for this Ray object
     */
    @Override
    public int hashCode() {
        return Objects.hash(p0, dir);
    }

    /**
     * Returns a string representation of this Ray object, in the form "Ray[p0=<p0>, dir=<dir>]".
     *
     * @return a string representation of this Ray object
     */
    @Override
    public String toString() {
        return "Ray" +
                "p0=" + p0 +
                ", dir=" + dir;
    }
}