package renderer;

import primitives.Point;
import primitives.Vector;
import primitives.Ray;
import static primitives.Util.*;
import java.util.*;
import primitives.Color;


/**
 * Represents a camera used for rendering images.
 */
public class Camera {

    /* Private instance variables */
    private Point p0;
    private Vector vTo;
    private Vector vUp;
    private Vector vRight;
    private double distance;
    private double width;
    private double height;
    private ImageWriter imageWriter;
    private RayTracerBase rayTracer;


    /**
     * Constructs a new Camera object with the given position, direction, and up vector.
     * The direction and up vectors must be orthogonal.
     *
     * @param p0 the position of the camera
     * @param vTo the direction that the camera is pointing in
     * @param vUp the up vector of the camera
     * @throws IllegalArgumentException if vTo and vUp are not orthogonal
     */
    public Camera(Point p0, Vector vTo, Vector vUp) throws IllegalArgumentException {
        if (!isZero(vTo.dotProduct(vUp))) {
            throw new IllegalArgumentException("Direction and up vectors must be orthogonal");
        }
        this.p0 = p0;
        this.vTo = vTo.normalize();
        this.vUp = vUp.normalize();
        this.vRight = vTo.crossProduct(vUp);
    }

    /**
     * Sets the viewPlane size of the camera to the given width and height.
     *
     * @param width the width of the viewport
     * @param height the height of the viewport
     * @return this camera object
     */
    public Camera setVPSize(double width, double height) {
        this.width = width;
        this.height = height;
        return this;
    }

    /**
     * Sets the distance of the viewPlane from the camera.
     *
     * @param distance the distance of the viewport
     * @return this camera object
     */
    public Camera setVPDistance(double distance) {
        this.distance = distance;
        return this;
    }

    /**
     * Constructs a ray that passes through the given pixel coordinates on the viewPlane.
     *
     * @param nX the number of pixels in the X direction
     * @param nY the number of pixels in the Y direction
     * @param j the X coordinate of the pixel
     * @param i the Y coordinate of the pixel
     * @return a Ray object that passes through the given pixel coordinates
     */
    public Ray constructRay(int nX, int nY, int j, int i) {
        Point pc = this.p0.add(this.vTo.scale(this.distance));
        double ry = this.height / nY;
        double rx = this.width / nX;
        double yi = (i - (nY - 1) / 2d) * ry;
        double xj = (j - (nX - 1) / 2d) * rx;
        Point pIj = pc;
        if (xj != 0) {
            pIj = pIj.add(vRight.scale(xj));
        }
        if (yi != 0) {
            pIj = pIj.add(vUp.scale(-yi));
        }
        return new Ray(this.p0, pIj.subtract(p0));
    }

    /**
     * Returns the distance of the viewPlane from the camera.
     *
     * @return the distance of the viewPlane
     */
    public double getDistance() {
        return distance;
    }

    /**
     * Returns the width of the viewPlane.
     *
     * @return the width of the viewPlane
     */
    public double getWidth() {
        return width;
    }

    /**
     * Returns the height of the viewPlane.
     *
     * @return the height of the viewPlane
     */
    public double getHeight() {
        return height;
    }
    public Camera setImageWriter(ImageWriter imageWriter) {
        this.imageWriter = imageWriter;
        return this;
    }

    public Camera setRayTracer(RayTracerBase rayTracer) {
        this.rayTracer = rayTracer;
        return this;
    }

    public void  renderImage(){
        try{
            if (imageWriter == null) {
                throw new MissingResourceException("missing resource", ImageWriter.class.getName(), "");
            }
            if (rayTracer == null) {
                throw new MissingResourceException("missing resource", RayTracerBase.class.getName(), "");
            }
            for (int i = 0; i < imageWriter.getNy(); i++) {
                for (int j = 0; j < imageWriter.getNx()  ; j++) {
                    castRay(imageWriter.getNx(),imageWriter.getNy(), i, j);
                }
            }
        }
        catch (MissingResourceException ex){
            throw new UnsupportedOperationException("Not implemented " + ex.getClass());
        }
    }
    public void printGrid(int interval,Color color){
        if(imageWriter == null){
            throw new MissingResourceException("missing resource", ImageWriter.class.getName(), "");
        }
        for (int i = 0; i < imageWriter.getNy(); i++) {
            for (int j = 0; j < imageWriter.getNx(); j++) {
                if (i % interval == 0 || j % interval == 0) {
                    imageWriter.writePixel(j, i, color);
                }
            }
        }
    }
    public void writeToImage() {
        if(imageWriter == null){
            throw new MissingResourceException("missing resource", ImageWriter.class.getName(), "");
        }
        imageWriter.writeToImage();
    }
    private void castRay(int nX, int nY, int column, int row ) {
        Ray ray = constructRay(nX, nY, row, column);
        Color pixelColor = rayTracer.traceRay(ray);
        imageWriter.writePixel(row, column, pixelColor);
    }




}

