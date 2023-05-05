package geometries;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import primitives.Vector;
import static org.junit.jupiter.api.Assertions.*;
import primitives.Point;
import primitives.Ray;
import java.util.List;

/* Testing Sphere */
class SphereTests {

    /* Test method for {@link geometries.Sphere#getNormal(primitives.Point)}.
            */
    @Test
    void testGetNormal() {

        Sphere sph = new Sphere(1, new Point(0, 0, 0));
        Vector v = new Vector(0, 0, 1).normalize();
        Point p = new Point(0, 0, 1);

        // ============ Equivalence Partitions Tests ==============

        // TC01: Bad normal
        assertEquals(1d, sph.getNormal(p).length(), 0.00001, "Wrong normal length");

        // TC02: Ensuring the vector is normal
        assertEquals(sph.getNormal(p), v, "Wrong normal for sphere");
    }

    /**
     * Test method for {@link geometries.Sphere#findIntersections(primitives.Ray)}.
     */
    @Test
    public void testFindIntersections() {
        Sphere sphere = new Sphere(1d,new Point(1, 0, 0) );

        // ============ Equivalence Partitions Tests ==============
        Point gp1 = new Point(0.0651530771650466, 0.355051025721682, 0);
        Point gp2 = new Point(1.53484692283495, 0.844948974278318, 0);
        List<Point> exp = List.of(gp1, gp2);
        // TC01: Ray's line is outside the sphere
        assertNull(sphere.findIntersections(new Ray(new Point(-1, 0, 0), new Vector(1, 1, 0))),
                "Ray's line out of sphere");
        // TC02: Ray starts before and crosses the sphere
        List<Point> result = sphere.findIntersections(new Ray(new Point(-1, 0, 0), new Vector(3, 1, 0)));
        assertEquals(2, result.size(), "Wrong number of points");
        if (result.get(0).getX() > result.get(1).getX())
            result = List.of(result.get(1), result.get(0));
        assertEquals(exp, result, "Ray crosses sphere");
        // TC03: Ray starts inside the sphere
        assertEquals(List.of(gp2), sphere.findIntersections(new Ray(new Point(0.5, 0.5, 0), new Vector(3, 1, 0))),
                "Ray from inside sphere");
        // TC04: Ray starts after the sphere
        assertNull(sphere.findIntersections(new Ray(new Point(2, 1, 0), new Vector(3, 1, 0))),
                "Sphere behind Ray");
        // =============== Boundary Values Tests ==================
        // **** Group: Ray's line crosses the sphere (but not the center)
        // TC11: Ray starts at sphere and goes inside
        assertEquals(1, sphere.findIntersections(new Ray(new Point(1, -1, 0), new Vector(1, 1, 0))).size(),
                "Ray from sphere inside");
        // TC12: Ray starts at sphere and goes outside
        assertNull(sphere.findIntersections(new Ray(new Point(2, 0, 0), new Vector(1, 1, 0))),
                "Ray from sphere outside");

        // **** Group: Ray's line goes through the center
        // TC13: Ray starts before the sphere
        result = sphere.findIntersections(new Ray(new Point(1, -2, 0), new Vector(0, 1, 0)));
        assertEquals(2, result.size(), "Wrong number of points");
        if (result.get(0).getY() > result.get(1).getY())
            result = List.of(result.get(1), result.get(0));
        assertEquals(2, result.size(), "Line through O, ray crosses sphere");
        // TC14: Ray starts at sphere and goes inside
        assertEquals(1, sphere.findIntersections(new Ray(new Point(1, -1, 0), new Vector(0, 1, 0))).size(),
                "Line through O, ray from and crosses sphere");
        // TC15: Ray starts inside (1 point)
        assertEquals(1, sphere.findIntersections(new Ray(new Point(0.5, 0.5, 0), new Vector(0.5, 1, 0))).size(),
                "Line through O, ray from inside sphere");
        // TC16: Ray starts at the center (1 point)
        assertEquals(1, sphere.findIntersections(new Ray(new Point(1, 0, 0), new Vector(0, 1, 0))).size(),
                "Line through O, ray from O");
        // TC17: Ray starts at sphere and goes outside
        assertNull(sphere.findIntersections(new Ray(new Point(1, 1, 0), new Vector(0, 1, 0))),
                "Line through O, ray from sphere outside");
        // TC18: Ray starts after sphere
        assertNull(sphere.findIntersections(new Ray(new Point(1, 2.5, 0), new Vector(0, 0.5, 0))),
                "Line through O, ray outside sphere");

        // **** Group: Ray's line is tangent to the sphere
        // TC19: Ray starts before the tangent point
        assertNull(sphere.findIntersections(new Ray(new Point(0, 1, 0), new Vector(1, 0, 0))),
                "Tangent line, ray before sphere");
        // TC20: Ray starts at the tangent point
        assertNull(sphere.findIntersections(new Ray(new Point(1, 1, 0), new Vector(1, 0, 0))),
                "Tangent line, ray at sphere");
        // TC21: Ray starts after the tangent point
        assertNull(sphere.findIntersections(new Ray(new Point(2, 1, 0), new Vector(1, 0, 0))),
                "Tangent line, ray after sphere");

        // **** Group: Special cases
        // TC19: Ray's line is outside, ray is orthogonal to ray start to sphere's center line
        assertNull(sphere.findIntersections(new Ray(new Point(-1, 0, 0), new Vector(0, 0, 1))),
                "Ray orthogonal to ray head -> O line");
    }
}