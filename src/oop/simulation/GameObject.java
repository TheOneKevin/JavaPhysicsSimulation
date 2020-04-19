package oop.simulation;

import oop.simulation.beans.*;

import java.util.ArrayList;

/**
 * GameObject is the replacement for Actors.
 * GameObjects are fundamentally different from Actors.
 * They only <b>STORE</b> information in the form of Components (see {@link IComponent}).
 * GameObjects contain no logic.
 *
 * @author Kevin Dai
 */
public class GameObject
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
    public <T extends IComponent> ArrayList<T> getComponents(Class<T> c)
    {
        ArrayList<T> res = new ArrayList<>(this.components.size());
        for(IComponent v : this.components)
            if(v.getClass() == c) res.add((T) v);
        return res;
    }

    public IComponent[] getComponents()
    {
        IComponent[] arr = new IComponent[this.components.size()];
        this.components.toArray(arr);
        return arr;
    }

    /**
     * Get first component matching class
     * @param c Class of component to get
     * @return First component of a given class
     */
    public <T extends IComponent> T getComponent(Class<T> c)
    {
        for(IComponent v : this.components)
            if(v.getClass() == c) return (T) v;
        return null;
    }

    /**
     * Adds a component to the GameObject
     * @param c Component to add
     */
    public void addComponent(IComponent c)
    {
        if(getComponent(c.getClass()) != null && c.isUnique())
            return; // TODO: Failure
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
