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
import oop.simulation.physics2d.PhysicsWorld;
import oop.simulation.physics2d.Rigidbody2d;
import oop.simulation.ui.PicoUI;
import oop.simulation.ui.RIconsUI;

import java.util.Random;

/**
 * Example main world
 *
 * @author Everyone
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
    private PicoUI ctx1; // Title sequence
    private boolean titleShown = false, titleClosed = false; // Title sequence
    private double forceMultiplier = 600; // Multiplier for controller
    private Camera2d cam; // Main camera

    /**
     * This is the world constructor
     */
    public MyWorld()
    {
        super(600, 400);
        Greenfoot.setSpeed(100); // Yeah baby!!
        PhysicsWorld.GRAVITY = new Vec2(0.0, -98.1);

        // Create a camera now
        cam = new Camera2d("camera1");
        cam.addComponent(new Transform(0, 0, 1, 1));
        this.addGameObject(cam);
        this.setActiveCamera("camera1");

        // FPS Counter
        UiOverlayComponent ui = new UiOverlayComponent(this.getWidth(), this.getHeight());
        cam.addComponent(ui);

        // Add simple scene behaviour
        cam.addComponent(new BehaviourComponent(g -> {
            String msg = titleClosed ? isHidden ? "Press [space] to view menu" : "Press [space] to hide menu" : "";
            String txt = String.format("FPS: %d", Math.round(1 / (DeltaT.get())));
            ui.writeString(txt, 15, 30, Color.RED, 15, false, false);
            ui.writeString(msg, 90, 30, Color.CYAN, 15, false, false);

            if(Greenfoot.isKeyDown("space") && spaceDebounceTimer > 0.3 && titleClosed)
            {
                if(isHidden)
                {
                    this.addObject(ctx, getWidth()/2, getHeight()/2);
                    ctx.update(5, 40);
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
            if(titleClosed)
                ui.writeString("Selected mass: " + rb.getMass(), 290, 30, Color.YELLOW, 15, false, false);

            // Strength
            double k = forceMultiplier * rb.getMass();

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
            addRandomCircle(false, 1.0, 2.1,0.5,0.3);
        getGameObject(prvSel).select();
        getGameObject(prvSel).addComponent(controllerScript);

        // Draw UI
        drawUI();
    }

    /**
     * This draws the user interface onto the scene
     */
    private void drawUI()
    {
        // New UI context
        this.ctx = new PicoUI(400, 290, PicoStyleCyber.getInstance());

        var lbl1 = ctx.new Label("title", "=== Kinematic Settings ===");
        lbl1.AutoSizeWidth.set(false);
        lbl1.Width.set(380);

        // Text
        ctx.new Label("lbl1", "Density:      ");
        var txt1 = ctx.new TextField("txt1", 40);
        ctx.new Label("lbl2", " Restitution:  ");
        var txt2 = ctx.new TextField("txt2", 40);
        ctx.new Label("lbl3", "Friction (s): ");
        var txt3 = ctx.new TextField("txt3", 40);
        ctx.new Label("lbl4", " Friction (k): ");
        var txt4 = ctx.new TextField("txt4", 40);

        txt1.PromptText.set("p"); txt1.Text.set("1.0");
        txt2.PromptText.set("e"); txt2.Text.set("1.8");
        txt3.PromptText.set("mu_s"); txt3.Text.set("0.5");
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
        var txt5 = ctx.new TextField("txtObj", 100, lbt.Height.get());
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
            getGameObject(prvSel).deselect();
            getGameObject(prvSel).removeComponent(controllerScript);
            getGameObject(txt5.Text.get()).select();
            getGameObject(txt5.Text.get()).addComponent(controllerScript);
            prvSel = txt5.Text.get();
        });

        ctx.new Button("btnApp", "Apply", RIconsUI.getIcon(RIconsUI.RIcons.RICON_OK_TICK))
            .onMouseClick.set((c, mi) -> {
            var g = getGameObject(txt5.Text.get());
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

        var chk1 =  ctx.new Label("checkbox1", "Is Static ", RIconsUI.getIcon(RIconsUI.RIcons.RICON_BOX));
        chk1.Enabled.set(true);
        chk1.onMouseClick.set((s, e) -> toggleLabelCheck((PicoUI.Label) s));

        var chk2 =  ctx.new Label("checkbox2", "Random Start Force ", RIconsUI.getIcon(RIconsUI.RIcons.RICON_BOX));
        chk2.Enabled.set(true);
        chk2.onMouseClick.set((s, e) -> toggleLabelCheck((PicoUI.Label) s));

        ctx.new Label("killMeNow_3", "      ");

        var chk3 =  ctx.new Label("checkbox3", "Gravity ", RIconsUI.getIcon(RIconsUI.RIcons.RICON_BOX_CHECKED));
        chk3.Enabled.set(true);
        chk3.Checked.set(true);
        chk3.onMouseClick.set((s, e) -> {
            toggleLabelCheck((PicoUI.Label) s);
            if(s.Checked.get())
                PhysicsWorld.GRAVITY = new Vec2(0.0, -98.1);
            else
                PhysicsWorld.GRAVITY = new Vec2(0.0, 0.0);
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
                this.addRandomCircle(chk2.Checked.get(), m, e, ms, mk);
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
                this.addRandomPolygon(chk2.Checked.get(), m, e, ms, mk);
            } catch(Exception ignored) // Fail silently
            { }
        });

        ctx.new Button("btnStart", "Party", RIconsUI.getIcon(RIconsUI.RIcons.RICON_PLAYER_PLAY))
            .onMouseClick.set((c, mi) -> {
            try
            {
                double m = Double.parseDouble(txt1.Text.get());
                double e = Double.parseDouble(txt2.Text.get());
                double ms = Double.parseDouble(txt3.Text.get());
                double mk = Double.parseDouble(txt4.Text.get());
                for(int i = 0; i < rand.nextInt(5) + 8; i++)
                    addRandomCircle(chk2.Checked.get(), m, e, ms, mk);
                for(int i = 0; i < rand.nextInt(5) + 8; i++)
                    addRandomPolygon(chk2.Checked.get(), m, e, ms, mk);
            } catch(Exception ignored) // Fail silently
            { }
        });

        ctx.new Button("btnReset", "Reset", RIconsUI.getIcon(RIconsUI.RIcons.RICON_ROTATE_FILL))
            .onMouseClick.set((c, mi) -> {
                var world = new MyWorld();
                world.titleShown = true; // Suppress title sequence
                world.titleClosed = true;
                Greenfoot.setWorld(world);
        });

        ctx.new Button("btnClose", "Close", RIconsUI.getIcon(RIconsUI.RIcons.RICON_CROSS_SMALL))
            .onMouseClick.set((c, mi) -> {
                isHidden = true;
                ctx.remove();
        });

        ctx.new Button("btnEmpty", RIconsUI.getIcon(RIconsUI.RIcons.RICON_BIN))
            .onMouseClick.set((c, mi) -> {
                for(int i = 0; i < ctr; i++)
                    removeGameObject("obj" + i);
                ctr = 0;
                addRandomCircle(false, 1, 2.1, 0.4, 0.3);
                getGameObject("obj0").select();
                getGameObject("obj0").addComponent(controllerScript);
                txt5.Text.set("obj0");
                prvSel = "obj0";
        });

        ctx.new Button("troll1", RIconsUI.getIcon(RIconsUI.RIcons.RICON_EXPLOSION))
            .onMouseClick.set((c, mi) -> {
                try
                {
                    double m = Double.parseDouble(txt1.Text.get());
                    double e = Double.parseDouble(txt2.Text.get());
                    double ms = Double.parseDouble(txt3.Text.get());
                    double mk = Double.parseDouble(txt4.Text.get());
                    for (int i = 0; i < 100; i++)
                        addRandomCircle(chk2.Checked.get(), 5, m, e, ms, mk);
                } catch(Exception ignored) { }
        });

        ctx.new Button("troll2", RIconsUI.getIcon(RIconsUI.RIcons.RICON_GEAR_EX))
            .onMouseClick.set((c, mi) -> {
            try
            {
                double m = Double.parseDouble(txt1.Text.get());
                double e = Double.parseDouble(txt2.Text.get());
                double ms = Double.parseDouble(txt3.Text.get());
                double mk = Double.parseDouble(txt4.Text.get());
                for (int i = 0; i < 100; i++)
                    addRandomCircle(chk2.Checked.get(), 10, m, e, ms, mk);
            } catch(Exception ignored) { }
        });

        ctx.new Button("btnFx", RIconsUI.getIcon(RIconsUI.RIcons.RICON_FX))
            .onMouseClick.set((c, mi) -> {
                c.Checked.set(!c.Checked.get());
                cam.ShowEffects.set(c.Checked.get());
        });
        ctx.getControl("btnFx").Checked.set(true);
    }

    /**
     * This adds a random circle onto the scene
     * @param random            Whether or not it is a random circle
     * @param d                 The density
     * @param e                 The restitution
     * @param ms                The static friction
     * @param mk                The dynamic friction
     */
    private void addRandomCircle(boolean random, double d, double e, double ms, double mk)
    {
        // Nathan did a poo poo
        double randomRadius = rand.nextDouble() * 30 + 10;
        this.addRandomCircle(random, randomRadius, d, e, ms, mk);
    }

    /**
     * This adds a random circle onto the scene
     * @param random            Whether or not it is a random circle
     * @param r                 The radius
     * @param d                 The density
     * @param e                 The restitution
     * @param ms                The static friction
     * @param mk                The dynamic friction
     */
    private void addRandomCircle(boolean random, double r, double d, double e, double ms, double mk)
    {
        double randomX = rand.nextDouble() * (getWidth() - 100) + 50;
        double randomY = rand.nextDouble() * (getHeight() - 100) + 50;
        Circle c = new Circle(r);
        var obj = new GameObject("obj" + ctr++);
        obj.addComponent(new CircleRenderer(c));
        Rigidbody2d rb = new Rigidbody2d(obj, new CircleCollider(c), d);
        rb.changeProperties(d * r / 100.0, e, ms, mk);
        // System.out.println(rb.getMass());

        if(random)
        {
            var th = rand.nextDouble() * Math.PI * 2;
            var rr = rand.nextDouble() * 50 + 90;
            rb.applyImpulse(new Vec2(rr * Math.cos(th), rr * Math.sin(th)), new Vec2(-rr * Math.sin(th), rr * Math.cos(th)));
        }

        obj.addComponent(rb);
        obj.addComponent(new Transform(randomX, randomY, 1, 1));

        this.addGameObject(obj);
    }

    /**
     * This adds a random circle onto the scene
     * @param random            Whether or not it is a random circle
     * @param d                 The density
     * @param e                 The restitution
     * @param ms                The static friction
     * @param mk                The dynamic friction
     */
    private void addRandomPolygon(boolean random, double d, double e, double ms, double mk)
    {
        // Nathan did a poo poo

        int nverts = rand.nextInt(6) + 4;
        Vec2[] verts = new Vec2[nverts];
        ValtrAlgorithm.generateRandomConvexPolygon(nverts).toArray(verts);
        for(Vec2 v: verts)
            v.scalarMultiply(5);

        double randomX = rand.nextDouble() * (getWidth() - 100) + 50;
        double randomY = rand.nextDouble() * (getHeight() - 100) + 50;

        Polygon p = new Polygon(verts);

        var obj = new GameObject("obj" + ctr++);
        obj.addComponent(new PolygonRenderer(p));
        Rigidbody2d rb = new Rigidbody2d(obj, new PolygonCollider(p), d);
        rb.changeProperties(d * Math.sqrt(Math.abs(p.getSignedArea())) / 100.0, e, ms, mk);
        // System.out.println(rb.getMass());

        if(random)
        {
            var th = rand.nextDouble() * Math.PI * 2;
            var rr = rand.nextDouble() * 50 + 90;
            rb.applyImpulse(new Vec2(rr * Math.cos(th), rr * Math.sin(th)), new Vec2(-rr * Math.sin(th), rr * Math.cos(th)));
        }

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

    private void toggleLabelCheck(PicoUI.Label s)
    {
        s.Checked.set(!s.Checked.get());
        if(s.Checked.get())
            s.Icon.set(RIconsUI.getIcon(RIconsUI.RIcons.RICON_BOX_CHECKED));
        else
            s.Icon.set(RIconsUI.getIcon(RIconsUI.RIcons.RICON_BOX));
    }

    /**
     * This sets the instructions for the simulation
     */
    private void showTitleCard()
    {
        ctx1 = new PicoUI(400, 290, PicoStyleCyber.getInstance());
        var title = ctx1.new Label("title", "=== Bouncy Code ===");
        title.AutoSizeWidth.set(false);
        title.Width.set(380);

        // Text goes here...
        ctx1.new Label("lbl1", "Hi welcome to our simulation");
        ctx1.new Label("lbl2", "In order to access the settings press space");
        ctx1.new Label("lbl3", "To change how bouncy all the polygons are,");
        ctx1.new Label("lbl3.5", "enter a new value in restitution");
        ctx1.new Label("lbl4", "To change the mass of the polygons,");
        ctx1.new Label("lbl4.5", "enter a new value in mass");
        ctx1.new Label("lbl5", "Finally press add as many new polygon or");
        ctx1.new Label("lbl5.5", "circle into the world");
        // Etc...

        // Button
        ctx1.new Label("lblLast", "                                ");
        ctx1.new Button("btnClose", "Close", RIconsUI.getIcon(RIconsUI.RIcons.RICON_CROSS_SMALL))
            .onMouseClick.set((c, mi) -> {
            ctx1.remove();
            titleClosed = true;
        });
    }

    @Override
    public void act()
    {
        super.act();

        if(!titleShown)
        {
            showTitleCard();
            this.addObject(ctx1, getWidth() / 2, getHeight() / 2);
            ctx1.update(5, 30);
            titleShown = true;
        }
    }
}
