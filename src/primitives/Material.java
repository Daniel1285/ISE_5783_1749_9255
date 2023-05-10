package primitives;
/**
 * The Material class represents the material properties of an object in a scene.
 * It defines the diffuse reflection coefficient (KD), specular reflection coefficient (KS),
 * and shininess factor (nShininess) of the material.
 */
public class Material {

    public Double3 KD = Double3.ZERO;
    public Double3 KS = Double3.ZERO;
    public int nShininess = 0;

    /**
     * Sets the specular reflection coefficient (KS) of the material using a Double3 value.
     *
     * @param ks The specular reflection coefficient as a Double3 value.
     * @return The modified Material object.
     */
    public Material setKs(Double3 ks) {
        this.KS = ks;
        return this;
    }

    /**
     * Sets the diffuse reflection coefficient (KD) of the material using a Double3 value.
     *
     * @param kd The diffuse reflection coefficient as a Double3 value.
     * @return The modified Material object.
     */
    public Material setKd(Double3 kd) {
        this.KD = kd;
        return this;
    }

    /**
     * Sets the specular reflection coefficient (KS) of the material using a double value.
     *
     * @param ks The specular reflection coefficient as a double value.
     * @return The modified Material object.
     */
    public Material setKs(double ks) {
        this.KS = new Double3(ks);
        return this;
    }

    /**
     * Sets the diffuse reflection coefficient (KD) of the material using a double value.
     *
     * @param kd The diffuse reflection coefficient as a double value.
     * @return The modified Material object.
     */
    public Material setKd(double kd) {
        this.KD = new Double3(kd);
        return this;
    }

    /**
     * Sets the shininess factor (nShininess) of the material.
     *
     * @param nShininess The shininess factor.
     * @return The modified Material object.
     */
    public Material setShininess(int nShininess) {
        this.nShininess = nShininess;
        return this;
    }
}
