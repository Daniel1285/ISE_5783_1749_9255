package geometries;

import primitives.Color;
import primitives.Material;
import primitives.Point;
import primitives.Vector;

/**
 * The Geometry class represents a geometric object that can be intersected with other objects.
 * It provides methods to set and retrieve the emission color and material properties.
 * This class is abstract and should be subclassed to create specific types of geometry.
 */
public abstract class Geometry extends Intersectable {
    protected Color emission = Color.BLACK;
    private Material material = new Material();
    /**
     * Returns the emission color of the geometry.
     *
     * @return The emission color.
     */
    public Color getEmission() {
        return emission;
    }

    /**
     * Sets the emission color of the geometry.
     *
     * @param emission The new emission color to be set.
     * @return The current instance of the Geometry class.
     */
    public Geometry setEmission(Color emission) {
        this.emission = emission;
        return this;
    }

    /**
     * Returns the material of the geometry.
     *
     * @return The material.
     */
    public Material getMaterial() {
        return material;
    }

    /**
     * Sets the material of the geometry.
     *
     * @param material The new material to be set.
     * @return The current instance of the Geometry class.
     */
    public Geometry setMaterial(Material material) {
        this.material = material;
        return this;
    }

    /**
     * Calculates the normal vector of a geometry at a given point.
     *
     * @param point The point on the geometry to get the normal vector at.
     * @return The normal vector at the given point.
     */
    public abstract Vector getNormal(Point point);
}

