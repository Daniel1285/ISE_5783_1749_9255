package renderer;

import primitives.Point;
import primitives.Vector;
import primitives.Ray;
import static primitives.Util.*;
import static primitives.Double3.*;

public class Camera {
    private Point p0;
    private Vector vTo;
    private Vector vUp;
    private Vector vRight;
    private double distance;
    private double width;

    public double getDistance() {
        return distance;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    private double height;

    public Camera(Point p0, Vector vTo, Vector vUp){
        if(!isZero(vTo.dotProduct(vUp))){
            throw new IllegalArgumentException("these vectors are not orthogonal");
        }
        this.p0 = p0;
        this.vTo = vTo.normalize();
        this.vUp = vUp.normalize();
        this.vRight = vTo.crossProduct(vUp);
    }
    public Camera setVPSize(double width, double height){
        this.height = height;
        this.width = width;
        return this;
    }
    public Camera setVPDistance(double distance){
        this.distance = distance;
        return this;
    }
    public Ray constructRay(int nX, int nY, int j, int i){
        Point pc = this.p0.add(this.vTo.scale(this.distance));
        double ry = this.height / nY;
        double rx = this.width / nX;
        double yi = (i - (nY - 1) / 2d) * ry;
        double xj = (j - (nX - 1) / 2d) * rx;
        Point pIj = pc;
        if (xj != 0)
            pIj = pIj.add(vRight.scale(xj));
        if (yi != 0)
            pIj = pIj.add(vUp.scale(-yi));
        return new Ray(this.p0,pIj.subtract(p0));
    }


}
