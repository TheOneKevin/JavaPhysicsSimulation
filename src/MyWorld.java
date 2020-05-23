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
import oop.simulation.ui.RIconsUI;

import java.util.Random;

/**
 * Example main world
 */
public class MyWorld extends Scene
{
    private PicoUI ctx;
    private int ctr = 0;
    private Random rand = new Random();
    boolean isHidden = true;
    private double spaceDebounceTimer = 0;

    public MyWorld()
    {
        super(600, 400);
        Greenfoot.setSpeed(100); // Yeah baby!!

        // Create a camera now
        Camera2d cam = new Camera2d("camera1");
        cam.addComponent(new Transform(0, 0, 1, 1));
        this.addGameObject(cam);
        this.setActiveCamera("camera1");

        // FPS Counter
        UiOverlayComponent ui = new UiOverlayComponent(this.getWidth(), this.getHeight());
        cam.addComponent(ui);

        // Add simple scene behaviour
        cam.addComponent(new BehaviourComponent(g -> {
            String msg = isHidden ? "Press [space] to view menu" : "Press [space] to hide menu";
            String txt = String.format("FPS: %d", Math.round(1 / (DeltaT.get())));
            ui.writeString(txt, 10, 30, Color.RED, 15, false, false);
            ui.writeString(msg, 100, 30, Color.RED, 15, false, false);

            if(Greenfoot.isKeyDown("space") && spaceDebounceTimer > 0.3)
            {
                if(isHidden)
                {
                    this.addObject(ctx, getWidth()/2, getHeight()/2);
                    ctx.update();
                }
                else
                {
                    ctx.remove();
                }
                isHidden = !isHidden;
                spaceDebounceTimer = 0;
            }
            // Avoid overflows - Kevin :D
            if(spaceDebounceTimer <= 0.3)
                spaceDebounceTimer += deltaTime;
        }));

        // Add walls
        addWalls();

        // Draw UI
        drawUI();
    }

    public void drawUI()
    {
        // New UI context
        this.ctx = new PicoUI(400, 200, PicoStyleCyber.getInstance());
        var lbl1 = ctx.new Label("lbl1", "Mass: ");
        var txt1 = ctx.new TextField("txt1", 40);
        var lbl2 = ctx.new Label("lbl2", " Restitution: ");
        var txt2 = ctx.new TextField("txt2", 40);
        var lbl3 = ctx.new Label("lbl3", "Friction (s): ");
        var txt3 = ctx.new TextField("txt3", 40);
        var lbl4 = ctx.new Label("lbl4", " Friction (k): ");
        var txt4 = ctx.new TextField("txt4", 40);

        txt1.PromptText.set("m"); txt1.Text.set("1");
        txt2.PromptText.set("e"); txt2.Text.set("2.8");
        txt3.PromptText.set("mu_s"); txt3.Text.set("0.4");
        txt4.PromptText.set("mu_k"); txt4.Text.set("0.3");

        var btnAdd = ctx.new Button("btnAdd", "Add Circle", RIconsUI.getIcon(RIconsUI.RIcons.RICON_FILE_NEW));
        btnAdd.onMouseClick.set((c, mi) -> {
            try
            {
                double m = Double.parseDouble(txt1.Text.get());
                double e = Double.parseDouble(txt2.Text.get());
                double ms = Double.parseDouble(txt3.Text.get());
                double mk = Double.parseDouble(txt4.Text.get());
                this.addRandomCircle(m, e, ms, mk);
            } catch(Exception ignored) // Fail silently
            { }
        });

        var btnReset = ctx.new Button("btnReset", "Reset", RIconsUI.getIcon(RIconsUI.RIcons.RICON_ROTATE_FILL));
        btnReset.onMouseClick.set((c, mi) -> Greenfoot.setWorld(new MyWorld()));
    }

    private void addRandomCircle(double m, double e, double ms, double mk)
    {
        // Nathan did a poo poo
        double randomRadius = rand.nextDouble() * 30 + 10;
        double randomX = rand.nextDouble() * getWidth() + 10;
        double randomY = rand.nextDouble() * getHeight() + 10;
        Circle c = new Circle(randomRadius);
        var obj = new GameObject("obj" + ctr++);
        obj.addComponent(new CircleRenderer(c));
        Rigidbody2d rb = new Rigidbody2d(obj, new CircleCollider(c), m);
        rb.changeProperties(m, e, ms, mk);
        obj.addComponent(rb);
        obj.addComponent(new Transform(randomX, randomY, 1, 1));

        this.addGameObject(obj);
    }

    private void addWalls()
    {
        // Poo poo Nathan xD
        Polygon wallX = new Polygon(
            new Vec2(0, 0),
            new Vec2(0, 10),
            new Vec2(getWidth(), 10),
            new Vec2(getWidth(), 0)
        );

        Polygon wallY = new Polygon(
            new Vec2(0, 0),
            new Vec2(10, 0),
            new Vec2(10, getHeight()),
            new Vec2(0, getHeight())
        );

        var wallX1 = new GameObject("wallLeft");
        var wallX2 = new GameObject("wallRight");
        var wallY1 = new GameObject("wallTop");
        var wallY2 = new GameObject("wallBottom");

        wallX1.addComponent(new Transform(getWidth() / 2.0, 0, 1, 1));
        wallX2.addComponent(new Transform(getWidth() / 2.0, getHeight(), 1, 1));
        wallY1.addComponent(new Transform(0, getHeight() / 2.0, 1, 1));
        wallY2.addComponent(new Transform(getWidth(), getHeight() / 2.0, 1, 1));

        wallX1.addComponent(new PolygonRenderer(wallX));
        wallX1.addComponent(new Rigidbody2d(wallX1, new PolygonCollider(wallX), Double.POSITIVE_INFINITY));
        wallX2.addComponent(new PolygonRenderer(wallX));
        wallX2.addComponent(new Rigidbody2d(wallX2, new PolygonCollider(wallX), Double.POSITIVE_INFINITY));
        wallY1.addComponent(new PolygonRenderer(wallY));
        wallY1.addComponent(new Rigidbody2d(wallY1, new PolygonCollider(wallY), Double.POSITIVE_INFINITY));
        wallY2.addComponent(new PolygonRenderer(wallY));
        wallY2.addComponent(new Rigidbody2d(wallY2, new PolygonCollider(wallY), Double.POSITIVE_INFINITY));

        this.addGameObject(wallX1);
        this.addGameObject(wallX2);
        this.addGameObject(wallY1);
        this.addGameObject(wallY2);
    }
}
