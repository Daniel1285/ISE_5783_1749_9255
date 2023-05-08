package lighting;

import primitives.Color;
import primitives.Double3;

/**

 The AmbientLight class represents a type of light source that evenly illuminates all objects in a scene, regardless of their position or orientation.

 It takes in a Color object representing the intensity of the light source, and a Double3 object representing the ambient reflection coefficient of the object.
 */
public class AmbientLight {

    /**

     The intensity of the AmbientLight.
     */
    private Color intensity;
    /**

     Constant representing an AmbientLight with zero intensity and reflection coefficient.
     */
    public static final AmbientLight NONE = new AmbientLight(Color.BLACK, Double3.ZERO);
    /**

     Constructs an AmbientLight with the specified intensity and ambient reflection coefficient.
     @param Ia a Color object representing the intensity of the light source
     @param Ka a Double3 object representing the ambient reflection coefficient of the object
     */
    public AmbientLight(Color Ia, Double3 Ka) {
        this.intensity = Ia.scale(Ka);
    }
    /**

     Returns the intensity of the AmbientLight.
     @return a Color object representing the intensity of the light source
     */
    public Color getIntensity(){
        return intensity;
    }
}