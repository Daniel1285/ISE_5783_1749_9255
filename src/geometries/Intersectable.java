package geometries;
import primitives.Point;
import primitives.Ray;
import java.util.List;
import java.util.Objects;

/**

 Interface representing an object that can be intersected by a Ray.
 */
public abstract class Intersectable {
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

    Finds and returns a List of all intersection points between this Intersectable object and a given Ray.
    @param ray the Ray to intersect with this object
    @return a List of all intersection points between this object and the given Ray
    */
   public List<Point> findIntersections(Ray ray) {
      var geoList = findGeoIntersections(ray);
      return geoList == null ? null : geoList.stream().map(gp -> gp.point).toList();
   }
   public List<GeoPoint> findGeoIntersections(Ray ray) {
      return findGeoIntersectionsHelper(ray);
   }
   protected abstract List<GeoPoint> findGeoIntersectionsHelper(Ray ray);

}