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
public class RayTracerBasic extends RayTracerBase {
    /**
     * constant for ray head offset size for shading rays
     */
    private static final int MAX_CALC_COLOR_LEVEL = 10;
    private static final double MIN_CALC_COLOR_K = 0.001;
    private static final Double3 K = Double3.ONE;

    /**
     * Constructs a new instance of RayTracerBasic with the given Scene.
     *
     * @param scene the Scene to be rendered
     */
    public RayTracerBasic(Scene scene) {
        super(scene);
    }

    /**
     * Traces a ray through the scene and calculates the color of the closest intersection point.
     * If no intersections are found, returns the background color of the scene.
     *
     * @param ray the Ray to be traced
     * @return the calculated Color of the closest intersection point, or the background color
     * of the scene if no intersections are found
     */
    @Override
    public Color traceRay(Ray ray) {
        GeoPoint closestPoint = findClosestIntersection(ray);
        return closestPoint == null ? scene.background
                : calcColor(closestPoint, ray);
    }
    /**
     * Constructs a refracted ray at the given intersection point with the given incident vector and surface normal.
     * The refracted ray is defined by the point, surface normal, and the refracted vector.
     *
     * @param point The intersection point where the ray originates.
     * @param v     The incident vector.
     * @param n     The surface normal vector.
     * @return The constructed refracted ray.
     */
    private Ray constructRefractedRay(Point point, Vector v, Vector n) {
        return new Ray(point, n, v);
    }
    /**
     * Constructs a reflected ray at the given intersection point with the given incident vector and surface normal.
     * The reflected ray is defined by the point, surface normal, and the reflected vector using the formula r = v - 2*(v.n)*n.
     *
     * @param pointGeo The intersection point where the ray originates.
     * @param v        The incident vector.
     * @param n        The surface normal vector.
     * @return The constructed reflected ray.
     */
    private Ray constructReflectedRay(Point pointGeo, Vector v, Vector n) {
        //r = v - 2.(v.n).n
        double vn = v.dotProduct(n);

        if (vn == 0) {
            return null;
        }

        Vector r = v.subtract(n.scale(2 * vn));
        return new Ray(pointGeo, n, r);
    }


    /**
     * Calculates the color at the given intersection point and ray by considering both local and global effects.
     * The color is calculated by adding the local effects and the global effects to the ambient light intensity.
     *
     * @param geoPoint The intersection point.
     * @param ray      The ray used for the calculation.
     * @return The calculated color at the intersection point.
     */
    private Color calcColor(GeoPoint geoPoint, Ray ray) {
        return calcColor(geoPoint, ray, MAX_CALC_COLOR_LEVEL, K)
                .add(scene.ambientLight.getIntensity());
    }


    /**
     * Calculates the color at the given intersection point and ray by considering both local and global effects recursively.
     * The color is calculated by adding the local effects and the global effects recursively until the maximum recursion level is reached.
     *
     * @param intersection The intersection point.
     * @param ray          The ray used for the calculation.
     * @param level        The current recursion level.
     * @param k            The accumulated transparency coefficient.
     * @return The calculated color at the intersection point.
     */
    private Color calcColor(GeoPoint intersection, Ray ray, int level, Double3 k) {
    Color color = calcLocalEffects(intersection, ray,k);
    return 1 == level ? color : color.add(calcGlobalEffects(intersection, ray, level, k));
    }




    /**
     * Calculates the local effects (diffuse and specular) at the given GeoPoint for the given Ray.
     *
     * @param gp  The GeoPoint at which to calculate the local effects.
     * @param ray The Ray used for the calculation.
     * @return The Color representing the local effects at the GeoPoint.
     */

    private Color calcLocalEffects(GeoPoint gp, Ray ray,Double3 k) {
        Color color = gp.geometry.getEmission();
        Vector v = ray.getDir();
        Vector n = gp.geometry.getNormal(gp.point);
        double nv = alignZero(n.dotProduct(v));
        if (nv == 0) return color;
        Material material = gp.geometry.getMaterial();
        for (LightSource lightSource : scene.lights) {
            Vector l = lightSource.getL(gp.point);
            double nl = alignZero(n.dotProduct(l));
            if (nl * nv > 0) { // sign(nl) == sign(nv)
                //if (unshaded(gp, lightSource, l, n)) {
                    Double3 ktr = transparency(lightSource, l, n, gp);
                    if (!ktr.product(k).lowerThan(MIN_CALC_COLOR_K)) {
                        Color iL = lightSource.getIntensity(gp.point).scale(ktr);
                        color = color.add(
                                calcDiffusive(material.KD, nl, iL),
                                calcSpecular(material.KS, n, l, nl, v, material.nShininess, iL)
                        );
                    }
                //}
            }
        }
        return color;
    }


    /**
     * Calculates the specular reflection color for the given material properties and lighting conditions.
     *
     * @param kS        The specular coefficient of the material.
     * @param n         The surface normal vector.
     * @param l         The vector towards the light source.
     * @param nl        The dot product of the surface normal and the vector towards the light source.
     * @param v         The view vector.
     * @param shininess The shininess factor of the material.
     * @param intensity The intensity of the light source.
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
     * @param kD        The diffuse coefficient of the material.
     * @param nl        The dot product of the surface normal and the vector towards the light source.
     * @param intensity The intensity of the light source.
     * @return The Color representing the diffuse reflection.
     */
    private Color calcDiffusive(Double3 kD, double nl, Color intensity) {
        double abs_nl = Math.abs(nl);
        Double3 amount = kD.scale(abs_nl);
        return intensity.scale(amount);
    }

    /**
     * Checks if the given point is unshaded by other objects in the scene in the direction of the light source.
     *
     * @param gp    the intersection point
     * @param l     the direction vector towards the light source
     * @param n     the surface normal vector at the intersection point
     * @param lightSource the light source
     * @return true if the point is unshaded, false otherwise
     */
//
    private boolean unshaded(GeoPoint gp, LightSource lightSource, Vector l, Vector n) {

        Vector lightDirection = l.scale(-1); // from point to light source

        Ray lightRay = new Ray(gp.point,n,lightDirection);
        double lightDistance = lightSource.getDistance(gp.point);
        List<GeoPoint> intersections = scene.geometries.findGeoIntersections(lightRay,lightDistance);

        if (intersections == null){
            return true;
        }

        for (var item : intersections){
            if (item.geometry.getMaterial().getKt().lowerThan(MIN_CALC_COLOR_K)) {
                    return false;
                }
        }

        return true;
    }
    /**
     * Finds the closest intersection between the given ray and the scene's geometries.
     *
     * @param ray The ray to intersect with the geometries.
     * @return The closest intersection point (GeoPoint), or null if no intersections were found.
     */
    private GeoPoint findClosestIntersection(Ray ray) {
        List<GeoPoint> intersections = scene.geometries.findGeoIntersections(ray);
        if (intersections == null) {
            return null;
        }
        return ray.findClosestGeoPoint(intersections);
    }


    /**
     * Calculates the global effects (reflection and refraction) at the given GeoPoint for the given Ray.
     *
     * @param gp    The GeoPoint at which to calculate the global effects.
     * @param ray   The Ray used for the calculation.
     * @param level The recursion level.
     * @param k     The accumulated transparency factor.
     * @return The Color representing the global effects at the GeoPoint.
     */
    private Color calcGlobalEffects(GeoPoint gp, Ray ray, int level, Double3 k) {
        Color color = Color.BLACK;
        Vector v = ray.getDir();
        Vector n = gp.geometry.getNormal(gp.point);
        Material material = gp.geometry.getMaterial();
        Double3 kr = material.KR, kkr = k.product(kr);
        Ray reflectedRay = constructReflectedRay(gp.point,v,n);
        if (!kkr.lowerThan(MIN_CALC_COLOR_K)) {
            color = color.add(calcGlobalEffect(reflectedRay, level - 1, kr, kkr)).scale(kr);
        }
        Double3 kt = material.KT, kkt = k .product(kt);
        Ray refractedRay = constructRefractedRay(gp.point,v,n);
        if (!kkt.lowerThan(MIN_CALC_COLOR_K)) {
            color = color.add(calcGlobalEffect(refractedRay, level - 1, kt, kkt)).scale(kt);
        }
        return color;
    }
    /**
     * Calculates the color contributed by global effects (reflection or refraction) for a given ray and level.
     *
     * @param ray   The ray to trace for global effects.
     * @param level The recursion level.
     * @param k     The accumulated transparency factor.
     * @param kx    The transparency factor of the current surface.
     * @return The Color representing the global effects contribution.
     */
    private Color calcGlobalEffect(Ray ray, int level, Double3 k, Double3 kx) {
        Double3 kkx = k.product(kx);
        if (kkx.lowerThan(MIN_CALC_COLOR_K)) return Color.BLACK;
        GeoPoint gp = findClosestIntersection(ray);
        if (gp == null) return scene.background.scale(kx);
        return isZero(gp.geometry.getNormal(gp.point).dotProduct(ray.getDir()))
                ? Color.BLACK : calcColor(gp, ray, level - 1, kkx);
    }
    /**
     * Calculates the transparency factor (ktr) for a given light source, light direction, surface normal, and intersection point.
     *
     * @param lightSource The light source.
     * @param l           The light direction.
     * @param n           The surface normal.
     * @param gp          The intersection point.
     * @return The transparency factor (ktr).
     */
    private Double3 transparency(LightSource lightSource, Vector l, Vector n, GeoPoint gp) {
        Vector lightDirection = l.scale(-1); // from point to light source
        Ray lightRay = new Ray(gp.point, n, lightDirection);
        double lightDistance = lightSource.getDistance(gp.point);
        List<GeoPoint> intersections = scene.geometries.findGeoIntersections(lightRay,lightDistance);
        if (intersections == null) {
            return K;
        }
        Double3 ktr = Double3.ONE;
        for (GeoPoint geoPoint : intersections) {
            Double3 kt = geoPoint.geometry.getMaterial().getKt();
            ktr = ktr.product(kt);
            if (ktr.lowerThan(MIN_CALC_COLOR_K)) {
                return Double3.ZERO;
            }
        }
        return ktr;
    }
}