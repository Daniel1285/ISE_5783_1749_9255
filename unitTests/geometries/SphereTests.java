package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

class SphereTests {
    /**
     * Test method for {@link geometries.Sphere#getNormal(primitives.Point)}.
     */
    @Test
    void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============

        Sphere sph = new Sphere(1, new Point(0, 0, 0));
        Vector v = new Vector(0, 0, 1);
        Point p = new Point(0, 0, 1);
        // TC01: Bad normal to sphere
        assertEquals(1d, sph.getNormal(p).length(), 0.00001, "Wrong normal length");

        // TC02: ensuring the vector is normal
        assertEquals(sph.getNormal(p), v, "Wrong normal");
    }
}