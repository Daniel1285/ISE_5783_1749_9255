package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Vector;
import primitives.Ray;
import static org.junit.jupiter.api.Assertions.*;

class TubeTests {
    /**
     * Test method for {@link geometries.Tube#getNormal(primitives.Point)}.
     */
    @Test
    void testGetNormal() {

        Point p0 = new Point(0, 0, 0);
        Vector v = new Vector(1, 0, 0);
        Ray ray = new Ray(p0, v);
        Tube tube = new Tube(1, ray);

        // ============ Equivalence Partitions Tests ==============

        // TC01: Bad normal
        assertEquals(1d, tube.getNormal(new Point(1, 1, 0)).length(),
                0.00001, "Wrong normal length");

        // TC02: Ensuring the vector is normal
        assertEquals(tube.getNormal(new Point(1, 0, 1)), new Vector(0, 0, 1), "Wrong normal");

        // =============== Boundary Values Tests ==================

        // TC10: Extreme case when (P - P0) is orthogonal to v
        assertThrows(IllegalArgumentException.class, () -> tube.getNormal(new Point(0, 1, 0)),
                "The ray of the tube is orthogonal to (P - P0)");
    }

}