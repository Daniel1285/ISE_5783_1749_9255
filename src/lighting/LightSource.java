package lighting;
import primitives.*;
/**
 * The LightSource interface represents a light source in a scene.
 * It defines methods for obtaining the color intensity and direction of the light at a given point.
 */
public interface LightSource {

    /**
     * Returns the color intensity of the light at the given point.
     *
     * @param p The point in the scene.
     * @return The color intensity of the light.
     */
    public Color getIntensity(Point p);

    /**
     * Returns the direction vector of the light at the given point.
     *
     * @param p The point in the scene.
     * @return The direction vector of the light.
     */
    public Vector getL(Point p);
}
