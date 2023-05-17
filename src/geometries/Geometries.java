package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
/**

 This class represents a collection of intersectable geometries.

 It extends the Intersectable abstract class, allowing it to be used in intersection calculations with rays.
 */
public class Geometries extends Intersectable {

    /**

     A list of intersectable geometries that make up this collection.
     */
    private List<Intersectable> intersectables;
    /**

     Constructs an empty collection of geometries.
     */
    public Geometries() {
        intersectables = new LinkedList<>();
    }
    /**

     Constructs a collection of geometries from an array of Intersectables.
     @param intersectable An array of Intersectables to add to the collection.
     */
    public Geometries(Intersectable... intersectable) {
        this();
        add(intersectable);
    }
    /**

     Adds an array of Intersectables to the collection.
     @param intersectable An array of Intersectables to add to the collection.
     */
    public void add(Intersectable... intersectable) {
        Collections.addAll(intersectables, intersectable);
    }
    /**

     Finds the intersections of a given Ray with the geometries in this collection.
     @param ray The Ray to intersect with the geometries.
     @return A list of GeoPoints representing the intersections with the geometries and the geometry himself, or null if there are no intersections.
     */
    @Override
    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray,double maxDistance) {
        List<GeoPoint> result = null;
        for (Intersectable item : intersectables) {
            //Calls the findGeoIntersectionsHelper method on the current item
            // to find the intersections between the ray and that item.
            List<GeoPoint> itemList = item.findGeoIntersectionsHelper(ray,maxDistance);
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





