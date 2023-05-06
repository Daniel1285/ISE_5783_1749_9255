package renderer;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import geometries.*;
import primitives.*;
import java.util.List;

/**
 * This class is used to test the functionality of the ImageWriter class.
 */
public class ImageWriterTest {

    /**
     * Test method for writing pixels to an image.
     */
    @Test
    void testWriteToImage(){
        int nX = 800; // Width of the image
        int nY = 500; // Height of the image

        Color yellow = new Color(255, 255, 0);
        Color red = new Color(255, 0, 0);

        ImageWriter image = new ImageWriter("testImage1", nX, nY); // Create a new ImageWriter object

        for(int i = 0; i < nY; i++){
            for(int j = 0; j < nX; j++){
                if(i % 50 == 0 || j % 50 == 0) {
                    image.writePixel(j, i, red); // Write red pixel
                }
                else {
                    image.writePixel(j, i, yellow); // Write yellow pixel
                }
            }
        }
        image.writeToImage(); // Save the image to a file
    }
}

