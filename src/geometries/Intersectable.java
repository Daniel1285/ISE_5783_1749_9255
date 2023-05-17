package geometries;
import primitives.Point;
import primitives.Ray;
import java.util.List;
import java.util.Objects;

/**

 Interface representing an object that can be intersected by a Ray.
 */
public abstract class Intersectable {
   /**
    * The GeoPoint class represents a point of intersection between a geometry and a ray.
    * It contains references to the geometry object and the intersection point.
    * This class is a nested static class within the Intersectable class.
    */
   public static class GeoPoint {
      public Geometry geometry;
      public Point point;

      public GeoPoint(Geometry geometry, Point point){
         this.geometry = geometry;
         this.point = point;
      }


      @Override
      public boolean equals(Object o) {
         if (this == o) return true;
         if (o == null || getClass() != o.getClass()) return false;
         GeoPoint geoPoint = (GeoPoint) o;
         return geometry.equals(geoPoint.geometry) && point.equals(geoPoint.point);
      }

      @Override
      public int hashCode() {
         return Objects.hash(geometry, point);
      }

      @Override
      public String toString() {
         return "GeoPoint{" +
                 "geometry=" + geometry +
                 ", point=" + point +
                 '}';
      }
   }

   /**
    * Finds the intersections between the intersectable object and a given ray.
    * Returns a list of intersection points.
    *
    * @param ray The ray to intersect with.
    * @return A list of intersection points, or null if no intersections are found.
    */
   public List<Point> findIntersections(Ray ray) {
      var geoList = findGeoIntersections(ray);
      return geoList == null ? null : geoList.stream().map(gp -> gp.point).toList();
   }
   public final List<GeoPoint> findGeoIntersections(Ray ray) {
      return findGeoIntersections(ray, Double.POSITIVE_INFINITY);
   }

   /**
    * Finds the intersections between the intersectable object and a given ray.
    * Returns a list of GeoPoint objects representing the intersections.
    *
    * @param ray The ray to intersect with.
    * @return A list of GeoPoint objects representing the intersections.
    */
   public final List<GeoPoint> findGeoIntersections(Ray ray, double maxDistance) {
      return findGeoIntersectionsHelper(ray, maxDistance);
   }
   /**
    * Finds the intersections between a ray and a collection of geographic points within a maximum distance.
    *
    * @param ray         The ray representing the starting point and direction of the intersection search.
    * @param maxDistance The maximum distance within which to search for intersections.
    * @return A list of geographic points that intersect with the ray within the specified maximum distance.
    */
   protected abstract List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance);


}