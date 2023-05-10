package scene;

import geometries.Geometries;
import lighting.*;
import primitives.Color;

import java.util.LinkedList;
import java.util.List;

/**

 The Scene class represents a scene in the ray tracing process. It contains information such as the name of the scene,

 the background color, the ambient light, and the geometries that compose the scene.
 */
public class Scene {

    /**

     The name of the scene.
     */
    public String name;
    /**

     The background color of the scene.
     */
    public Color background = Color.BLACK;
    /**

     The ambient light of the scene.
     */
    public AmbientLight ambientLight = AmbientLight.NONE;
    /**

     The geometries that compose the scene.
     */
    public Geometries geometries = new Geometries();

    /**
     * A list of light sources in the scene.
     */
    public List<LightSource> lights = new LinkedList<>();
    /**

     Constructs a new Scene object with the given name.
     @param name the name of the scene
     */
    public Scene(String name) {
        this.name = name;
        geometries = new Geometries();
    }
    /**

     Sets the name of the scene.
     @param name the name of the scene
     @return this Scene object
     */
    public Scene setName(String name) {
        this.name = name;
        return this;
    }
    /**

     Sets the background color of the scene.
     @param background the background color of the scene
     @return this Scene object
     */
    public Scene setBackground(Color background) {
        this.background = background;
        return this;
    }
    /**

     Sets the ambient light of the scene.
     @param ambientLight the ambient light of the scene
     @return this Scene object
     */
    public Scene setAmbientLight(AmbientLight ambientLight) {
        this.ambientLight = ambientLight;
        return this;
    }
    /**

     Sets the geometries that compose the scene.
     @param geometries the geometries that compose the scene
     @return this Scene object
     */
    public Scene setGeometries(Geometries geometries) {
        this.geometries = geometries;
        return this;
    }
    /**
     * Sets the list of light sources in the scene.
     *
     * @param lights The list of light sources to set.
     * @return The updated Scene object.
     */
    public Scene setLights(List<LightSource> lights) {
        this.lights = lights;
        return this;
    }

}