import geometries.Geometries;
import geometries.Plane;
import geometries.Sphere;
import geometries.Triangle;
import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;
import static org.junit.jupiter.api.Assertions.*;

public class GeometriesTests {
    @Test
    void testFindIntersections() {
        Geometries geo = new Geometries();
        Ray ray = new Ray(new Point(3,3,0.5),new Vector(-2,-2,0));

        // ============ Boundary Values Tests ==============

        // TC01: An empty body collection
        assertNull(geo.findIntersections(ray),"Must not be an intersection");

        geo.add(new Sphere(1,new Point(1,0,0)),
                new Triangle(new Point(1,0,0),new Point(0,1,0),new Point(0,0,1)),
                new Plane(new Point(1,0,0),new Point(0,1,0),new Point(0,0,1))
        );
        // TC02: All shapes are intersection
        assertEquals(4,geo.findIntersections(ray).size(),"All shapes must be intersection ");

        // TC03: No shape is intersection
        ray = new Ray(new Point(4,5,0),new Vector(2,3,5));
        assertNull(geo.findIntersections(ray),"No shape is supposed to be intersection");

        // TC04: Only one shape is intersection
        ray = new Ray(new Point(2,2,0),new Vector(-2,-2,2));
        assertEquals(1,geo.findIntersections(ray).size()," Only one shape should be intersection");


        // ============ Equivalence Partitions Tests ==============

        // TC10: Some shapes (but not all) are intersection
        ray = new Ray(new Point(2,1.2,0),new Vector(-2,-2,1));
        assertEquals(3,geo.findIntersections(ray).size(),"Only a few shapes need to be intersection ");
    }
}
