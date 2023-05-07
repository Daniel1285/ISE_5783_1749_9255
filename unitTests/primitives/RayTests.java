package primitives;

import org.junit.jupiter.api.Test;

import java.util.*;

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
        List<Point> points = new LinkedList<>();

        Ray ray = new Ray(new Point(0,1,0),new Vector(1,2,3));

        Point p1 = new Point(1,2,3);
        Point p2 = new Point(1,1,3);
        Point p3 = new Point(0,0.5,0);
        // =============== Boundary Values Tests ==================
        // TC01: An empty list (the method should return a null value)
        assertNull(ray.findClosestPoint(points),"Should return NULL!");

        points.add(p3);
        points.add(p2);
        points.add(p1);

        // TC02: The first point is closest to the beginning of the ray
        assertEquals(points.get(0),ray.findClosestPoint(points),"The first point should be the closest to the ray");

        points.clear();
        points.add(p1);
        points.add(p2);
        points.add(p3);

        // TC02: The last point is closest to the beginning of the ray
        assertEquals(points.get(2),ray.findClosestPoint(points),"The first point should be the closest to the ray");

        // ============ Equivalence Partitions Tests ==============
        // TC11: A point in the middle of the list is the one closest to the beginning of the ray.
        points.clear();
        points.add(p1);
        points.add(p3);
        points.add(p2);

        assertEquals(points.get(1),ray.findClosestPoint(points),"The middle point should be the closest to the ray");


    }
}