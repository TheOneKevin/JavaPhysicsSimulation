import greenfoot.Color;
import greenfoot.Greenfoot;
import oop.simulation.GameObject;
import oop.simulation.Scene;
import oop.simulation.components.*;
import oop.simulation.math.Circle;
import oop.simulation.math.Polygon;
import oop.simulation.math.Vec2;
import oop.simulation.objects.Camera2d;
import oop.simulation.physics2d.MprCollision;
import oop.simulation.physics2d.Rigidbody2d;

/**
 * Example main world
 */
public class MyWorld extends Scene
{
    private UiOverlayComponent ui;

    public MyWorld()
    {
        super(600, 400);
        Greenfoot.setSpeed(100); // Yeah baby!!

        // Create a camera now
        Camera2d cam = new Camera2d("camera1");
        cam.addComponent(new Transform(10, 10, 1, 1));
        this.addGameObject(cam);
        this.setActiveCamera("camera1");

        // Do the UI magic
        ui = new UiOverlayComponent(this.getWidth(), this.getHeight());
        cam.addComponent(ui);

        // Create the polygons
        Polygon t1 = new Polygon(
            new Vec2(0, 0),
            new Vec2(100, 0),
            new Vec2(100, 100),
            new Vec2(0, 100)
        );

        Polygon t2 = new Polygon(
            new Vec2(10, 30), new Vec2(125,140), new Vec2(0,140)
        );
        Polygon t3 = new Polygon(
            new Vec2(0, 0), new Vec2(600,0), new Vec2(600,100), new Vec2(0, 100)
        );

        Polygon t4 = new Polygon(
                new Vec2(0,200), new Vec2(0,900), new Vec2(1, 900), new Vec2(1,200)
        );

        Polygon t5 = new Polygon(
                new Vec2(600,200), new Vec2(600,900), new Vec2(599, 900), new Vec2(599,200)
        );


        // Create gameobject 1
        GameObject g1 = new GameObject("g1");
        g1.addComponent(new PolygonRenderer(t1));
        g1.addComponent(new Rigidbody2d(g1, new PolygonCollider(t1), 1));
        g1.addComponent(new Transform(100, 100, 1, 1));

        // Create gameobject 2
        GameObject g2 = new GameObject("g2");
        g2.addComponent(new PolygonRenderer(t2));
        g2.addComponent(new Rigidbody2d(g2, new PolygonCollider(t2), 1));
        g2.addComponent(new Transform(100, 300, 1, 1));

        // Create gameobject 3
        GameObject g3 = new GameObject("g3");
        g3.addComponent(new PolygonRenderer(t3));
        g3.addComponent(new Rigidbody2d(g3, new PolygonCollider(t3), Double.POSITIVE_INFINITY));
        g3.addComponent(new Transform(290, 5, 1, 1));

        //Creates the left boarder
        GameObject g4 = new GameObject("g4");
        g4.addComponent(new PolygonRenderer(t4));
        g4.addComponent(new Rigidbody2d(g4, new PolygonCollider(t4), Double.POSITIVE_INFINITY));
        g4.addComponent(new Transform(-10, 50, 1, 1));

        //Creates the right boarder
        GameObject g5 = new GameObject("g5");
        g5.addComponent(new PolygonRenderer(t5));
        g5.addComponent(new Rigidbody2d(g5, new PolygonCollider(t5), Double.POSITIVE_INFINITY));
        g5.addComponent(new Transform(588, 50, 1, 1));

        // Create gameobject 2
        Circle c1 = new Circle(20);
        GameObject g6 = new GameObject("g6");
        g6.addComponent(new CircleRenderer(c1));
        g6.addComponent(new Rigidbody2d(g6, new CircleCollider(c1), 1));
        g6.addComponent(new Transform(100, 300, 1, 1));

        // Make gameobject 1 moveable
        g1.addComponent(new BehaviourComponent(g -> {
            // Get rigidbody
            var rb = g.getComponent(Rigidbody2d.class);

            // Strength
            double k = 300;

            // Just testing, apply forces of 3N (1px = 1cm, 1s = 1s)
            if(Greenfoot.isKeyDown("w")) rb.applyForce(new Vec2(0,  k));
            if(Greenfoot.isKeyDown("s")) rb.applyForce(new Vec2(0, -k));
            if(Greenfoot.isKeyDown("a")) rb.applyForce(new Vec2(-k, 0));
            if(Greenfoot.isKeyDown("d")) rb.applyForce(new Vec2( k, 0));

            // Rotate the damn thing
            // rb.AngularVelocity.set(0d); // Biggest facepalm ever
            if(Greenfoot.isKeyDown("left"))  rb.applyForce(new Vec2(-k, 0), new Vec2(0, 100));
            if(Greenfoot.isKeyDown("right")) rb.applyForce(new Vec2( k, 0), new Vec2(0, 100));
        }));

        // Add them to the world!
        this.addGameObject(g1);
        this.addGameObject(g2);
        this.addGameObject(g3);
        this.addGameObject(g4);
        this.addGameObject(g5);
        this.addGameObject(g6);
    }

    @Override
    public void act()
    {
        // Draw FPS
        ui.writeString("FPS: " + Math.round(1/(DeltaT.get())), 10, 30, Color.RED, 15, false, false);

        //cuts of part of fps counter, but also deletes other things. Test it out if you'd like.
        //ui.clearPartially(0,0, 1000, 25);

        super.act();
    }
}