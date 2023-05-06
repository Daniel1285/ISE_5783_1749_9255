package primitives;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RayTests {

    @Test
    void testGetPoint() {

        Ray ray = new Ray(new Point(1,1,1),new Vector(0,0,1));

        assertEquals(new Point(1,1,1),ray.getPoint(0.0000000000000001),"should be p0");

        assertEquals(new Point(1,1,2),ray.getPoint(1),"should be p0 + t * v");
    }
    @Test
    void testFindClosestPoint() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: A point in the middle of the list is the one closest to the beginning of the foundation.
    }
}