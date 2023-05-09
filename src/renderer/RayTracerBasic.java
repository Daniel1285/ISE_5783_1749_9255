package renderer;
import geometries.Intersectable.GeoPoint;
import primitives.*;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;
import scene.Scene;
import java.util.*;
import static primitives.Util.*;
import lighting.*;

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
        return calcColor(closest, ray);
    }
    /**

     Calculates the color at a given Point in the scene.
     This implementation only returns the intensity of the ambient light in the scene.
     @param gp the Point at which to calculate the color
     @return the Color at the given Point
     */
    private Color calcColor(GeoPoint gp,Ray ray){
        return scene.ambientLight.getIntensity().add(calcLocalEffects(gp,ray));
    }
    private Color calcLocalEffects(GeoPoint gp, Ray ray) {
        Color color = gp.geometry.getEmission();
        Vector v = ray.getDir();
        Vector n = gp.geometry.getNormal(gp.point);
        double nv = alignZero(n.dotProduct(v));
        if (nv == 0)
            return color;
        Material mat = gp.geometry.getMaterial();
        for (LightSource lightSource : scene.lights) {
            Vector l = lightSource.getL(gp.point);
            double nl = alignZero(n.dotProduct(l));
            if (nl * nv > 0) { // sign(nl) == sing(nv)
                Color iL = lightSource.getIntensity(gp.point);
                color = color.add(calcDiffusive(mat.KD, nl,iL),
                        calcSpecular(mat.KS, n, l, nl, v,mat.nShininess,iL));
            }
        }
        return color;
    }
    private Color calcSpecular(Double3 kS, Vector n, Vector l, double nl,Vector v,int shininess,Color intensity) {
        Vector r = l.add(n.scale(-2 * nl)); // nl must not be zero!
        double VR = -alignZero(r.dotProduct(v));
        if (VR <= 0)
            return Color.BLACK; // view from direction opposite to r vector
        Double3 amount =kS.scale(Math.pow(VR, shininess));
        return intensity.scale(amount);
    }

    private Color calcDiffusive(Double3 kD, double nl,  Color intensity) {
        double abs_nl = Math.abs(nl);
        Double3 amount =kD.scale(abs_nl);
        return intensity.scale(amount);
    }

}
