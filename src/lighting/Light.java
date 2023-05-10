package lighting;

import primitives.Color;

/**
 * The Light class represents a light source in a scene.
 * It is an abstract class that provides the common functionality for different types of lights.
 */
public abstract class Light {

    private Color intensity;

    /**
     * Constructs a Light object with the specified intensity.
     *
     * @param intensity The color intensity of the light.
     */
    protected Light(Color intensity) {
        this.intensity = intensity;
    }

    /**
     * Returns the color intensity of the light.
     *
     * @return The color intensity of the light.
     */
    public Color getIntensity() {
        return intensity;
    }
}
