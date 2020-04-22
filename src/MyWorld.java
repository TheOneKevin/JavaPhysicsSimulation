import greenfoot.Color;
import greenfoot.Greenfoot;
import oop.simulation.GameObject;
import oop.simulation.Scene;
import oop.simulation.components.*;
import oop.simulation.math.Polygon;
import oop.simulation.math.Vec2;
import oop.simulation.objects.Camera2d;
import oop.simulation.physics2d.collision.Manifold;
import oop.simulation.physics2d.collision.MprCollision;
import oop.simulation.ui.PicoStyleCyber;
import oop.simulation.ui.PicoUI;

/**
 * Example main world
 */
public class MyWorld extends Scene
{
    public MyWorld()
    {
        super(600, 400);

        // Create a camera now
        Camera2d cam = new Camera2d("camera1");
        cam.addComponent(new Transform(10, 10, 1, 1));
        this.addGameObject(cam);
        this.setActiveCamera("camera1");

        // Do the UI magic
        UiOverlayComponent ui = new UiOverlayComponent(this.getWidth(), this.getHeight());
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

        // Create gameobject 1
        GameObject g1 = new GameObject("g1");
        g1.addComponent(new PolygonRenderer(t1));
        g1.addComponent(new Rigidbody2d(g1, new PolygonCollider(t1), 1));
        g1.addComponent(new Transform(100, 100, 1, 1));

        // Create gameobject 2
        GameObject g2 = new GameObject("g2");
        g2.addComponent(new PolygonRenderer(t2));
        g2.addComponent(new Rigidbody2d(g2, new PolygonCollider(t2), 1));
        g2.addComponent(new Transform(100, 200, 1, 1));

        // Make gameobject 1 moveable
        g1.addComponent(new BehaviourComponent(g -> {
            // Get rigidbody
            var rb = g.getComponent(Rigidbody2d.class);
            rb.clearForces();

            // Just testing, apply forces of 3N (1px = 1cm, 1s = 1s)
            if(Greenfoot.isKeyDown("w")) rb.applyForce(new Vec2(0,  300));
            if(Greenfoot.isKeyDown("s")) rb.applyForce(new Vec2(0, -300));
            if(Greenfoot.isKeyDown("a")) rb.applyForce(new Vec2(-300, 0));
            if(Greenfoot.isKeyDown("d")) rb.applyForce(new Vec2( 300, 0));

            // Rotate the damn thing
            rb.AngularVelocity.set(0d);
            if(Greenfoot.isKeyDown("left"))  rb.AngularVelocity.set( 1d);
            if(Greenfoot.isKeyDown("right")) rb.AngularVelocity.set(-1d);
        }));

        // Add them to the world!
        this.addGameObject(g1);
        this.addGameObject(g2);
    }
}