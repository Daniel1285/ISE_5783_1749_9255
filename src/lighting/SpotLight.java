package lighting;

import primitives.*;
import primitives.Point;
import primitives.Vector;
import static primitives.Util.*;

public class SpotLight extends PointLight {
    private Vector direction;

    public SpotLight(Color intensity, Point position,Vector direction){
        super(intensity,position);
        this.direction = direction.normalize();
    }
    @Override
    public Color getIntensity(Point point) {
        Color Ic = super.getIntensity(point);
        double projection = getL(point).dotProduct(direction);
        double factor = Math.max(0,projection);
        return Ic.scale(factor);
    }


}
