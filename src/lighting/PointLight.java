package lighting;

import primitives.*;
import primitives.Point;

/**
 * The PointLight class represents a point light source in a scene.
 * It extends the Light class and implements the LightSource interface.
 */
public class PointLight extends Light implements LightSource {

    private Point position;
    private Double3 Kc = Double3.ONE;
    private Double3 Kl = Double3.ZERO;
    private Double3 Kq = Double3.ZERO;

    /**
     * Constructs a PointLight object with the specified intensity and position.
     *
     * @param intensity The color intensity of the light.
     * @param position  The position of the light source.
     */
    public PointLight(Color intensity, Point position) {
        super(intensity);
        this.position = position;
    }

    /**
     * Sets the constant attenuation factor of the point light.
     *
     * @param kc The constant attenuation factor.
     * @return The modified PointLight object.
     */
    public PointLight setKc(double kc) {
        this.Kc = new Double3(kc);
        return this;
    }
    public PointLight setKc(Double3 kc) {
        this.Kc = kc;
        return this;
    }

    /**
     * Sets the linear attenuation factor of the point light.
     *
     * @param kl The linear attenuation factor.
     * @return The modified PointLight object.
     */
    public PointLight setKl(double kl) {
        this.Kl = new Double3(kl);;
        return this;
    }
    public PointLight setKl(Double3 kl) {
        this.Kl = kl;
        return this;
    }

    /**
     * Sets the quadratic attenuation factor of the point light.
     *
     * @param kq The quadratic attenuation factor.
     * @return The modified PointLight object.
     */
    public PointLight setKq(double kq) {
        this.Kq =new Double3(kq);
        return this;
    }


    public PointLight setKq(Double3 kq) {
        this.Kq = kq;
        return this;
    }


    /**
     * Returns the color intensity of the light at the given point.
     *
     * @param point The point in the scene.
     * @return The color intensity of the light.
     */
    @Override
    public Color getIntensity(Point point) {
        Color Ic = getIntensity();
        double distance = point.distance(position);
        double distanceSquared = point.distanceSquared(position);
        Double3 factor = Kc.add(Kl.scale(distance).add(Kq.scale(distanceSquared)));
        return Ic.reduce(factor);
    }

    /**
     * Returns the direction vector of the light at the given point.
     *
     * @param point The point in the scene.
     * @return The direction vector of the light.
     */
    @Override
    public Vector getL(Point point) {
        return point.subtract(position).normalize();
    }
    @Override
    public double getDistance(Point point){
        return point.distance(this.position);
    }
}
