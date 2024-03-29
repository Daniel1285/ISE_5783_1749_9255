package renderer;
import primitives.Color;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;
import scene.Scene;

import java.util.List;

/**
 * This is an abstract base class for a ray tracer. It defines a protected Scene object that is shared by all subclasses, and an abstract traceRay method that must be implemented by subclasses to calculate the color of a given ray.
 */
public abstract class RayTracerBase {

    /**
     * The Scene object that contains all the objects in the scene.
     */
    protected Scene scene;

    /**
     * Constructor for the RayTracerBase class.
     * @param scene The Scene object that contains all the objects in the scene.
     */
    public RayTracerBase(Scene scene){
        this.scene = scene;
    }

    /**
     * This method should be implemented by subclasses to calculate the color of a given ray.
     * @param ray The ray to trace.
     * @return The color of the traced ray.
     */
    public abstract Color traceRay(Ray ray);
    /**
     * Traces multiple rays and returns the average color.
     *
     * @param rays The list of rays to trace.
     * @return The average color of the traced rays.
     */
    public Color traceMultipleRays(List<Ray> rays) {
        int size = rays.size(); // Get the number of rays in the list.
        Color avgColor = Color.BLACK; // Initialize the average color to black.

        // Iterate over each ray in the list of rays.
        for (Ray ray : rays) {
            // Trace the current ray and add the resulting color to the average color scaled by 1.0 / size.
            avgColor = avgColor.add(traceRay(ray).reduce(size));
        }

        return avgColor; // Return the average color of the traced rays.
    }

}
