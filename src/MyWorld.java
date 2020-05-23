import greenfoot.Color;
import greenfoot.Greenfoot;
import oop.simulation.GameObject;
import oop.simulation.Scene;
import oop.simulation.components.*;
import oop.simulation.math.Circle;
import oop.simulation.math.Polygon;
import oop.simulation.math.Vec2;
import oop.simulation.objects.Camera2d;
import oop.simulation.physics2d.Rigidbody2d;
import oop.simulation.ui.PicoUI;

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
        // New UI
        PicoUI ctx = new PicoUI(400, 200, PicoStyleCyber.getInstance());
        this.addObject(ctx, 300, 200);
        setBackground(PicoStyleCyber.getInstance());
        // Text box
        var txt1 = ctx.new TextField("textbox1", 50);
        txt1.Margins.set(new PicoUI.Margins(5, 3, 5, 3));
        txt1.Metadata.set(0);
        txt1.Text.set("4");
        String forceText = txt1.Text.get();
        double force = Double.parseDouble(forceText);
        // Update layout
        ctx.update();

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
            new Vec2(10, 30), new Vec2(125, 140), new Vec2(0, 140)
        );
        Polygon t3 = new Polygon(
            new Vec2(0, 0), new Vec2(600, 0), new Vec2(600, 100), new Vec2(0, 100)
        );

        Polygon t4 = new Polygon(
            new Vec2(0, 200), new Vec2(0, 900), new Vec2(1, 900), new Vec2(1, 200)
        );

        Polygon t5 = new Polygon(
            new Vec2(600, 200), new Vec2(600, 900), new Vec2(599, 900), new Vec2(599, 200)
        );

        Polygon t7 = new Polygon(
            new Vec2(-40, 900), new Vec2(-40, 850), new Vec2(600, 850), new Vec2(600, 900)
        );


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

        GameObject g7 = new GameObject("g7");
        g7.addComponent(new PolygonRenderer(t7));
        g7.addComponent(new Rigidbody2d(g7, new PolygonCollider(t7), Double.POSITIVE_INFINITY));
        g7.addComponent(new Transform(290, 415, 1, 1));


        // Create gameobject 2
        Circle c1 = new Circle(20);
        Circle c2 = new Circle(20);
        Circle c3 = new Circle(20);
        Circle c4 = new Circle(20);
        Circle c5 = new Circle(20);
        Circle c6 = new Circle(20);
        Circle c7 = new Circle(20);
        Circle c8 = new Circle(20);
        Circle c9 = new Circle(20);
        Circle c10 = new Circle(20);

        GameObject k1 = new GameObject("k1");
        k1.addComponent(new CircleRenderer(c1));
        k1.addComponent(new Rigidbody2d(k1, new CircleCollider(c1), 0.5));
        k1.addComponent(new Transform(405, 300, 1, 1));

        GameObject k2 = new GameObject("k2");
        k2.addComponent(new CircleRenderer(c2));
        k2.addComponent(new Rigidbody2d(k2, new CircleCollider(c2), 0.5));
        k2.addComponent(new Transform(400, 100, 1, 1));

        GameObject k3 = new GameObject("k3");
        k3.addComponent(new CircleRenderer(c3));
        k3.addComponent(new Rigidbody2d(k3, new CircleCollider(c3), 0.5));
        k3.addComponent(new Transform(405, 100, 1, 1));

        GameObject k4 = new GameObject("k4");
        k4.addComponent(new CircleRenderer(c4));
        k4.addComponent(new Rigidbody2d(k4, new CircleCollider(c4), 0.5));
        k4.addComponent(new Transform(4010, 100, 1, 1));

        GameObject k5 = new GameObject("k5");
        k5.addComponent(new CircleRenderer(c5));
        k5.addComponent(new Rigidbody2d(k5, new CircleCollider(c5), 0.5));
        k5.addComponent(new Transform(400, 105, 1, 1));

        GameObject k6 = new GameObject("k6");
        k6.addComponent(new CircleRenderer(c6));
        k6.addComponent(new Rigidbody2d(k6, new CircleCollider(c6), 0.5));
        k6.addComponent(new Transform(405, 105, 1, 1));

        GameObject k7 = new GameObject("k7");
        k7.addComponent(new CircleRenderer(c7));
        k7.addComponent(new Rigidbody2d(k7, new CircleCollider(c7), 0.5));
        k7.addComponent(new Transform(410, 105, 1, 1));

        GameObject k8 = new GameObject("k8");
        k8.addComponent(new CircleRenderer(c8));
        k8.addComponent(new Rigidbody2d(k8, new CircleCollider(c8), 0.5));
        k8.addComponent(new Transform(415, 110, 1, 1));

        GameObject k9 = new GameObject("k9");
        k9.addComponent(new CircleRenderer(c9));
        k9.addComponent(new Rigidbody2d(k9, new CircleCollider(c9), 0.5));
        k9.addComponent(new Transform(415, 105, 1, 1));

        GameObject k10 = new GameObject("k10");
        k10.addComponent(new CircleRenderer(c10));
        k10.addComponent(new Rigidbody2d(k10, new CircleCollider(c10), 0.5));
        k10.addComponent(new Transform(420, 100, 1, 1));

        // Make gameobject 2 moveable
        k1.addComponent(new BehaviourComponent(g -> {
            // Get rigidbody
            var rb = g.getComponent(Rigidbody2d.class);

            // Strength
            double k = 300;
            double l = force * 1000;

            // Just testing, apply forces of 3N (1px = 1cm, 1s = 1s)
            if (Greenfoot.isKeyDown("w")) rb.applyForce(new Vec2(0, k));
            if (Greenfoot.isKeyDown("s")) rb.applyForce(new Vec2(0, -k));
            if (Greenfoot.isKeyDown("a")) rb.applyForce(new Vec2(-k, 0));
            if (Greenfoot.isKeyDown("d")) rb.applyForce(new Vec2(k, 0));
            if (Greenfoot.isKeyDown("f")) rb.applyForce(new Vec2(0, -l));

            // Rotate the damn thing
            // rb.AngularVelocity.set(0d); // Biggest facepalm ever
            if (Greenfoot.isKeyDown("left")) rb.applyForce(new Vec2(-k, 0), new Vec2(0, 100));
            if (Greenfoot.isKeyDown("right")) rb.applyForce(new Vec2(k, 0), new Vec2(0, 100));
        }));

        // Add them to the world
        this.addGameObject(g3);
        this.addGameObject(g4);
        this.addGameObject(g5);
        this.addGameObject(k1);
        this.addGameObject(g7);
        this.addGameObject(k2);
        this.addGameObject(k3);
        this.addGameObject(k4);
        this.addGameObject(k5);
        this.addGameObject(k6);
        this.addGameObject(k7);
        this.addGameObject(k8);
        this.addGameObject(k9);
        this.addGameObject(k10);

    }

    private void setBackground(PicoUI.PicoStyle style)
    {
        getBackground().setColor(style.getBackgroundDefault());
        getBackground().fill();
    }

    @Override
    public void act()
    {
        // Draw FPS
        ui.writeString("FPS: " + Math.round(1 / (DeltaT.get())), 10, 30, Color.RED, 15, false, false);
        //cuts of part of fps counter, but also deletes other things. Test it out if you'd like.
        //ui.clearPartially(0,0, 1000, 25);
        super.act();
    }

}
