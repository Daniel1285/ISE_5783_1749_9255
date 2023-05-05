package geometries;
import primitives.Point;
import primitives.Ray;
import java.util.List;
/**

 Interface representing an object that can be intersected by a Ray.
 */
public interface Intersectable {

   /**

    Finds and returns a List of all intersection points between this Intersectable object and a given Ray.
    @param ray the Ray to intersect with this object
    @return a List of all intersection points between this object and the given Ray
    */
   public List<Point> findIntersections(Ray ray);
}