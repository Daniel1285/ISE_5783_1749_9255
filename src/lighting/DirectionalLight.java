package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

/**
 * The DirectionalLight class represents a directional light source in a scene.
 * It extends the Light class and implements the LightSource interface.
 */
public class DirectionalLight extends Light implements LightSource {
    private Vector direction;
    /**
     * Constructs a DirectionalLight object with the specified intensity and direction.
     *
     * @param intensity The color intensity of the light.
     * @param direction The direction vector of the light.
     */
    public DirectionalLight(Color intensity, Vector direction) {
        super(intensity);
        this.direction = direction.normalize();
    }

    /**
     * Returns the color intensity of the light at the given point.
     *
     * @param point The point in the scene.
     * @return The color intensity of the light.
     */
    @Override
    public Color getIntensity(Point point) {
        return getIntensity();
    }

    /**
     * Returns the direction vector of the light at the given point.
     *
     * @param point The point in the scene.
     * @return The direction vector of the light.
     */
    @Override
    public Vector getL(Point point) {
        return direction;
    }
    @Override
    public double getDistance(Point point){
        return Double.POSITIVE_INFINITY;
    }

}
