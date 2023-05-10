package lighting;

import primitives.*;
import primitives.Point;
import primitives.Vector;
import static primitives.Util.*;
/**
 * The SpotLight class represents a spotlight source in a scene.
 * It extends the PointLight class.
 */
public class SpotLight extends PointLight {

    private Vector direction;

    //A parameter for narrowing the light beam
    private double narrowBeam = 1d;

    /**
     * Constructs a SpotLight object with the specified intensity, position, and direction.
     *
     * @param intensity  The color intensity of the light.
     * @param position   The position of the light source.
     * @param direction  The direction vector of the light.
     */
    public SpotLight(Color intensity, Point position, Vector direction) {
        super(intensity, position);
        this.direction = direction.normalize();
    }

    /**
     * Sets the narrow beam factor of the spotlight.
     *
     * @param narrowBeam The narrow beam factor.
     * @return The modified SpotLight object.
     */
    public SpotLight setNarrowBeam(double narrowBeam) {
        this.narrowBeam = narrowBeam;
        return this;
    }

    /**
     * Returns the color intensity of the light at the given point, taking into account the spotlight's properties.
     *
     * @param point The point in the scene.
     * @return The color intensity of the light.
     */
    @Override
    public Color getIntensity(Point point) {
        Color Ic = super.getIntensity(point);
        double lDir = getL(point).dotProduct(direction);
        double max = Math.max(0, lDir);
        // For the bonus of getting a spotlight source with a narrower skin beam
        max = Math.pow(max, narrowBeam);
        return Ic.scale(max);
    }
}
