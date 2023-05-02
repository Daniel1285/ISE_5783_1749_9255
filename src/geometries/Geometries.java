package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
public class Geometries implements Intersectable{
    private List<Intersectable> intersectables;
    public Geometries(){
        intersectables = new LinkedList<>();
    }
    public Geometries(Intersectable... intersectable) {}
    public void add(Intersectable... intersectable) {
        Collections.addAll(intersectables, intersectable);
    }

    @Override
    public List<Point> findIntersections(Ray ray) {
        List<Point> result = null;
        for (Intersectable item : intersectables) {
            List<Point> itemList = item.findIntersections(ray);
            if (itemList != null) {
                if (result == null) {
                    result = new LinkedList<>();
                }
                result.addAll(itemList);
            }
        }
        return result;
    }

}
