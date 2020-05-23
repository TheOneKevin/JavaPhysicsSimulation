import greenfoot.Color;
import greenfoot.Greenfoot;
import oop.simulation.GameObject;
import oop.simulation.Scene;
import oop.simulation.components.*;
import oop.simulation.math.Circle;
import oop.simulation.math.Polygon;
import oop.simulation.math.ValtrAlgorithm;
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
    private int ctr = 0; // Total number of rb's in scene
    private Random rand = new Random();
    boolean isHidden = true; // Is menu hidden?
    private double spaceDebounceTimer = 0; // Makes sure space bar is debounced
    private String prvSel = "obj0"; // Get previously selected object
    private BehaviourComponent controllerScript; // Behaviour for controller

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

        controllerScript = new BehaviourComponent(g -> {
            // Get rigidbody
            var rb = g.getComponent(Rigidbody2d.class);

            // Strength
            double k = 300;

            // Just testing, apply forces of 3N (1px = 1cm, 1s = 1s)
            if (Greenfoot.isKeyDown("w")) rb.applyForce(new Vec2(0, k));
            if (Greenfoot.isKeyDown("s")) rb.applyForce(new Vec2(0, -k));
            if (Greenfoot.isKeyDown("a")) rb.applyForce(new Vec2(-k, 0));
            if (Greenfoot.isKeyDown("d")) rb.applyForce(new Vec2(k, 0));

            // Rotate the damn thing
            // rb.AngularVelocity.set(0d); // Biggest facepalm ever
            if (Greenfoot.isKeyDown("left")) rb.applyForce(new Vec2(-k, 0), new Vec2(0, 10));
            if (Greenfoot.isKeyDown("right")) rb.applyForce(new Vec2(k, 0), new Vec2(0, 10));
        });

        // Add walls
        addWalls();

        // And spice!
        for(int i = 0; i < rand.nextInt(5) + 1; i++)
            addRandomCircle(1, 2.8,0.4,0.3);
        getGameObjects(prvSel).select();
        getGameObjects(prvSel).addComponent(controllerScript);

        // Draw UI
        drawUI();
    }

    private void drawUI()
    {
        // New UI context
        this.ctx = new PicoUI(400, 302, PicoStyleCyber.getInstance());

        var lbl1 = ctx.new Label("title", "=== Kinematic Settings ===");
        lbl1.AutoSizeWidth.set(false);
        lbl1.Width.set(380);

        // Text
        ctx.new Label("lbl1", "Mass:         ");
        var txt1 = ctx.new TextField("txt1", 40);
        ctx.new Label("lbl2", " Restitution:  ");
        var txt2 = ctx.new TextField("txt2", 40);
        ctx.new Label("lbl3", "Friction (s): ");
        var txt3 = ctx.new TextField("txt3", 40);
        ctx.new Label("lbl4", " Friction (k): ");
        var txt4 = ctx.new TextField("txt4", 40);

        txt1.PromptText.set("m"); txt1.Text.set("1");
        txt2.PromptText.set("e"); txt2.Text.set("2.0");
        txt3.PromptText.set("mu_s"); txt3.Text.set("0.4");
        txt4.PromptText.set("mu_k"); txt4.Text.set("0.3");
        ctx.new Label("killMeNow_1", "  ");

        /* Create a carousel "control" (made of smaller controls) */

        ctx.new Label("lbl5", "Body: ");

        // Left button
        var lbt = ctx.new Button("btnLbt", "", RIconsUI.getIcon(RIconsUI.RIcons.RICON_ARROW_LEFT_FILL));
        lbt.Margins.set(new PicoUI.Margins(5, 0, 5, -1));
        lbt.AutoSizeWidth.set(false);
        lbt.Width.set(17);
        lbt.onMouseClick.set((s, e) -> {
            var t = ctx.getControl("txtObj", PicoUI.TextField.class);
            t.Metadata.set(((int) t.Metadata.get() - 1 + ctr) % ctr);
            t.Text.set("obj" + t.Metadata.get().toString());
        });

        // Text box
        var txt5 = ctx.new TextField("txtObj", 50, lbt.Height.get());
        txt5.Margins.set(new PicoUI.Margins(5, 3, 5, 3));
        txt5.Metadata.set(0);
        txt5.Text.set("obj0");
        txt5.Enabled.set(false);

        // Right button
        var rbt = ctx.new Button("btnRbt", "", RIconsUI.getIcon(RIconsUI.RIcons.RICON_ARROW_RIGHT_FILL));
        rbt.Margins.set(new PicoUI.Margins(5, 0, 5, -2));
        rbt.AutoSizeWidth.set(false);
        rbt.Width.set(17);
        rbt.onMouseClick.set((s, e) -> {
            var t = ctx.getControl("txtObj", PicoUI.TextField.class);
            t.Metadata.set(((int) t.Metadata.get() + 1 + ctr) % ctr);
            t.Text.set("obj" + t.Metadata.get().toString());
        });

        // Select
        ctx.new Button("btnSel", "Select", RIconsUI.getIcon(RIconsUI.RIcons.RICON_TARGET))
            .onMouseClick.set((c, mi) -> {
            getGameObjects(prvSel).deselect();
            getGameObjects(prvSel).removeComponent(controllerScript);
            getGameObjects(txt5.Text.get()).select();
            getGameObjects(txt5.Text.get()).addComponent(controllerScript);
            prvSel = txt5.Text.get();
        });

        ctx.new Button("btnApp", "Apply", RIconsUI.getIcon(RIconsUI.RIcons.RICON_OK_TICK))
            .onMouseClick.set((c, mi) -> {
            var g = getGameObjects(txt5.Text.get());
            try
            {
                double m = Double.parseDouble(txt1.Text.get());
                double e = Double.parseDouble(txt2.Text.get());
                double ms = Double.parseDouble(txt3.Text.get());
                double mk = Double.parseDouble(txt4.Text.get());
                if(ctx.getControl("checkbox1").Checked.get())
                    m = Double.POSITIVE_INFINITY;
                g.getComponent(Rigidbody2d.class).changeProperties(m, e, ms, mk);
            } catch(Exception ignored) // Fail silently
            { }
        });

        // More buttons

        var chk1 =  ctx.new Label("checkbox1", "Is Static?", RIconsUI.getIcon(RIconsUI.RIcons.RICON_BOX));
        chk1.Enabled.set(true);
        chk1.onMouseClick.set((s, e) -> {
            chk1.Checked.set(!chk1.Checked.get());
            if(chk1.Checked.get())
                chk1.Icon.set(RIconsUI.getIcon(RIconsUI.RIcons.RICON_BOX_CHECKED));
            else
                chk1.Icon.set(RIconsUI.getIcon(RIconsUI.RIcons.RICON_BOX));
        });

        ctx.new Button("btnAddC", "Add Circle", RIconsUI.getIcon(RIconsUI.RIcons.RICON_FILE_NEW))
            .onMouseClick.set((c, mi) -> {
            try
            {
                double m = Double.parseDouble(txt1.Text.get());
                double e = Double.parseDouble(txt2.Text.get());
                double ms = Double.parseDouble(txt3.Text.get());
                double mk = Double.parseDouble(txt4.Text.get());
                if(chk1.Checked.get())
                    m = Double.POSITIVE_INFINITY;
                this.addRandomCircle(m, e, ms, mk);
            } catch(Exception ignored) // Fail silently
            { }
        });

        ctx.new Button("btnAddP", "Add Polygon", RIconsUI.getIcon(RIconsUI.RIcons.RICON_FILE_NEW))
            .onMouseClick.set((c, mi) -> {
            try
            {
                double m = Double.parseDouble(txt1.Text.get());
                double e = Double.parseDouble(txt2.Text.get());
                double ms = Double.parseDouble(txt3.Text.get());
                double mk = Double.parseDouble(txt4.Text.get());
                if(chk1.Checked.get())
                    m = Double.POSITIVE_INFINITY;
                this.addRandomPolygon(m, e, ms, mk);
            } catch(Exception ignored) // Fail silently
            { }
        });

        ctx.new Button("button3", RIconsUI.getIcon(RIconsUI.RIcons.RICON_DEMON))
            .onMouseClick.set((c, mi) -> {
            for(int i = 0; i < rand.nextInt(30) + 30; i++)
                addRandomCircle(1, 3.5,0.4,0.3);
        });

        ctx.new Button("btnReset", "Reset", RIconsUI.getIcon(RIconsUI.RIcons.RICON_ROTATE_FILL))
            .onMouseClick.set((c, mi) -> Greenfoot.setWorld(new MyWorld()));

        ctx.new Button("btnClose", "Close", RIconsUI.getIcon(RIconsUI.RIcons.RICON_CROSS_SMALL))
            .onMouseClick.set((c, mi) -> {
                isHidden = true;
                ctx.remove();
        });
    }

    private void addRandomCircle(double m, double e, double ms, double mk)
    {
        // Nathan did a poo poo
        double randomRadius = rand.nextDouble() * 30 + 10;
        double randomX = rand.nextDouble() * (getWidth() - 100) + 50;
        double randomY = rand.nextDouble() * (getHeight() - 100) + 50;
        Circle c = new Circle(randomRadius);
        var obj = new GameObject("obj" + ctr++);
        obj.addComponent(new CircleRenderer(c));
        Rigidbody2d rb = new Rigidbody2d(obj, new CircleCollider(c), m);
        rb.changeProperties(m, e, ms, mk);
        obj.addComponent(rb);
        obj.addComponent(new Transform(randomX, randomY, 1, 1));

        this.addGameObject(obj);
    }

    private void addRandomPolygon(double m, double e, double ms, double mk)
    {
        // Nathan did a poo poo

        int nverts = rand.nextInt(6) + 3;
        Vec2[] verts = new Vec2[nverts];
        ValtrAlgorithm.generateRandomConvexPolygon(nverts).toArray(verts);
        for(Vec2 v: verts)
            v.scalarMultiply(5);

        double randomX = rand.nextDouble() * (getWidth() - 100) + 50;
        double randomY = rand.nextDouble() * (getHeight() - 100) + 50;

        Polygon p = new Polygon(verts);

        var obj = new GameObject("obj" + ctr++);
        obj.addComponent(new PolygonRenderer(p));
        Rigidbody2d rb = new Rigidbody2d(obj, new PolygonCollider(p), m);
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
            new Vec2(0, 20),
            new Vec2(getWidth(), 20),
            new Vec2(getWidth(), 0)
        );

        Polygon wallY = new Polygon(
            new Vec2(0, 0),
            new Vec2(20, 0),
            new Vec2(20, getHeight()),
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
