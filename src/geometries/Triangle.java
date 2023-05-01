package geometries;
import primitives.Point;

/**
 * The Triangle class represents a two-dimensional triangle in a 3D Cartesian coordinate system.
 * It extends the Polygon class, which is a collection of connected vertices.
 */
public class Triangle extends Polygon{
    /**
     * Constructs a Triangle object with the given three vertices.
     * @param p1 the first vertex of the triangle
     * @param p2 the second vertex of the triangle
     * @param p3 the third vertex of the triangle
     */
    public Triangle(Point p1, Point p2, Point p3) {
        super(p1, p2, p3);
    }
}
