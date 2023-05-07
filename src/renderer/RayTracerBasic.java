package renderer;

import primitives.Color;
import primitives.Point;
import primitives.Ray;
import scene.Scene;
import java.util.*;
public class RayTracerBasic extends RayTracerBase{
    public RayTracerBasic(Scene scene){super(scene);}

    @Override
    public Color traceRay(Ray ray){
        List<Point> pointList =  this.scene.geometries.findIntersections(ray);
        if (pointList.size() == 0){
            return scene.background;
        }
        Point closest = ray.findClosestPoint(pointList);
        return calcColor(closest);
    }

    private Color calcColor(Point p){
        return scene.ambientLight.getIntensity();
    }

}
