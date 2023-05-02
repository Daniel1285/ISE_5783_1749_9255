package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Vector;
import static org.junit.jupiter.api.Assertions.*;
import primitives.Ray;
import java.util.List;

public class TriangleTests {
    /**
     * Test method for {@link geometries.Triangle#getNormal(primitives.Point)}.
     */
    @Test
    void testGetNormal() {

        // ============ Equivalence Partitions Tests ==============

        Point p1 = new Point(0, -1, 0);
        Point p2 = new Point(0, 0, 3);
        Point p3 = new Point(2, 0, 0);
        Vector v = new Vector(-3,6, -2).normalize();
        Triangle tri = new Triangle(p1, p2, p3);

        // TC01: Bad normal
        assertEquals(1d, tri.getNormal(p1).length(), 0.00001,
                "Wrong normal length");

        // TC02: Ensuring the vector is normal
        assertTrue(tri.getNormal(p1).equals(v) || tri.getNormal(p1).equals(v.scale(-1.0)));
    }

    @Test
    void testFindIntersections() {
        Triangle tri = new Triangle(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0));
        Plane plane = new Plane(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0));

        // ============ Equivalence Partitions Tests ==============
        // TC01: Inside triangle
        Ray ray = new Ray(new Point(1, 1, 1), new Vector(-1, -1, -1.5));
        assertEquals(1, tri.findIntersections(ray).size(), "Bad intersection");

        // TC02: Against edge
        ray = new Ray(new Point(0, 0, -1), new Vector(1, 1, 0));
        assertEquals(1, plane.findIntersections(ray).size(), "Wrong intersection with plane");
        assertNull(tri.findIntersections(ray), "Bad intersection");

        // TC03: Against vertex
        ray = new Ray(new Point(0, 0, 1.5), new Vector(-1, -1, 0.5));
        assertEquals(1, plane.findIntersections(ray).size(), "Wrong intersection with plane");
        assertNull(tri.findIntersections(ray), "Bad intersection");

        // =============== Boundary Values Tests ==================
        // TC11: On edge
        ray = new Ray(new Point(-1, -1, 0), new Vector(1, 1, 0));
        assertEquals(1, plane.findIntersections(ray).size(), "Wrong intersection with plane");
        assertNull(tri.findIntersections(ray), "Bad intersection");
        // TC12: In vertex
        ray = new Ray(new Point(0, -1, 0), new Vector(0, 2, 0));
        assertEquals(1, plane.findIntersections(ray).size(), "Wrong intersection with plane");
        assertNull(tri.findIntersections(ray), "Bad intersection");
        // TC13: On edge continuation
        ray = new Ray(new Point(2, 0, 0), new Vector(-1, -1, 0));
        assertEquals(1, plane.findIntersections(ray).size(), "Wrong intersection with plane");
        assertNull(tri.findIntersections(ray), "Bad intersection");
    }
}
