package renderer;
import primitives.Color;
import primitives.Ray;
import scene.Scene;
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
}
