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

   /**
    * Finds the intersections between the intersectable object and a given ray.
    * Returns a list of GeoPoint objects representing the intersections.
    *
    * @param ray The ray to intersect with.
    * @return A list of GeoPoint objects representing the intersections.
    */
   public List<GeoPoint> findGeoIntersections(Ray ray) {
      return findGeoIntersectionsHelper(ray);
   }

   protected abstract List<GeoPoint> findGeoIntersectionsHelper(Ray ray);

}