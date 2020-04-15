package oop.simulation;

import greenfoot.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Function;

/**
 * Scene adds the base foundation support for GameObjects, Cameras and DeltaT.
 *
 * @author Kevin Dai
 */
public class Scene extends World
{
    private long prevTime, deltaTime;
    private Camera mainCamera;
    private HashMap<String, GameObject> gameObjectHashMap;
    private GreenfootImage renderBuffer;

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

        // Init dt counter
        this.prevTime = System.nanoTime();
        this.act();
        // Then add camera
        this.mainCamera = camera;
    }

    @Override
    public void act()
    {
        // Compute dt
        deltaTime = System.nanoTime() - prevTime;
        prevTime = System.nanoTime();

        if(mainCamera == null)
        {
            getBackground().setColor(Color.BLACK);
            getBackground().fill();
            getBackground().setColor(Color.WHITE);
            getBackground().setFont(errFont);
            getBackground().drawString("This scene does not have a valid camera!", 10, 30);
            return;
        }

        // Is buffering needed?
        mainCamera.renderToBuffer(renderBuffer);
        this.setBackground(renderBuffer);
        repaint();
    }

    @Override
    public void repaint()
    {
        super.repaint(); // TODO: Do something with this if rendering goes horribly wrong
    }

    @Override
    public void addObject(Actor object, int x, int y)
    {
        throw new UnsupportedOperationException("You cannot addObject() with Scenes!");
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
    public GameObject getGameObject(String name)
    {
        return this.gameObjectHashMap.getOrDefault(name, null);
    }

    /**
     * Gets a GameObject based ona filter
     * @param filter Takes in a GameObject and returns a Boolean to decide whether to include or exclude object.
     * @return ArrayList of GameObjects which satisfies the given filter
     */
    public ArrayList<GameObject> getGameObject(Function<GameObject, Boolean> filter)
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
        this.mainCamera = (Camera) camera;
    }
}
