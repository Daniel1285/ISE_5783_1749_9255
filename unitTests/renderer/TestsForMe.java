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
        Scene scene = new Scene("Test scene").setBackground(new Color(51,153,255));

        Camera camera = new Camera(new Point(0, -2600, 400), new Vector(0, 1, 0), new Vector(0,0 , 1)) //
                .setVPSize(200, 200).setVPDistance(500);
        //scene.setAmbientLight(new AmbientLight(new Color(WHITE), new Double3(0.15)));

        scene.geometries.add(new Polygon(new Point(500,-300,0),new Point(500,300,0),new Point(-500,300,0),new Point(-500,-300,0)).setEmission(new Color(green)).
                        setMaterial(new Material().setKd(0.5).setKs(0.8).setShininess(20).setKR(0.1)),
                new Polygon(new Point(300,300,0),new Point(300,-100,0),new Point(-300,-100,0),new Point(-300,300,0)),
                new Polygon(new Point(300,300,0),new Point(300,-100,0),new Point(300,-100,300),new Point(300,300,300)).setEmission(new Color(GRAY)).setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(30)),
               // new Polygon(new Point(300,-100,0),new Point(-300,-100,0),new Point(-300,-100,300),new Point(300,-100,300)).setEmission(new Color(GRAY)).setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(31)),
                new Polygon(new Point(-75,-100,0),new Point(-75,-100,200),new Point(-150,-150,200),new Point(-150,-150,0)).setEmission(new Color(149,71,71)) //
                        .setMaterial(new Material().setKd(0.95).setKs(0).setKR(0).setKT(0).setShininess(20)),
                new Polygon(new Point(-75,-100,200),new Point(-75,-100,300),new Point(75,-100,300),new Point(75,-100,200)).setEmission(new Color(GRAY)).setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(30)),
                new Polygon(new Point(-300,-100,0),new Point(-75,-100,0),new Point(-75,-100,300),new Point(-300,-100,300)).setEmission(new Color(GRAY)).setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(30)),
                new Polygon(new Point(300,-100,0),new Point(75,-100,0),new Point(75,-100,300),new Point(300,-100,300)).setEmission(new Color(GRAY)).setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(30)),
                new Polygon(new Point(-300,-100,0),new Point(-300,300,0),new Point(-300,300,300),new Point(-300,-100,300)).setEmission(new Color(GRAY)).setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(30)),
                new Polygon(new Point(-300,300,0),new Point(300,300,0),new Point(300,300,300),new Point(-300,300,300)).setEmission(new Color(GRAY)).setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(30)),
                new Triangle(new Point(-300,-100,300),new Point(-300,300,300),new Point(0,100,400)).setEmission(new Color(RED)) //
                        .setMaterial(new Material().setKd(0.95).setKs(0).setKR(0).setKT(0).setShininess(20)),
                new Triangle(new Point(-300,300,300),new Point(300,300,300),new Point(0,100,400)).setEmission(new Color(RED)) //
                        .setMaterial(new Material().setKd(0.95).setKs(0).setKR(0).setKT(0).setShininess(20)),
                new Triangle(new Point(-300,-100,300),new Point(300,-100,300),new Point(0,100,400)).setEmission(new Color(RED)) //
                        .setMaterial(new Material().setKd(0.95).setKs(0).setKR(0).setKT(0).setShininess(20)),
                new Triangle(new Point(300,-100,300),new Point(300,300,300),new Point(0,100,400)).setEmission(new Color(RED)) //
                        .setMaterial(new Material().setKd(0.95).setKs(0).setKR(0).setKT(0).setShininess(20)),
                new Polygon(new Point(240,150,320),new Point(240,50,320),new Point(240,50,400),new Point(240,150,400)).setEmission(new Color(GRAY)),
                new Polygon(new Point(150,50,350),new Point(150,50,400),new Point(150,150,400),new Point(150,150,350)).setEmission(new Color(GRAY)),
                new Polygon(new Point(150,50,400),new Point(240,50,400),new Point(240,50,320),new Point(150,50,350)).setEmission(new Color(GRAY)),
                new Polygon(new Point(240,150,320),new Point(150,150,350),new Point(150,150,400),new Point(240,150,400)).setEmission(new Color(GRAY)),
                new Plane(new Point(0,400,0),new Point(0,400,4),new Point(3,400,4)).setEmission(new Color(10,100,255)) //background
                        .setMaterial(new Material().setKd(0.5).setKs(0.8).setShininess(20).setKT(1)),//.setEmission(new Color(0,204,204)),
                //new Sphere(20,new Point(420,0,500)).setEmission(new Color(yellow)).setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(300)),
                new Polygon(new Point(-500,300,10),new Point(-500,300,30),new Point(-500,-300,30),new Point(-500,-300,10)).setEmission(new Color(102,0,153)) //
                        .setMaterial(new Material().setKd(0.95).setKs(0).setKR(0).setKT(0).setShininess(20)),
                new Polygon(new Point(-500,300,50),new Point(-500,300,70),new Point(-500,-300,70),new Point(-500,-300,50)).setEmission(new Color(102,0,153)) //
                        .setMaterial(new Material().setKd(0.95).setKs(0).setKR(0).setKT(0).setShininess(20)),
                new Polygon(new Point(-500,300,0),new Point(-500,270,0),new Point(-500,270,80),new Point(-500,300,80)).setEmission(new Color(149,71,71)) //
                        .setMaterial(new Material().setKd(0.95).setKs(0).setKR(0).setKT(0).setShininess(20)),
                new Polygon(new Point(-500,15,0),new Point(-500,-15,0),new Point(-500,-15,80),new Point(-500,15,80)).setEmission(new Color(149,71,71)) //
                        .setMaterial(new Material().setKd(0.95).setKs(0).setKR(0).setKT(0).setShininess(20)),
                new Polygon(new Point(-500,-300,0),new Point(-500,-270,0),new Point(-500,-270,80),new Point(-500,-300,80)).setEmission(new Color(149,71,71)) //
                        .setMaterial(new Material().setKd(0.95).setKs(0).setKR(0).setKT(0).setShininess(20)),
                new Cylinder(new Ray(new Point(-400,-200,0),new Vector(0,0,100)),10,100).setEmission(new Color(149,71,71)) //
                        .setMaterial(new Material().setKd(0.95).setKs(0).setKR(0).setKT(0).setShininess(20)),
                new Cylinder(new Ray(new Point(400,-200,0),new Vector(0,0,100)),10,100).setEmission(new Color(149,71,71)) //
                        .setMaterial(new Material().setKd(0.95).setKs(0).setKR(0).setKT(0).setShininess(20)),
                //new Polygon(new Point(-500,300,0),new Point(500,300,0),new Point(500,300,80),new Point(-500,300,80)).setEmission(new Color(102,0,153)) //
                        //.setMaterial(new Material().setKd(0.95).setKs(0).setKR(0).setKT(0).setShininess(20)),
                new Polygon(new Point(500,300,10),new Point(500,300,30),new Point(-500,300,30),new Point(-500,300,10)).setEmission(new Color(102,0,153)) //
                        .setMaterial(new Material().setKd(0.95).setKs(0).setKR(0).setKT(0).setShininess(20)),
                new Polygon(new Point(500,300,50),new Point(500,300,70),new Point(-500,300,70),new Point(-500,300,50)).setEmission(new Color(102,0,153)) //
                        .setMaterial(new Material().setKd(0.95).setKs(0).setKR(0).setKT(0).setShininess(20)),
                new Polygon(new Point(500,300,10),new Point(500,-300,10),new Point(500,-300,30),new Point(500,300,30)).setEmission(new Color(102,0,153)) //
                        .setMaterial(new Material().setKd(0.95).setKs(0).setKR(0).setKT(0).setShininess(20)),
                new Polygon(new Point(500,300,50),new Point(500,300,70),new Point(500,-300,70),new Point(500,-300,50)).setEmission(new Color(102,0,153)) //
                        .setMaterial(new Material().setKd(0.95).setKs(0).setKR(0).setKT(0).setShininess(20)),
                new Polygon(new Point(500,300,0),new Point(500,270,0),new Point(500,270,80),new Point(500,300,80)).setEmission(new Color(149,71,71)) //
                        .setMaterial(new Material().setKd(0.95).setKs(0).setKR(0).setKT(0).setShininess(20)),
                new Polygon(new Point(500,15,0),new Point(500,-15,0),new Point(500,-15,80),new Point(500,15,80)).setEmission(new Color(149,71,71)) //
                        .setMaterial(new Material().setKd(0.95).setKs(0).setKR(0).setKT(0).setShininess(20)),
                new Polygon(new Point(500,-300,0),new Point(500,-270,0),new Point(500,-270,80),new Point(500,-300,80)).setEmission(new Color(149,71,71)) //
                        .setMaterial(new Material().setKd(0.95).setKs(0).setKR(0).setKT(0).setShininess(20)),
                new Sphere(23,new Point(400,-200,120)).setEmission(new Color(YELLOW)).setMaterial(new Material().setKd(0.5).setKs(0.4).setKT(1).setKR(0).setShininess(20)),
                new Sphere(23,new Point(-400,-200,120)).setEmission(new Color(YELLOW)).setMaterial(new Material().setKd(0.5).setKs(0.4).setKT(1).setKR(0).setShininess(20)),
                new Sphere(30,new Point(-30,0,30)).setEmission(new Color(red)),
                new Polygon(new Point(-500,-300,0),new Point(500,-300,0),new Point(500,-1100,0),new Point(-500,-1100,0)).setEmission(new Color(gray)),
                new Polygon(new Point(500,300,0),new Point(750,300,0),new Point(750,-1100,0),new Point(500,-1100,0)).setEmission(new Color(gray)),
                new Sphere(50,new Point(-300,200,500)).setEmission(new Color(WHITE)),
                new Sphere(40,new Point(-250,200,490)).setEmission(new Color(WHITE)),
                new Sphere(40,new Point(-350,200,490)).setEmission(new Color(WHITE)),
                new Sphere(40,new Point(-500,200,550)).setEmission(new Color(WHITE)),
                new Sphere(50,new Point(-550,200,560)).setEmission(new Color(WHITE)),
                new Sphere(40,new Point(-600,200,550)).setEmission(new Color(WHITE)),
                new Sphere(40,new Point(-400,200,690)).setEmission(new Color(WHITE)),
                new Sphere(40,new Point(-500,200,690)).setEmission(new Color(WHITE)),
                new Sphere(50,new Point(-450,200,700)).setEmission(new Color(WHITE)),
                new Polygon(new Point(550,-250,0.001),new Point(575,-250,0.001),new Point(575,-450,0.001),new Point(550,-450,0.001)).setEmission(new Color(WHITE)),
                new Polygon(new Point(550,0,0.001),new Point(575,0,0.001),new Point(575,-200,0.001),new Point(550,-200,0.001)).setEmission(new Color(WHITE)),
                new Polygon(new Point(200,-550,0.001),new Point(200,-600,0.001),new Point(400,-600,0.001),new Point(400,-550,0.001)).setEmission(new Color(WHITE)),
                new Polygon(new Point(-50,-550,0.001),new Point(-50,-600,0.001),new Point(150,-600,0.001),new Point(150,-550,0.001)).setEmission(new Color(WHITE)),
                new Polygon(new Point(-300,-600,0.001),new Point(-300,-550,0.001),new Point(-100,-550,0.001),new Point(-100,-600,0.001)).setEmission(new Color(WHITE)),
                new Cylinder(new Ray(new Point(-127.5,-135,100),new Vector(2,-3,0)),5,20).setEmission(new Color(green)),
                new Sphere(4,new Point(-115.5,-152,100)).setEmission(new Color(BLUE)));


        for(double x = 200, z = 430; x < 230 && z < 670 ; x += 5,z += 30 ){
            scene.geometries.add(new Sphere(10,new Point(x,100,z)).setEmission(new Color(135,105,105)));
        }

        scene.lights.add(new SpotLight(new Color(400, 240, 0),new Point(0,0,800),new Vector(0,1,0)).setKl(1E-5).setKq(1.5E-7));
        scene.lights.add(new DirectionalLight(new Color(0,76,153),new Vector(1,1,-1)));
        scene.lights.add(new SpotLight(new Color(400, 240, 0),new Point(0,100,370),new Vector(0,0,-1)));
        ImageWriter imageWriter = new ImageWriter("home1", 1200, 1200);
        camera.setImageWriter(imageWriter)
                .setRayTracer(new RayTracerBasic(scene))//.setAdaptive(true)
                .setMultiThreading(4)
                .setSuperSampling(0).setAdaptive(true).setDepthOfField(20,1000)
                //.moveCamera(new Vector(1200,0,0))
                //.rotateCamera(new Vector(0,0,1),25)
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
        camera.setImageWriter(imageWriter).setMultiThreading(4).setAdaptive(false).setSuperSampling(9)
                .setRayTracer(new RayTracerBasic(scene))
                .renderImage()
                .writeToImage();
    }
}
