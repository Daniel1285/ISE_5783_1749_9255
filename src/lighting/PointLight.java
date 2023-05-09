package lighting;

import primitives.*;
import primitives.Point;

public class PointLight extends Light implements LightSource{
    private Point position;
    private double Kc = 1;
    private double Kl = 0;
    private double Kq = 0;

    public PointLight(Color intensity,Point position){
        super(intensity);
        this.position = position;
    }
    public PointLight setKc(double kc) {
        this.Kc = kc;
        return this;
    }

    public PointLight setKl(double kl) {
        this.Kl = kl;
        return this;
    }

    public PointLight setKq(double kq) {
        this.Kq = kq;
        return this;
    }
    @Override
    public Color getIntensity(Point point) {
        Color Ic = getIntensity();
        double distance = point.distance(position);
        double distanceSquared = point.distanceSquared(position);
        double factor = Kc + Kl * distance + Kq * distanceSquared;
        return Ic.reduce(factor);
    }

    @Override
    public Vector getL(Point point) {
        return point.subtract(position).normalize();
    }




}
