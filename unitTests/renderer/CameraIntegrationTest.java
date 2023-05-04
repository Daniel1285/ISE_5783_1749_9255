package renderer;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import geometries.*;
import primitives.*;

import java.util.LinkedList;
import java.util.List;
public class CameraIntegrationTest {

    private void countOfIntersections(Camera cam, Intersectable geo , int amount){
        cam.setVPSize(3, 3);
        cam.setVPDistance(1);
        int nX = 3;
        int nY = 3;

        //List<Point> points = null;
        int sum = 0;
        for (int i = 0; i < nY; ++i) {
            for (int j = 0; j < nX; ++j) {
                List<Point> intersections = geo.findIntersections(cam.constructRay(nX, nY, j, i));
                sum += intersections == null ? 0 : intersections.size();
            }
        }
        assertEquals(amount, sum, "Wrong amount of intersections");

    }
    @Test
    public void cameraRaySphereIntegration() {
        Camera camera1 = new Camera(new Point(0,0,0), new Vector(0, 0, -1), new Vector(0, -1, 0));
        Camera camera2 = new Camera(new Point(0, 0, 0.5), new Vector(0, 0, -1), new Vector(0, -1, 0));

        // TC01: Small Sphere 2 points
        countOfIntersections(camera1, new Sphere(1,new Point(0, 0, -3)), 2);

        // TC02: Big Sphere 18 points
        countOfIntersections(camera2, new Sphere(2.5,new Point(0, 0, -2.5)), 18);

        // TC03: Medium Sphere 10 points
        countOfIntersections(camera2, new Sphere(2,new Point(0, 0, -2)), 10);

        // TC04: Inside Sphere 9 points
        countOfIntersections(camera2, new Sphere(4,new Point(0, 0, -1)), 9);

        // TC05: Beyond Sphere 0 points
        countOfIntersections(camera1, new Sphere(0.5,new Point(0, 0, 1)), 0);
    }
    @Test
    public void cameraRayPlaneIntegration() {
        Camera cam = new Camera(new Point(0,0,0), new Vector(0, 0, -1), new Vector(0, -1, 0));

        // TC01: Plane against camera 9 points
        countOfIntersections(cam, new Plane(new Point(0, 0, -3), new Vector(0, 0, 1)), 9);

        // TC02: Plane with small angle 9 points
        countOfIntersections(cam, new Plane(new Point(0, 0, -3), new Vector(2, -3, -1)), 9);

        // TC03: Plane parallel to lower rays 6 points
        countOfIntersections(cam, new Plane(new Point(0, 0, -5), new Vector(0, 1, 1)), 6);

        // TC04: Beyond Plane 0 points
        countOfIntersections(cam, new Plane(new Point(0, 0, -5), new Vector(0, -1, 0)), 0);
    }

    @Test
    public void cameraRayTriangleIntegration() {
        Camera cam = new Camera(new Point(0,0,0), new Vector(0, 0, -1), new Vector(0, -1, 0));

        // TC01: Small triangle 1 point
        countOfIntersections(cam, new Triangle(new Point(-1, -1, -2),new Point(1, -1, -2), new Point(0, -1, -2)), 1);

        // TC02: Medium triangle 2 points
        countOfIntersections(cam, new Triangle(new Point(1, -1, -2), new Point(-1, -1, -2), new Point(0, 20, -2)), 2);
    }



}
