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
     * Calculates the color at the given GeoPoint for the given Ray by combining the ambient light intensity and
     * the local effects (diffuse and specular) at the GeoPoint.
     *
     * @param gp  The GeoPoint at which to calculate the color.
     * @param ray The Ray used for the calculation.
     * @return The Color representing the final color at the GeoPoint.
     */
    private Color calcColor(GeoPoint gp, Ray ray) {
        return scene.ambientLight.getIntensity().add(calcLocalEffects(gp, ray));
    }

    /**
     * Calculates the local effects (diffuse and specular) at the given GeoPoint for the given Ray.
     *
     * @param gp  The GeoPoint at which to calculate the local effects.
     * @param ray The Ray used for the calculation.
     * @return The Color representing the local effects at the GeoPoint.
     */
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
            if (nl * nv > 0) { // sign(nl) == sign(nv)
                Color iL = lightSource.getIntensity(gp.point);
                color = color.add(
                        calcDiffusive(mat.KD, nl, iL),
                        calcSpecular(mat.KS, n, l, nl, v, mat.nShininess, iL)
                );
            }
        }
        return color;
    }

    /**
     * Calculates the specular reflection color for the given material properties and lighting conditions.
     *
     * @param kS         The specular coefficient of the material.
     * @param n          The surface normal vector.
     * @param l          The vector towards the light source.
     * @param nl         The dot product of the surface normal and the vector towards the light source.
     * @param v          The view vector.
     * @param shininess  The shininess factor of the material.
     * @param intensity  The intensity of the light source.
     * @return The Color representing the specular reflection.
     */
    private Color calcSpecular(Double3 kS, Vector n, Vector l, double nl, Vector v, int shininess, Color intensity) {
        Vector r = l.add(n.scale(-2 * nl));
        double VR = -alignZero(r.dotProduct(v));
        if (VR <= 0)
            return Color.BLACK;
        Double3 amount = kS.scale(Math.pow(VR, shininess));
        return intensity.scale(amount);
    }

    /**
     * Calculates the diffuse reflection color for the given material properties and lighting conditions.
     *
     * @param kD         The diffuse coefficient of the material.
     * @param nl         The dot product of the surface normal and the vector towards the light source.
     * @param intensity  The intensity of the light source.
     * @return The Color representing the diffuse reflection.
     */
    private Color calcDiffusive(Double3 kD, double nl, Color intensity) {
        double abs_nl = Math.abs(nl);
        Double3 amount = kD.scale(abs_nl);
        return intensity.scale(amount);
    }


}
