package oop.simulation;

import oop.simulation.beans.*;

import java.util.ArrayList;

/**
 * GameObject is the replacement for Actors
 * TODO: Should we extend Actor? No.
 *
 * @author Kevin Dai
 */
public abstract class GameObject
{
    private String internalName;
    private Scene scene;

    // TODO: Should we expose as a property?
    private ArrayList<IComponent> components;

    // ======================= Properties ======================= //

    /**
     * The name of this GameObject
     */
    public final Readonly<String> Name = Property.get(() -> internalName).readonly();

    /**
     * Default constructor of GameObject
     * @param name Name of GameObject
     */
    public GameObject(String name)
    {
        this.internalName = name;
        this.components = new ArrayList<>();
    }

    /**
     * Gets all components with a certain class
     * @param c Class of component to get
     * @return ArrayList of valid components
     */
    public ArrayList<IComponent> getComponentsByClass(Class<IComponent> c)
    {
        ArrayList<IComponent> res = new ArrayList<>();
        this.components.forEach((v) -> {
            if(v.getClass() == c) res.add(v);
        });
        return res;
    }

    /**
     * Adds a component to the GameObject
     * @param c
     */
    public void addComponent(IComponent c)
    {
        this.components.add(c);
    }

    /**
     * Gets the scene the GameObject is currently in
     * @return Scene the GameObject is currently in
     */
    public Scene getScene()
    {
        return this.scene;
    }

    protected void addToScene(Scene s)
    {
        this.scene = s;
    }
}
