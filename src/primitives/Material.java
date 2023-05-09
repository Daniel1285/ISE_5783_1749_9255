package primitives;

public class Material {
    public Double3 KD = Double3.ZERO;
    public Double3 KS = Double3.ZERO;
    public int nShininess = 0;
    public Material setKs(Double3 ks) {
        this.KS = ks;
        return this;
    }

    public Material setKd(Double3 kd) {
        this.KD = kd;
        return this;
    }
    public Material setKs(double ks) {
        this.KS = new Double3(ks);
        return this;
    }

    public Material setKd(double kd) {
        this.KD = new Double3(kd);
        return this;
    }
    public Material setShininess(int nShininess) {
        this.nShininess = nShininess;
        return this;
    }

}
