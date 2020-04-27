package oop.simulation;

import greenfoot.*;
import oop.simulation.beans.Property;
import oop.simulation.beans.Readonly;
import oop.simulation.components.BehaviourComponent;
import oop.simulation.physics2d.CollisionSystem;
import oop.simulation.physics2d.Rigidbody2d;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Function;

/**
 * Scene contains all the working logic for GameObjects and their Components.
 * The scene is what interprets the information held within the Components
 * of GameObjects - it drives the entire game forwards.
 * <p>
 * See {@link GameObject} {@link IComponent}
 *
 * @author Kevin Dai
 */
public class Scene extends World
{
    private long prevTime;

    private Camera activeCamera;
    private HashMap<String, GameObject> gameObjectHashMap;
    private GreenfootImage renderBuffer;
    private CollisionSystem collisionSystem;
    protected double deltaTime;

    /**
     * Time passed between this frame and the last one
     * in nanoseconds.
     */
    public final Readonly<Double> DeltaT = Property.get(() -> deltaTime).readonly();

    // Why not?
    private static final Font errFont = new Font("Sans Serif", 20);

    /**
     * Initialize scene without a camera
     * @param width  Width of window
     * @param height Height of window
     */
    public Scene(int width, int height)
    {
        this(null, width, height);
    }

    /**
     * Constructor for Scene. You'll rarely use this one.
     * @param camera The main camera of the scene
     * @param width  Width of window
     * @param height Height of window
     */
    public Scene(Camera camera, int width, int height)
    {
        super(width, height, 1);
        this.gameObjectHashMap = new HashMap<>();
        this.renderBuffer = new GreenfootImage(width, height);
        this.collisionSystem = new CollisionSystem();

        // Render
        this.render();

        // Then add camera
        this.activeCamera = camera;
    }

    @Override
    public void started()
    {
        // Init dt counter
        this.prevTime = System.nanoTime();
    }

    @Override
    public void act()
    {
        // Compute dt in ms
        deltaTime = (System.nanoTime() - prevTime) / 1000000000.0;
        prevTime = System.nanoTime();

        // Cache on the go!
        var bodies = new ArrayList<Rigidbody2d>();
        var scripts = new ArrayList<BehaviourComponent>();

        // Component updates first
        for(var g : gameObjectHashMap.values())
        {
            for (var c : g.getComponents())
            {
                if (c instanceof BehaviourComponent)
                {
                    scripts.add((BehaviourComponent) c);
                    continue;
                }

                if(c instanceof Rigidbody2d)
                {
                    bodies.add((Rigidbody2d) c);
                    continue;
                }

                c.update();
            }
        }

        // Do collisions
        collisionSystem.collide(bodies, deltaTime);
        for(var b : bodies) b.integrate();
        for(int i = 0; i < 20; i++)
            collisionSystem.solve();
        collisionSystem.correctPositions();
        for(var b : bodies)
        {
            b.update();
            b.clearForces();
        }

        // Render!
        this.render();

        // Apply behaviour updates last
        for (var c : scripts)
            c.update();
    }

    @Override
    public void repaint()
    {
        super.repaint(); // TODO: Do something with this if rendering goes horribly wrong
    }

    @Override
    public void addObject(Actor object, int x, int y)
    {
        super.addObject(object, x, y);
        //throw new UnsupportedOperationException("You cannot addObject() with Scenes!");
    }

    /**
     * Adds a new GameObject to the scene
     * @param obj GameObject to add
     */
    public void addGameObject(GameObject obj) //throws IllegalArgumentException
    {
        if(this.gameObjectHashMap.containsKey(obj.Name.get()))
            throw new IllegalArgumentException("Object of name '" + obj.Name.get() + "' already exists!");
        this.gameObjectHashMap.put(obj.Name.get(), obj);
        obj.addToScene(this);
    }

    /**
     * Gets a GameObject by name
     * @param name Name of GameObject
     * @return GameObject if found, null otherwise.
     */
    public GameObject getGameObjects(String name)
    {
        return this.gameObjectHashMap.getOrDefault(name, null);
    }

    /**
     * Gets a GameObject based ona filter
     * @param filter Takes in a GameObject and returns a Boolean to decide whether to include or exclude object.
     * @return ArrayList of GameObjects which satisfies the given filter
     */
    public ArrayList<GameObject> getGameObjects(Function<GameObject, Boolean> filter)
    {
        ArrayList<GameObject> obj = new ArrayList<>();
        this.gameObjectHashMap.forEach((k, v) -> {
            if(filter.apply(v)) obj.add(v);
        });
        return obj;
    }

    /**
     * Sets the default camera of the scene.
     * @param name Name of camera to add
     */
    public void setActiveCamera(String name)
    {
        var camera = this.gameObjectHashMap.getOrDefault(name, null);
        if(camera == null)
            throw new IllegalArgumentException("Object of name '" + name + "' does not exist!");
        else if(!(camera instanceof Camera))
            throw new IllegalArgumentException("Object of name '" + name + "' (" + camera.getClass() + ") is not an instanceof Camera!");
        this.activeCamera = (Camera) camera;
    }

    private void render()
    {
        // Render!
        if(activeCamera == null)
        {
            getBackground().setColor(Color.BLACK);
            getBackground().fill();
            getBackground().setColor(Color.WHITE);
            getBackground().setFont(errFont);
            getBackground().drawString("This scene does not have an active camera (yet)!", 10, 30);
            return;
        }

        // Is buffering needed?
        activeCamera.renderToBuffer(renderBuffer);
        this.setBackground(renderBuffer);
        repaint();
    }
}
