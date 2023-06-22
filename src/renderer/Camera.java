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

    // number of rays to be used per pixel width or height.
    private int superSampling = 0;

    //The radius of the target area.
    private double aperture = 0;

    //Distance between the view plane to focal plane.
    private double focalLength = 0;
    private int threadsCount =1; //number of rays to the superSampling
    private boolean adaptive = false;
    private final int maxLevelAdaptiveSS = 3;
    public Camera setMultiThreading(int threadsCount) {
        this.threadsCount = threadsCount;
        return this;
    }
    public Camera setAdaptive(boolean adaptive) {
        this.adaptive = adaptive;
        return this;
    }

    public Camera setSuperSampling(int density) {
        this.superSampling = density;
        return this;
    }
    public Camera setDepthOfField(double aperture, double distance) {
        this.aperture = aperture;
        this.focalLength = distance;
        return this;
    }
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
    /**

     Sets the image writer for the camera.
     @param imageWriter The image writer to set.
     @return The camera instance.
     */
    public Camera setImageWriter(ImageWriter imageWriter) {
        this.imageWriter = imageWriter;
        return this;
    }
    /**

     Sets the ray tracer for the camera.
     @param rayTracer The ray tracer to set.
     @return The camera instance.
     */
    public Camera setRayTracer(RayTracerBase rayTracer) {
        this.rayTracer = rayTracer;
        return this;
    }

    /**
     * Renders the image by casting rays or beam rays based on the superSampling setting.
     *
     * @return The camera itself - for chaining.
     * @throws UnsupportedOperationException If required resources (ImageWriter or RayTracer) are missing.
     */
    public Camera renderImage() {
        try {
            int Ny = imageWriter.getNy(); // Get the number of rows in the image.
            int Nx = imageWriter.getNx(); // Get the number of columns in the image.

            // Check if the ImageWriter is null. If so, throw a MissingResourceException.
            if (imageWriter == null) {
                throw new MissingResourceException("missing resource", ImageWriter.class.getName(), "");
            }

            // Check if the RayTracer is null. If so, throw a MissingResourceException.
            if (rayTracer == null) {
                throw new MissingResourceException("missing resource", RayTracerBase.class.getName(), "");
            }
            Pixel.initialize(imageWriter.getNy(), imageWriter.getNx(), 1);
            // Check if superSampling is disabled (superSampling = 0). If so, cast a single ray for each pixel.
            if (superSampling == 0) {
                while (threadsCount-- > 0) {
                    new Thread(() -> {
                        for (Pixel pixel = new Pixel(); pixel.nextPixel(); Pixel.pixelDone())
                            imageWriter.writePixel(pixel.row, pixel.col, castRay(Nx, Ny, pixel.col, pixel.row));
                    }).start();
                }
                Pixel.waitToFinish();
            }
            else {
                if (!adaptive) {
                    List<Point> points = Point.pointsInTheTargetArea(p0, vUp, vRight, superSampling, aperture);
                    while (threadsCount-- > 0) {
                        new Thread(() -> {
                            for (Pixel pixel = new Pixel(); pixel.nextPixel(); Pixel.pixelDone())
                                 imageWriter.writePixel(pixel.row, pixel.col, castBeamRay(Nx, Ny, pixel.col, pixel.row, points));
                        }).start();
                    }
                    Pixel.waitToFinish();
                }
                else{
                    while (threadsCount-- > 0) {
                        new Thread(() -> {
                            for (Pixel pixel = new Pixel(); pixel.nextPixel(); Pixel.pixelDone())
                                imageWriter.writePixel(pixel.row, pixel.col, AdaptiveSuperSampling(pixel.col, pixel.row));
                        }).start();
                    }
                    Pixel.waitToFinish();
                }
            }
        } catch (MissingResourceException ex) {
            throw new UnsupportedOperationException("Not implemented " + ex.getClass());
        }

        return this; // Return the camera itself to support method chaining.
    }

    /**

     Prints a grid of pixels to the image writer.
     @param interval The interval between grid lines.
     @param color The color of the grid lines.
     @throws MissingResourceException if the image writer is null.
     */
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
    /**

     Writes the image to a file using the image writer.
     @throws MissingResourceException if the image writer is null.
     */
    public void writeToImage() {
        if(imageWriter == null){
            throw new MissingResourceException("missing resource", ImageWriter.class.getName(), "");
        }
        imageWriter.writeToImage();
    }

    /**
     * Creates the foundation and summons the calculation
     * Color the ray using the traceRay method of the rayTracer field, finally the method will return the color
     * @param nX Represents the amount of columns (row width)
     * @param nY Represents amount of rows (column height)
     * @param column Represents the columns according to the resulting index
     * @param row Represents the rows according to the resulting index
     */
    private Color castRay(int nX, int nY, int column, int row) {
        Ray ray = constructRay(nX, nY, row, column);
        Color pixelColor = rayTracer.traceRay(ray);
        return pixelColor;
    }
    /**
     * Casts a beam ray from a specified position and traces it to generate a color for a specific pixel in the image.
     *
     * @param nX      The number of columns in the image.
     * @param nY      The number of rows in the image.
     * @param column  The column index of the pixel.
     * @param row     The row index of the pixel.
     * @param points  The list of points representing the target area.
     */
    private Color castBeamRay(int nX, int nY, int column, int row, List<Point> points) {
        // Construct the ray originating from the specified position (nX, nY) and passing through the current pixel (row, column).
        Ray ray = constructRay(nX, nY, row, column);

        // Calculate the point of intersection between the constructed ray and the focal plane using the specified focal length.
        Point point = ray.getPoint(focalLength);

        // Trace multiple rays from the target area towards the calculated intersection point and obtain the average color.
        Color color = rayTracer.traceMultipleRays(Ray.createBeamOfRaysFromTargetArea(points, point));
        return color;
    }

    /**
     * Rotates the camera around a specified axis by a given angle.
     *
     * @param axis  The axis of rotation.
     * @param theta The angle of rotation in radians.
     * @return The camera itself - for chaining.
     */
    public Camera rotateCamera(Vector axis, double theta) {
        if (theta == 0) return this; // If the angle of rotation is zero, no rotation is performed, so return the camera itself.

        // Rotate the vUp, vRight, and vTo vectors of the camera around the specified axis by the given angle.
        vUp = vUp.rotateVector(axis, theta);
        vRight = vRight.rotateVector(axis, theta);
        vTo = vTo.rotateVector(axis, theta);

        return this; // Return the camera itself to support method chaining.
    }


    /**
     * Moves the camera position by a specified vector.
     *
     * @param move The vector representing the movement direction and distance.
     * @return The camera itself - for chaining.
     */
    public Camera moveCamera(Vector move) {
        // Create a new Point object representing the current camera position.
        Point myPoint = new Point(p0.getXYZ());

        // Add the movement vector to the current camera position to obtain the new camera position.
        myPoint = myPoint.add(move);

        // Update the camera's p0 (position) to the new camera position.
        p0 = myPoint;

        return this; // Return the camera itself to support method chaining.
    }
    /**
     * Performs adaptive super sampling at pixel (i, j) to improve image quality.
     *
     * @param i The column index of the pixel.
     * @param j The row index of the pixel.
     * @return The color obtained after adaptive super sampling.
     */
    private Color AdaptiveSuperSampling(int i, int j) {
        // Construct the center ray for the pixel at (i, j).
        Ray center = constructRay(imageWriter.getNx(), imageWriter.getNy(), j, i);

        // Trace the center ray to obtain the center color.
        Color centerColor = rayTracer.traceRay(center);

        // Perform adaptive super sampling using the center color.
        return calcAdaptiveSuperSampling(imageWriter.getNx(), imageWriter.getNy(), j, i, maxLevelAdaptiveSS, centerColor);
    }

    /**
     * Performs recursive adaptive super sampling to improve image quality.
     *
     * @param nX          The total number of columns in the image.
     * @param nY          The total number of rows in the image.
     * @param j           The column index of the current pixel.
     * @param i           The row index of the current pixel.
     * @param maxLevel    The maximum recursion level for adaptive super sampling.
     * @param centerColor The color of the center pixel.
     * @return The color obtained after adaptive super sampling.
     */
    private Color calcAdaptiveSuperSampling(int nX, int nY, int j, int i, int maxLevel, Color centerColor) {
        // Base case: if maxLevel is less than or equal to 0, return the center color.
        if (maxLevel <= 0) {
            return centerColor;
        }

        Color color = centerColor;
        List<Ray> beam = new LinkedList<>();

        // Construct the beam rays for the current pixel.
        beam.add(constructRay(2 * nX, 2 * nY, 2 * j, 2 * i));
        beam.add(constructRay(2 * nX, 2 * nY, 2 * j, 2 * i + 1));
        beam.add(constructRay(2 * nX, 2 * nY, 2 * j + 1, 2 * i));
        beam.add(constructRay(2 * nX, 2 * nY, 2 * j + 1, 2 * i + 1));

        // Iterate over the beam rays and perform adaptive super sampling recursively.
        for (int ray = 0; ray < 4; ray++) {
            Color currentColor = rayTracer.traceRay(beam.get(ray));

            // Check if the current color is different from the center color.
            if (!currentColor.equals(centerColor)) {
                currentColor = calcAdaptiveSuperSampling(2 * nX, 2 * nY, 2 * j + ray / 2, 2 * i + ray % 2, (maxLevel - 1), currentColor);
            }

            // Accumulate the color obtained from the recursive call.
            color = color.add(currentColor);
        }

        // Reduce the accumulated color by dividing it by 5.
        return color.reduce(5);
    }

}

