package lighting;
import primitives.*;
public interface LightSource {
    public Color getIntensity(Point p);
    public Vector getL(Point p);
}
