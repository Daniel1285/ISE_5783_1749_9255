package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Vector;
import static org.junit.jupiter.api.Assertions.*;

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
}
