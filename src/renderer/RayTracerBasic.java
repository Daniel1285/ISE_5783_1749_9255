package renderer;
import geometries.Intersectable.GeoPoint;
import primitives.Color;
import primitives.Point;
import primitives.Ray;
import scene.Scene;
import java.util.*;
/**

 This class is a basic implementation of a ray tracer for rendering images of 3D scenes.

 It extends the abstract class RayTracerBase and overrides its traceRay method to implement

 the basic ray tracing algorithm.
 */
public class RayTracerBasic extends RayTracerBase{
    /**

     Constructs a new instance of RayTracerBasic with the given Scene.
     @param scene the Scene to be rendered
     */
    public RayTracerBasic(Scene scene){super(scene);}
    /**

     Traces a ray through the scene and calculates the color of the closest intersection point.
     If no intersections are found, returns the background color of the scene.
     @param ray the Ray to be traced
     @return the calculated Color of the closest intersection point, or the background color
     of the scene if no intersections are found
     */
    @Override
    public Color traceRay(Ray ray){
        List<GeoPoint> pointList =  this.scene.geometries.findGeoIntersections(ray);
        if (pointList == null){
            return scene.background;
        }
        GeoPoint closest = ray.findClosestGeoPoint(pointList);
        return calcColor(closest);
    }
    /**

     Calculates the color at a given Point in the scene.
     This implementation only returns the intensity of the ambient light in the scene.
     @param gp the Point at which to calculate the color
     @return the Color at the given Point
     */
    private Color calcColor(GeoPoint gp){
        return scene.ambientLight.getIntensity().add(gp.geometry.getEmission());
    }

}
