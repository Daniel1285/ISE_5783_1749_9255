package renderer;

import geometries.*;
import lighting.*;
import org.junit.jupiter.api.Test;
import primitives.*;
import scene.Scene;
import static java.awt.Color.*;
public class TestsForMe {
    @Test
    public void tests(){
        Scene scene = new Scene("Test scene");

        Camera camera = new Camera(new Point(0, -2600, 400), new Vector(0, 1, 0), new Vector(0,0 , 1)) //
                .setVPSize(190, 190).setVPDistance(500);
        scene.setAmbientLight(new AmbientLight(new Color(WHITE), new Double3(0.15)));

        scene.geometries.add(new Polygon(new Point(500,-300,0),new Point(500,300,0),new Point(-500,300,0),new Point(-500,-300,0)).setEmission(new Color(green)).
                        setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(30)),
                new Polygon(new Point(300,300,0),new Point(300,-100,0),new Point(-300,-100,0),new Point(-300,300,0)),
                new Polygon(new Point(300,300,0),new Point(300,-100,0),new Point(300,-100,300),new Point(300,300,300)).setEmission(new Color(GRAY)).setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(30)),
                new Polygon(new Point(300,-100,0),new Point(-300,-100,0),new Point(-300,-100,300),new Point(300,-100,300)).setEmission(new Color(GRAY)).setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(31)),
                new Polygon(new Point(-300,-100,0),new Point(-300,300,0),new Point(-300,300,300),new Point(-300,-100,300)).setEmission(new Color(GRAY)).setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(30)),
                new Polygon(new Point(-300,300,0),new Point(300,300,0),new Point(300,300,300),new Point(-300,300,300)).setEmission(new Color(GRAY)).setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(30)),
                new Triangle(new Point(-300,-100,300),new Point(-300,300,300),new Point(0,100,400)).setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(30)),
                new Triangle(new Point(-300,300,300),new Point(300,300,300),new Point(0,100,400)).setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(30)),
                new Triangle(new Point(-300,-100,300),new Point(300,-100,300),new Point(0,100,400)).setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(30)),
                new Triangle(new Point(300,-100,300),new Point(300,300,300),new Point(0,100,400)).setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(30)),
                new Polygon(new Point(240,150,320),new Point(240,50,320),new Point(240,50,400),new Point(240,150,400)).setEmission(new Color(GRAY)),
                new Polygon(new Point(150,50,350),new Point(150,50,400),new Point(150,150,400),new Point(150,150,350)).setEmission(new Color(GRAY)),
                new Polygon(new Point(150,50,400),new Point(240,50,400),new Point(240,50,320),new Point(150,50,350)).setEmission(new Color(GRAY)),
                new Polygon(new Point(240,150,320),new Point(150,150,350),new Point(150,150,400),new Point(240,150,400)).setEmission(new Color(GRAY)),
                new Plane(new Point(0,400,0),new Point(0,400,4),new Point(3,400,4)).setEmission(new Color(0,204,204)),
                new Sphere(20,new Point(420,0,500)).setEmission(new Color(yellow)).setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(300)));
        for(double x = 200, z = 430; x < 240 && z < 670 ; x += 5,z += 30 ){
            scene.geometries.add(new Sphere(10,new Point(x,100,z)).setEmission(new Color(black)));
        }
        scene.lights.add(new SpotLight(new Color(255,255,102),new Point(420,0,500),new Vector(-1,0,-1)));
        scene.lights.add(new DirectionalLight(new Color(0,76,153),new Vector(1,1,-1)));
        ImageWriter imageWriter = new ImageWriter("home3", 1200, 1200);
        camera.setImageWriter(imageWriter)
                .setRayTracer(new RayTracerBasic(scene))
                .moveCamera(new Vector(-800,0,0))
                .rotateCamera(new Vector(0,0,1),-20)
                .renderImage() //
                .writeToImage();
    }

   @Test
    public void zsdxfcsasd() {
        Scene scene = new Scene("DoF");
        Camera camera = new Camera(new Point(0, 0, 2500), new Vector(0, 0, -1), new Vector(0, 1, 0)).setVPSize(200, 200)
                .setVPDistance(850).setSuperSampling(9).setDepthOfField(20,1600);
        AmbientLight ambientLight = new AmbientLight(new Color(30, 30, 30), 0.1);
        scene.setAmbientLight(ambientLight);

        Geometry plane = new Plane(new Point(0, 0, 0), new Vector(0, 0, 1)).setEmission(new Color(0, 20, 20));
        plane.setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60).setKR(0.02));
        scene.geometries.add(plane);
        scene.geometries.add(new Sphere(70, new Point(100, 0 ,300)).setMaterial(new Material().setKd(0.6).setKs(0.4).setShininess(100).setKR(0.3)));
        scene.geometries.add(new Sphere(70, new Point(0, 0 ,900)).setMaterial(new Material().setKd(0.6).setKs(0.4).setShininess(100).setKR(0.3)));
        scene.geometries.add(new Sphere(70, new Point(-100, 0 ,1500)).setMaterial(new Material().setKd(0.6)
                .setKs(0.4).setShininess(100).setKR(0.3)));
        LightSource lightSource = new DirectionalLight(new Color(70, 172, 21), new Vector(-1, 0, 0));
        scene.lights.add(lightSource);
        ImageWriter imageWriter = new ImageWriter("DoF", 1200, 1200);
        camera.setImageWriter(imageWriter)
                .setRayTracer(new RayTracerBasic(scene))
                .renderImage()
                .writeToImage();
    }
}
