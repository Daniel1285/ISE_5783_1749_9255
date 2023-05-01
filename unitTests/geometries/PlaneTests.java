package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Vector;
import static org.junit.jupiter.api.Assertions.*;

class PlaneTests {

    /** Test method for {@link geometries.Plane#Plane(Point, Point, Point)}. */
    @Test
    public void testConstructor() {
        // ============ Boundary Values Tests ==============

        // TC10: Two points are the same
        assertThrows(IllegalArgumentException.class, //
                () -> new Plane(new Point(1, 1, 2), new Point(1, 1, 2),
                        new Point(1, 0, 0)), //
                "Two points coincide, a plane cannot be constructed");

        // TC11: All the points lie on the same line
        assertThrows(IllegalArgumentException.class, //
                () -> new Plane(new Point(1, 1, 1), new Point(1, 1, 2),
                        new Point(1, 1, 3)), //
                "All the points are in the same collinear points");
    }

    /** Test method for {@link geometries.Plane#getNormal(Point)}. */
    @Test
    void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============

        Plane pol = new Plane(new Point(1,0,0), new Point(0,1,0), new Point(0,0,1));
        Vector v = new Vector(1, 1, 1).normalize();

        // TC01: Bad normal
        assertEquals(1d, pol.getNormal().length(), 0.00001, "Wrong normal length");

        // TC02: Ensuring the vector is normal
        assertTrue(pol.getNormal().equals(v) || pol.getNormal().equals(v.scale(-1.0)),
                "Wrong normal");

    }



}